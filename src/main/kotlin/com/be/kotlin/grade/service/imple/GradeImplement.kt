package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.gradeDTO.Grade_DTO
import com.be.kotlin.grade.dto.gradeDTO.Grade_DTO_ID
import com.be.kotlin.grade.mapper.GradeMapper
import com.be.kotlin.grade.repository.GradeRepository
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.service.interf.GradeInterface
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

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

    override fun addGrade(@RequestBody grade: Grade_DTO): Response {
        val gradeStudyId = grade.studyId?: return Response(
            statusCode = 400,
            message = "Study ID cannot be null"
        )
        val study = studyRepository.findById(gradeStudyId).orElse(null)
            ?:return Response(
                statusCode = 404,
                message = "Study not found"
            )

        if (!isScoreValid(grade.score)) {
            return Response(
                statusCode = 400,
                message = "Score must be between 0 and 10"
            )
        }
        //Kiểm tra weight
        val weightValidationResult = isWeightValid(gradeStudyId, grade.weight)
        if (!weightValidationResult.first) {
            return Response(400, weightValidationResult.second)
        }

        val newGrade = gradeMapper.toGrade(grade)
        study.gradesList.add(newGrade)
        studyRepository.save(study)
        val savedGradeDTO = gradeMapper.toGradeDTO(newGrade)
        return Response(
            gradeDTO = savedGradeDTO,
            statusCode = 200,
            message = "Grade added successfully"
        )
    }


    override fun deleteGrade(@RequestBody grade: Grade_DTO_ID): Response {
        val gradeStudyId = grade.studyId ?: return Response(
            statusCode = 400,
            message = "Study ID cannot be null"
        )
        val gradeId = grade.id ?: return Response(
            statusCode = 400,
            message = "Grade ID cannot be null"
        )
        // Kiểm tra xem điểm có tồn tại không
        val optionalGrade = gradeRepository.findById(gradeId)
        if (!optionalGrade.isPresent) {
            return Response(
                statusCode = 404,
                message = "Grade not found"
            )
        }
        // Kiểm tra xem điểm có thuộc về studyId không
        val deletedGrade = optionalGrade.get()
        if (deletedGrade.studyId != gradeStudyId) {
            return Response(
                statusCode = 403,
                message = "Grade does not belong to the specified study"
            )
        }
        // Xóa điểm
        gradeRepository.deleteById(gradeId)
        // Cập nhật danh sách điểm trong Study
        val study = studyRepository.findById(gradeStudyId).orElse(null)
        study?.gradesList?.remove(deletedGrade)
        studyRepository.save(study)
        return Response(
            gradeDTO = gradeMapper.toGradeDTO(deletedGrade),
            statusCode = 200,
            message = "Grade deleted successfully"
        )
    }

    override fun updateGrade(@RequestBody grade: Grade_DTO_ID): Response {
        val gradeId = grade.id ?: return Response(
            statusCode = 400,
            message = "Grade ID cannot be null"
        )
        val gradeStudyId = grade.studyId ?: return Response(
            statusCode = 400,
            message = "Study ID cannot be null"
        )
        // Kiểm tra xem điểm có tồn tại không
        val optionalGrade = gradeRepository.findById(gradeId)
        if (!optionalGrade.isPresent) {
            return Response(
                statusCode = 404,
                message = "Grade not found"
            )
        }
        // Kiểm tra xem điểm có thuộc về studyId không
        val existingGrade = optionalGrade.get()
        if (existingGrade.studyId != gradeStudyId) {
            return Response(
                statusCode = 403,
                message = "Grade does not belong to the specified study"
            )
        }
        //Kiểm tra Score
        if (!isScoreValid(grade.score)) {
            return Response(
                statusCode = 400,
                message = "Score must be between 0 and 10"
            )
        }
        //Kiểm tra Weight
        val weightValidationResult = isWeightValid(gradeStudyId, grade.weight, existingGrade.weight)
        if (!weightValidationResult.first) {
            return Response(400, weightValidationResult.second)
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
        studyRepository.save(study)
        return Response(
            gradeDTO = gradeMapper.toGradeDTO(updatedGrade),
            statusCode = 200,
            message = "Grade updated successfully"
        )
    }
}