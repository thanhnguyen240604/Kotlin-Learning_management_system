package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.dto.subjectDTO.SubjectRelationDTO
import com.be.kotlin.grade.model.SubjectRelation
import com.be.kotlin.grade.model.SubjectRelationId
import com.be.kotlin.grade.model.enums.CreditType
import org.springframework.stereotype.Component

@Component
class SubjectRelationMapper {
    fun toSubjectRelation(request: SubjectRelationDTO): SubjectRelation? {
        return request.creditType?.let { CreditType.valueOf(it) }?.let {
            SubjectRelation(
                id = SubjectRelationId(request.subjectId, request.faculty),
                creditType = it,
                preSubjectId = request.preSubjectId,
                postSubject = request.postSubject
            )
        }
    }

    fun toSubjectRelationDTO(subjectRelation: SubjectRelation): SubjectRelationDTO {
        return SubjectRelationDTO(
            subjectId = subjectRelation.id.subjectId,
            faculty = subjectRelation.id.faculty,
            creditType = subjectRelation.creditType.toString(),
            preSubjectId = subjectRelation.preSubjectId,
            postSubject = subjectRelation.postSubject
        )
    }
}