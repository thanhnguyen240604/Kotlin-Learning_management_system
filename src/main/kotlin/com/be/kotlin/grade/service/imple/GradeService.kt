package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.gradeDTO.GradeDTO
import com.be.kotlin.grade.dto.gradeDTO.GradeIdDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.GradeMapper
import com.be.kotlin.grade.model.Grade
import com.be.kotlin.grade.repository.GradeRepository
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.service.interf.IGrade
import com.be.kotlin.grade.service.interf.IStudyProgress
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.ss.usermodel.CellType

@Service
class GradeService(
    private val gradeRepository: GradeRepository,
    private val gradeMapper: GradeMapper,
    private val studyRepository: StudyRepository,
    private val studyProgressService: IStudyProgress
) : IGrade {
    private fun isWeightValid(studyId: Long, newWeight: Float, existingWeight: Float? = null): Pair<Boolean, String> {
        val study = studyRepository.findById(studyId).orElse(null) ?: return Pair(false, "Study not found")
        // Tính tổng trọng số hiện tại
        val currentTotalWeight = study.gradesList.sumOf { it.weight.toDouble() }
        // Nếu có trọng số cũ, trừ nó ra
        val totalWeight = currentTotalWeight - (existingWeight?.toDouble() ?: 0.0) + newWeight.toDouble()
        return if (totalWeight <= 100) {
            Pair(true, "Weight is valid. Total weight: $totalWeight")
        } else {
            val remainingWeight = 100 - (currentTotalWeight- (existingWeight?.toDouble() ?: 0.0))
            Pair(false, "Total weight exceeds 100. Remaining weight: $remainingWeight")
        }
    }

    private fun isScoreValid(score: Float): Boolean {
        return score in 0.0..10.0
    }

    private fun caculateAverageScore(gradesList: MutableList<Grade>): Float  {
        var averageScore = 0F
        for (grade in gradesList) {
            averageScore += grade.score * grade.weight / 100
        }
        return averageScore
    }

    fun sanitizeColumnName(columnName: String): String {
        return columnName.replace(Regex("[^A-Za-z0-9]"), "")
    }

    fun extractWeight(header: String): Float {
        val regex = Regex("(\\d+\\.?\\d*)%")
        val matchResult = regex.find(header)
        return matchResult?.groups?.get(1)?.value?.toFloat() ?: 0f
    }

    override fun addGrade(grade: GradeDTO): Response {
        val gradeStudyId = grade.studyId
            ?: throw AppException(ErrorCode.STUDY_ID_INVALID)

        val study = studyRepository.findById(gradeStudyId).orElse(null)
            ?: throw AppException(ErrorCode.STUDY_NOT_FOUND)

        if (!isScoreValid(grade.score)) {
            throw AppException(ErrorCode.SCORE_INVALID)
        }

        // Kiểm tra weight
        val weightValidationResult = isWeightValid(gradeStudyId, grade.weight)
        if (!weightValidationResult.first) {
            throw AppException(ErrorCode.WEIGHT_LIMIT_INVALID, weightValidationResult.second)
        }

        val newGrade = gradeMapper.toGrade(grade)
        val savedGrade = gradeRepository.save(newGrade) // Lưu newGrade vào cơ sở dữ liệu
        study.gradesList.add(savedGrade) // Thêm savedGrade vào danh sách
        study.score = caculateAverageScore(study.gradesList)
        studyRepository.save(study)

        val savedGradeDTO = gradeMapper.toGradeDTO(savedGrade)

        //Add this study to study progress
//        if ( study.gradesList.sumOf { it.weight.toDouble() } == 100.0)
//            studyProgressService.updateStudyProgress(study)

        return Response(
            gradeDTO = savedGradeDTO,
            statusCode = 200,
            message = "Grade added successfully"
        )
    }

    override fun deleteGrade(grade: GradeIdDTO): Response {
        val gradeStudyId = grade.studyId
            ?: throw AppException(ErrorCode.STUDY_ID_INVALID)

        val gradeId = grade.id
            ?: throw AppException(ErrorCode.GRADE_ID_INVALID)

        // Kiểm tra xem điểm có tồn tại không
        val optionalGrade = gradeRepository.findById(gradeId)
        if (!optionalGrade.isPresent) {
            throw AppException(ErrorCode.GRADE_NOT_FOUND)
        }

        // Kiểm tra xem điểm có thuộc về studyId không
        val deletedGrade = optionalGrade.get()
        if (deletedGrade.studyId != gradeStudyId) {
            throw AppException(ErrorCode.GRADE_NOT_MATCH_INVALID)
        }
        // Xóa điểm
        gradeRepository.deleteById(gradeId)
        // Cập nhật danh sách điểm trong Study
        val study = studyRepository.findById(gradeStudyId).orElse(null)
        study?.gradesList?.remove(deletedGrade)
        study.score = caculateAverageScore(study.gradesList)
        studyRepository.save(study)
        return Response(
            gradeDTO = gradeMapper.toGradeDTO(deletedGrade),
            statusCode = 200,
            message = "Grade deleted successfully"
        )
    }

    override fun updateGrade(grade: GradeIdDTO): Response {
        val gradeId = grade.id
            ?: throw AppException(ErrorCode.GRADE_ID_INVALID)

        val gradeStudyId = grade.studyId
            ?: throw AppException(ErrorCode.STUDY_ID_INVALID)

        // Kiểm tra xem điểm có tồn tại không
        val optionalGrade = gradeRepository.findById(gradeId)
        if (!optionalGrade.isPresent) {
            throw AppException(ErrorCode.GRADE_NOT_FOUND)
        }
        // Kiểm tra xem điểm có thuộc về studyId không
        val existingGrade = optionalGrade.get()
        if (existingGrade.studyId != gradeStudyId) {
            throw AppException(ErrorCode.GRADE_NOT_MATCH_INVALID)
        }
        //Kiểm tra Score
        if (!isScoreValid(grade.score)) {
            throw AppException(ErrorCode.SCORE_INVALID)
        }
        //Kiểm tra Weight
        val weightValidationResult = isWeightValid(gradeStudyId, grade.weight, existingGrade.weight)
        if (!weightValidationResult.first) {
            throw AppException(ErrorCode.WEIGHT_LIMIT_INVALID, weightValidationResult.second)
        }

        // Cập nhật thông tin điểm
        val updatedGrade = existingGrade.copy(
            score = grade.score,
            weight = grade.weight
        ) // Giữ nguyên ID cũ và cập nhật các thuộc tính khác
        gradeRepository.save(updatedGrade)
        // Cập nhật danh sách điểm trong Study
        val study = studyRepository.findById(gradeStudyId).orElse(null)
        study?.gradesList?.remove(existingGrade) // Xóa điểm cũ
        study?.gradesList?.add(updatedGrade) // Thêm điểm đã cập nhật
        study.score = caculateAverageScore(study.gradesList)
        studyRepository.save(study)

        //Add this study to study progress
        if ( study.gradesList.sumOf { it.weight.toDouble() } == 100.0)
            studyProgressService.updateStudyProgress(study)

        return Response(
            gradeDTO = gradeMapper.toGradeDTO(updatedGrade),
            statusCode = 200,
            message = "Grade updated successfully"
        )
    }

    override fun getGradeById(id: Long): Response {
        val gradeGot = gradeRepository.findById(id)
            .orElseThrow { AppException(ErrorCode.GRADE_NOT_FOUND) }

        val gradeDTO = gradeMapper.toGradeDTO(gradeGot)
        return Response(
            statusCode = 200,
            message = "Grade found successfully",
            gradeDTO = gradeDTO
        )
    }

    override fun processExcel(file: MultipartFile): Response {
        val responses = mutableListOf<Response>()
        val errorMessages = mutableListOf<String>()
        val successMessages = mutableListOf<String>()

        // Chuyển file Excel thành InputStream
        val inputStream: InputStream = file.inputStream

        // Tạo Workbook từ file Excel
        val workbook = WorkbookFactory.create(inputStream)
        // Lấy sheet đầu tiên
        val sheet = workbook.getSheetAt(0)

        // Đọc thông tin từ dòng đầu tiên (tiêu đề)
        val headerRow = sheet.getRow(0) ?: return Response(statusCode = 400, message = "Missing header row")
        val headerData = headerRow.getCell(0)?.stringCellValue ?: return Response(statusCode = 400, message = "Invalid header format")
        val titleRow = sheet.getRow(1)
        var studentIdCol = -1
        val weightMap = mutableMapOf<Int, Float>() // Lưu trữ chỉ số cột và trọng số tương ứng

        // Xác định trọng số từ tiêu đề
        for (cellIndex in 0 until titleRow.lastCellNum) {
            val cellValue = titleRow.getCell(cellIndex).stringCellValue
            if (sanitizeColumnName(cellValue).equals("studentid", ignoreCase = true)) {
                studentIdCol = cellIndex
            } else {
                val weight = extractWeight(cellValue) // Trích xuất trọng số từ tiêu đề
                if (weight > 0) {
                    weightMap[cellIndex] = weight // Lưu trọng số nếu có
                }
            }
        }
        // Kiểm tra xem cột student_id có được tìm thấy không
        if (studentIdCol == -1) {
            errorMessages.add("Không tìm thấy cột studentid trong file.")
            return Response(
                statusCode = 400,
                message = errorMessages.joinToString(" --- "),
                listGradeDTO = emptyList()
            )
        }

        // Trích xuất mã môn học, lớp, học kỳ
        val regex = """([A-Z0-9]+) - L(\d+) - (\d+)""".toRegex()
        val matchResult = regex.find(headerData) ?: return Response(statusCode = 400, message = "Header format not recognized")
        val subjectId = matchResult.groupValues[1]
        val className = matchResult.groupValues[2].toLong()
        val semester = matchResult.groupValues[3].toInt()

        // Lặp qua từng dòng (bỏ qua dòng đầu tiên là tiêu đề)
        for (rowIndex in 2..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex)

//            // Lấy dữ liệu từ các cột, kiểm tra null nếu cần
//            val studentId = row.getCell(0)?.numericCellValue?.toLong() ?: continue

            // Đọc student ID
            val studentIdCell = row.getCell(studentIdCol)
            if (studentIdCell == null || studentIdCell.cellType == CellType.BLANK) {
                // Dừng xử lý nếu hàng không có student ID
                break
            }

            // Kiểm tra xem student ID có phải là số không
            if (studentIdCell.cellType != CellType.NUMERIC) {
                errorMessages.add("Student ID không hợp lệ ở hàng ${rowIndex + 1}. Phải là số.")
                continue
            }

            val studentId = studentIdCell.numericCellValue.toLong() // Cột studentId
            val studyGot = studyRepository.findByStudentIdAndSubjectIdAndSemester(studentId, subjectId, semester)
                ?: return Response(statusCode = 400, message = "Error adding study for student ID: $studentId")
            // Duyệt qua các cột điểm
            for (cellIndex in 1 until titleRow.lastCellNum) {
                if (cellIndex == studentIdCol) continue // Bỏ qua cột student_id
                val scoreCell = row.getCell(cellIndex)

                // Kiểm tra xem ô điểm có hợp lệ không
                if (scoreCell == null || scoreCell.cellType == CellType.BLANK) {
                    continue // Bỏ qua ô điểm trống
                }

                // Kiểm tra xem điểm có phải là số không
                if (scoreCell.cellType != CellType.NUMERIC) {
                    errorMessages.add("Điểm không hợp lệ ở hàng ${rowIndex + 1}, cột ${cellIndex + 1}. Phải là số.")
                    continue
                }

                val score = scoreCell.numericCellValue.toFloat() // Cột điểm
                val weight = weightMap[cellIndex] ?: continue // Lấy trọng số tương ứng

                // Tìm studyId từ classId, subjectId và studentId
                val studyId = studyGot.id

                if (studyId == null) {
                    errorMessages.add("Không tìm thấy study cho studentId $studentId ở hàng ${rowIndex + 1}.")
                    continue
                }

                // Tạo đối tượng GradeDTO
                val gradeDTO = GradeDTO(studyId = studyId, score = score, weight = weight)

                try {
                    val res = addGrade(gradeDTO)
                    responses.add(res)
                    successMessages.add("Thêm điểm thành công cho studentId $studentId với điểm ${gradeDTO.score} và trọng số ${gradeDTO.weight} ở hàng ${rowIndex + 1}.")
                } catch (e: AppException) {
                    errorMessages.add("Lỗi xảy ra khi thêm điểm ở hàng ${rowIndex + 1}: ${e.message ?: "Lỗi không xác định"}")
                } catch (e: Exception) {
                    errorMessages.add("Lỗi không xác định xảy ra ở hàng ${rowIndex + 1}: ${e.message ?: "Lỗi không xác định"}")
                }
            }

        }

        val finalMessage = StringBuilder()
        if (successMessages.isNotEmpty()) {
            finalMessage.append("Thêm điểm thành công cho các sinh viên sau:  ")
            finalMessage.append(successMessages.joinToString(" --- "))
        }
        if (errorMessages.isNotEmpty()) {
            if (finalMessage.isNotEmpty()) finalMessage.append(" |------| ")
            finalMessage.append("Một số điểm không thể được thêm vào:  ")
            finalMessage.append(errorMessages.joinToString(" --- "))
        }
        workbook.close()
        inputStream.close()

//        return Response(statusCode = 200, message = "danh sách học sinh đã được thêm vào lớp")
        return Response(
            statusCode = if (errorMessages.isNotEmpty()) 400 else 200,
            message = "Grade added successfully through excel file",
            listGradeDTO = responses.mapNotNull { it.gradeDTO }
        )
    }
}