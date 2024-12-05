package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.Grade
import com.be.kotlin.grade.Study
import com.be.kotlin.grade.dto.gradeDTO.Grade_DTO
import org.springframework.stereotype.Component

@Component
class GradeMapper {
    fun toGrade(gradeDTO: Grade_DTO ): Grade {
        return Grade(
            id = gradeDTO.id,
            score = gradeDTO.score,
            weight = gradeDTO.weight,
            studyId = gradeDTO.studyId
        )
    }
    fun toGradeDTO(grade: Grade): Grade_DTO {
        return Grade_DTO(
            id = grade.id,
            score = grade.score,
            weight = grade.weight,
            studyId = grade.studyId
        )
    }
}