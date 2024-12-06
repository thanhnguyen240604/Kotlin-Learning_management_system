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
import com.be.kotlin.grade.service.interf.GradeInterface
import org.springframework.stereotype.Service

@Service
class GradeImplement(
    private val gradeRepository: GradeRepository,
    private val gradeMapper: GradeMapper,
    private val studyRepository: StudyRepository
) : GradeInterface {
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
        study.gradesList.add(newGrade)
        study.score = caculateAverageScore(study.gradesList)
        studyRepository.save(study)
        val savedGradeDTO = gradeMapper.toGradeDTO(newGrade)


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
}