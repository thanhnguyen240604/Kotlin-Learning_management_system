package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.model.Grade
import com.be.kotlin.grade.dto.gradeDTO.GradeDTO
import org.springframework.stereotype.Component

@Component
class GradeMapper {
    fun toGrade(gradeDTO: GradeDTO ): Grade {
        return Grade(
            id = gradeDTO.id,
            score = gradeDTO.score,
            weight = gradeDTO.weight,
            studyId = gradeDTO.studyId
        )
    }
    fun toGradeDTO(grade: Grade): GradeDTO {
        return GradeDTO(
            id = grade.id,
            score = grade.score,
            weight = grade.weight,
            studyId = grade.studyId
        )
    }
}