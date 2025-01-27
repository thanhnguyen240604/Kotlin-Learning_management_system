package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.dto.subjectDTO.SubjectRelationDTO
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.dto.userDTO.UserResponseDTO
import com.be.kotlin.grade.model.Subject
import com.be.kotlin.grade.model.SubjectRelation
import com.be.kotlin.grade.model.SubjectRelationId
import com.be.kotlin.grade.model.User
import com.be.kotlin.grade.model.enums.CreditType
import org.springframework.stereotype.Component

@Component
class SubjectRelationMapper {
    fun toSubjectRelation(request: SubjectRelationDTO): SubjectRelation {
        return SubjectRelation(
            id = SubjectRelationId(request.subjectId, request.faculty),
            creditType = CreditType.valueOf(request.creditType),
            preSubjectId = request.preSubjectId,
            postSubject = request.postSubject
        )
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