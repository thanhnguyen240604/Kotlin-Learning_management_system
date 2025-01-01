package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.reportDTO.ReportOfSubjectRequestDTO
import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.gradeDTO.GradeDTO
import com.be.kotlin.grade.dto.reportDTO.ReportOfSubjectResponseDTO
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.GradeMapper
import com.be.kotlin.grade.mapper.StudyMapper
import com.be.kotlin.grade.model.Subject
import com.be.kotlin.grade.repository.*
import com.be.kotlin.grade.service.interf.IStudy
import org.apache.poi.ss.usermodel.CellType
import org.springframework.core.io.FileSystemResource
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import org.apache.poi.ss.usermodel.WorkbookFactory
//import org.apache.poi.xssf.usermodel.XSSFWorkbook
//import okhttp3.*

import org.springframework.stereotype.Service
import java.io.File
import java.util.*
import kotlin.collections.HashMap

@Service
class StudyService(
    private val studyRepository: StudyRepository,
    private val studyMapper: StudyMapper,
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository,
    private val classRepository: ClassRepository,
    private val userRepository: UserRepository,
    private val gradeRepository: GradeRepository,
    private val gradeMapper: GradeMapper,
    private val GradeService: GradeService
) : IStudy {
    override fun addStudyStudent(studyDTO: StudyDTO): Response {
        val newStudy = studyMapper.toStudy(studyDTO)

        val existingStudy = newStudy?.student?.let {
            studyRepository.findByStudentStudentIdAndSubjectIdAndSemester(
                it.studentId,
                newStudy.subject.id,
                newStudy.semester)
        }

        if (existingStudy != null) {
            throw AppException(ErrorCode.STUDY_EXISTED)
        }

        val studentId = newStudy?.student?.studentId ?: throw AppException(ErrorCode.STUDENT_ID_INVALID)

        if (!studentRepository.findById(studentId).isPresent) {
            throw AppException(ErrorCode.STUDENT_NOT_FOUND)
        }

        val subjectId = newStudy.subject.id ?: throw AppException(ErrorCode.SUBJECT_ID_INVALID)
        if (!subjectRepository.existsById(subjectId)) {
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)
        }

        val classId = newStudy.studyClass.id ?: throw AppException(ErrorCode.CLASS_ID_INVALID)
        if (!classRepository.existsById(classId)) {
            throw AppException(ErrorCode.CLASS_NOT_FOUND)
        }

        val userFaculty = newStudy.student.user.faculty
        val subjectFaculty = newStudy.subject.faculty
        val studentMajor = newStudy.student.major
        val subjectMajor = newStudy.subject.major

        if (userFaculty != subjectFaculty && studentMajor != subjectMajor) {
            throw AppException(ErrorCode.FACULTY_MAJOR_MISMATCH)
        }

        if (userFaculty != subjectFaculty) {
            throw AppException(ErrorCode.FACULTY_MISMATCH)
        }

        if (studentMajor != subjectMajor) {
            throw AppException(ErrorCode.MAJOR_MISMATCH)
        }

        studyRepository.save(newStudy)

        return Response(
            statusCode = 200,
            message = "Study added successfully",
            studyDTO = studyMapper.toStudyDTO(newStudy))
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
            if (GradeService.sanitizeColumnName(cellValue).equals("studentid", ignoreCase = true)) {
                studentIdCol = cellIndex
            } else {
                val weight = GradeService.extractWeight(cellValue) // Trích xuất trọng số từ tiêu đề
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
        val classId = matchResult.groupValues[2].toLong()
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

            // Tạo StudyDTO từ dữ liệu trong file Excel
            val studyDTO = StudyDTO(
                studentId = studentId,
                subjectId = subjectId,
                classId = classId,
                semester = semester
            )

            val response = addStudyStudent(studyDTO)

            if (response.statusCode != 200) {
                return Response(statusCode = 400, message = "Error adding study for student ID: $studentId")
//                responses.add(response)
//                errorMessages.add("Error adding study for student ID: $studentId")
            }
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
                val studyId = studyRepository.findStudyIdByClassIdAndSubjectIdAndStudentIdAndSemester(classId, subjectId, studentId, semester)

                if (studyId == null) {
                    errorMessages.add("Không tìm thấy study cho studentId $studentId ở hàng ${rowIndex + 1}.")
                    continue
                }

                // Tạo đối tượng GradeDTO
                val gradeDTO = GradeDTO(studyId = studyId, score = score, weight = weight)

                try {
                    val res = GradeService.addGrade(gradeDTO)
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
            message = finalMessage.toString(),
            listGradeDTO = responses.mapNotNull { it.gradeDTO }
        )
    }

    override fun deleteStudyStudent(studyIdD: Long): Response {
        if (!studyRepository.findById(studyIdD).isPresent)
            throw AppException(ErrorCode.STUDY_NOT_FOUND)

        val deletedStudy = studyRepository.findById(studyIdD).get()

        // Xóa Study
        studyRepository.deleteById(studyIdD)
        return Response(
            studyDTO = studyMapper.toStudyDTO(deletedStudy),
            statusCode = 200,
            message = "Study deleted successfully"
        )
    }

    override fun updateStudyStudent(study: StudyDTO): Response {

        val studyId: Long = study.id ?: throw AppException(ErrorCode.STUDY_ID_INVALID)

        if (!studyRepository.findById(studyId).isPresent){
            throw AppException(ErrorCode.STUDY_NOT_FOUND)
        }

        // Chuyển đổi từ DTO sang Entity và lưu vào cơ sở dữ liệu
        val updatedStudy = studyMapper.toStudy(study)
        val userFaculty = updatedStudy?.student?.user?.faculty
        val subjectFaculty = updatedStudy?.subject?.faculty
        val studentMajor = updatedStudy?.student?.major
        val subjectMajor = updatedStudy?.subject?.major

        if (userFaculty != subjectFaculty && studentMajor != subjectMajor) {
            throw AppException(ErrorCode.FACULTY_MAJOR_MISMATCH)
        }

        if (userFaculty != subjectFaculty) {
            throw AppException(ErrorCode.FACULTY_MISMATCH)
        }

        if (studentMajor != subjectMajor) {
            throw AppException(ErrorCode.MAJOR_MISMATCH)
        }
        if (updatedStudy != null) {
            studyRepository.save(updatedStudy)
        }

        return Response(
            studyDTO = study,
            statusCode = 200,
            message = "Study updated successfully"
        )
    }

    override fun getStudyById(studyIdD: Long): Response {
        val studyGot = studyRepository.findById(studyIdD)
            .orElseThrow { AppException(ErrorCode.STUDY_NOT_FOUND) }

        val studyDTO = studyMapper.toStudyDTO(studyGot)
        return Response(
            statusCode = 200,
            message = "Study found successfully",
            studyDTO = studyDTO
        )
    }

    fun generateCSV(username: String, studyList: List<StudyDTO>): File {
        val subjectMap = HashMap<String, Optional<Subject>>()
        val studentName = userRepository.findByUsername(username).get().name
        val studentId = studentRepository.findByUserUsername(username).get().studentId
        var totalCredits = 0

        for (study in studyList) {
            val subject = subjectRepository.findById(study.subjectId)
            totalCredits += subject.get().credits
            subjectMap[study.subjectId] = subject
        }

        // Title row for the CSV
        val title = "BẢNG ĐIỂM CỦA SINH VIÊN ${studentName.uppercase()} - MSSV ${studentId} HỌC KỲ ${studyList[0].semester}"
        val total = "TỔNG SỐ TÍN CHỈ ĐÃ HỌC: $totalCredits" ;

        // Content rows
        val header = listOf("STT", "MÃ MÔN HỌC", "TÊN MÔN HỌC", "SỐ TÍN CHỈ", "ĐIỂM THÀNH PHẦN", "ĐIỂM TRUNG BÌNH")
        val rows = studyList.mapIndexed { index, study ->
            val grades = study.gradeList.joinToString("\n") { grade ->
                "Điểm ${grade.weight}%: ${grade.score}"
            }
            listOf(
                index + 1, // STT
                study.subjectId, // MÃ MÔN HỌC
                subjectMap[study.subjectId]?.get()?.name ?: "N/A", // TÊN MÔN HỌC
                subjectMap[study.subjectId]?.get()?.credits ?: 0, // SỐ TÍN CHỈ
                grades, // ĐIỂM THÀNH PHẦN (multi-line)
                study.score // ĐIỂM TRUNG BÌNH
            )
        }

        val file = File("$studentName-$studentId-${studyList[0].semester}.csv")

        // Write the CSV file
        file.outputStream().bufferedWriter(Charsets.UTF_8).use { writer ->
            writer.write("\uFEFF") // Write UTF-8 BOM for Excel compatibility
            writer.write(title) // Write the title row
            writer.newLine()
            writer.write(header.joinToString(",")) // Write the header row
            writer.newLine()
            rows.forEach { row ->
                writer.write(row.joinToString(",") { cell ->
                    // Quote cells that contain commas or newlines
                    if (cell.toString().contains(",") || cell.toString().contains("\n")) {
                        "\"${cell.toString()}\""
                    } else {
                        cell.toString()
                    }
                })
                writer.newLine()
            }
            writer.newLine()
            writer.write(total)
        }

        return file
    }

    override fun getStudyByUsernameAndSemester(username: String, semester: Int, pageable: Pageable): Response {
        if (studyRepository.findByStudentUserUsernameAndSemester(username, semester, pageable).isEmpty)
            return Response(
                statusCode = 404,
                message = "Study not found"
            )

        val allStudies = studyRepository.findByStudentUserUsernameAndSemester(username, semester)
        var totalCredits = 0

        for (study in allStudies) {
            totalCredits += study.subject.credits
        }

        val studyPage = studyRepository.findByStudentUserUsernameAndSemester(username, semester, pageable)
        val studyDTOList = studyPage.content.map { studyMapper.toStudyDTO(it) }

        return Response(
            statusCode = 200,
            message = "Study record for semester $semester found successfully",
            listStudyDTO = studyDTOList,
            totalCredits = totalCredits,
            totalPages = studyPage.totalPages,
            currentPage = studyPage.number,
            totalElements = studyPage.totalElements
        )
    }

    override fun getStudyByUsernameAndSemesterCSV(username: String, semester: Int): Response {
        if (studyRepository.findByStudentUserUsernameAndSemester(username, semester).isEmpty())
            return Response(
                statusCode = 404,
                message = "Study not found"
            )

        val studyList = studyRepository.findByStudentUserUsernameAndSemester(username, semester)
        val studyDTOList = studyList.map { studyMapper.toStudyDTO(it) }
        val file = generateCSV(username, studyDTOList)
        val resource = FileSystemResource(file)

        return Response(
            statusCode = 200,
            message = "Study record for semester $semester found successfully",
            listStudyDTO = studyDTOList,
            file = resource
        )
    }

    override fun generateSubjectReport(report: ReportOfSubjectRequestDTO): Response{
        val subjectId = report.subjectId
        val semester = report.semester
        val year = report.year
        var reportResponseDTO: ReportOfSubjectResponseDTO
        val scoreRanges = listOf(
            "A+" to 9.5F..10.1F,
            "A" to 8.5F..9.5F,
            "B+" to 8.0F..8.5F,
            "B" to 7.0F..8.0F,
            "C+" to 6.5F..7.0F,
            "C" to 5.5F..6.5F,
            "D+" to 5.0F..5.5F,
            "D" to 4.0F..5.0F,
            "F" to 0F..4.0F
        )
        if (semester != null) {
            //SEMESTER
            val reportData = scoreRanges.associate { (grade, range) ->
                grade to studyRepository.countByScoreRangeAndSubjectIdAndSemester(
                    subjectId,
                    range.start,
                    range.endInclusive,
                    semester,
                    semester)
            }
            reportResponseDTO = ReportOfSubjectResponseDTO(
                subjectId = subjectId,
                semester = semester,
                totalStudies = reportData.values.sum(),
                data = reportData
            )
        } else if (year != null) {
            //YEAR
            val reportData = scoreRanges.associate { (grade, range) ->
                grade to studyRepository.countByScoreRangeAndSubjectIdAndSemester(
                    subjectId,
                    range.start,
                    range.endInclusive,
                    (year % 100) * 10 + 1,
                    (year % 100) * 10 + 3)
            }
            reportResponseDTO = ReportOfSubjectResponseDTO(
                subjectId = subjectId,
                year = year,
                totalStudies = reportData.values.sum(),
                data = reportData
            )
        } else{
            //ALL
            val reportData = scoreRanges.associate { (grade, range) ->
                grade to studyRepository.countByScoreRangeAndSubjectId(subjectId, range.start, range.endInclusive)
            }
            reportResponseDTO = ReportOfSubjectResponseDTO(
                subjectId = subjectId,
                totalStudies = reportData.values.sum(),
                data = reportData
            )
        }

        return Response(
            statusCode = 200,
            message = "Report for this subject has been generated successfully",
            reportSubjectDTO = reportResponseDTO
        )
    }

    override fun getGradeBySubjectIdAndSemester(subjectId: String, semester: Int): Response {
        // Find all study in semester
        val context = SecurityContextHolder.getContext()
        val username = context.authentication?.name

        val studyList = username?.let {
            val studies = studyRepository.findByStudentUserUsernameAndSemester(it, semester)
            if (studies.isEmpty()) {
                throw AppException(ErrorCode.STUDY_NOT_FOUND)
            }
            studies
        } ?: throw RuntimeException("No username found in SecurityContext")

        if (studyList.isEmpty())
            return Response(
                statusCode = 404,
                message = "Study not found"
            )

        val studyDTOList = studyList.map { studyMapper.toStudyDTO(it) }

        // Find study that have same subjectId
        val matchingStudies = studyDTOList.filter { it.subjectId == subjectId }

        if (matchingStudies.isEmpty()) {
            return Response(
                statusCode = 404,
                message = "Subject not found in studies"
            )
        }

        // Lấy studyId từ các study khớp
        val studyIds = matchingStudies.mapNotNull { it.id }

        val gradeList = gradeRepository.findGradeByStudyID(studyIds)

        // Chuyển đổi các entity sang DTO
        val gradeDTOs = gradeList.map { gradeEntity ->
            gradeMapper.toGradeDTO(gradeEntity)
        }

        // Trả về thông tin
        return Response(
            statusCode = 200,
            message = "Success",
            listStudyDTO = matchingStudies,
            listGradeDTO = gradeDTOs
        )
    }
}