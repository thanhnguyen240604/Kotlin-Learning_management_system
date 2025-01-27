package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectRelationDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.SubjectRelationMapper
import com.be.kotlin.grade.repository.SubjectRelationRepository
import com.be.kotlin.grade.service.interf.ISubjectRelation
import org.springframework.stereotype.Service

@Service
class SubjectRelationService (
    private val subjectRelationRepository: SubjectRelationRepository,
    private val subjectRelationMapper: SubjectRelationMapper
): ISubjectRelation {
    override fun addSubjectRelation(subjectRelationDTO: SubjectRelationDTO): Response {
        val relation = subjectRelationRepository.findBySubjectRelationId(
            subjectRelationDTO.subjectId,
            subjectRelationDTO.faculty
        )
        if (relation != null) {
            throw AppException(ErrorCode.SUBJECT_RELATION_EXISTS)
        }

        val newRelation = subjectRelationMapper.toSubjectRelation(subjectRelationDTO)
        subjectRelationRepository.save(newRelation)

        return Response(
            statusCode = 200,
            message = "Subject relation added successfully",
            subjectRelationDTO = subjectRelationDTO
        )
    }

    override fun updateSubjectRelation(subjectRelationDTO: SubjectRelationDTO): Response {
        val relation = subjectRelationRepository.findBySubjectRelationId(
            subjectRelationDTO.subjectId,
            subjectRelationDTO.faculty
        )
        if (relation == null) {
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)
        }

        val updatedRelation = subjectRelationMapper.toSubjectRelation(subjectRelationDTO)
        subjectRelationRepository.save(updatedRelation)

        return Response(
            statusCode = 200,
            message = "Subject relation updated successfully",
            subjectRelationDTO = subjectRelationDTO
        )
    }

    override fun deleteSubjectRelation(subjectRelationDTO: SubjectRelationDTO): Response {
        val relation = subjectRelationRepository.findBySubjectRelationId(
            subjectRelationDTO.subjectId,
            subjectRelationDTO.faculty
        )
        if (relation == null) {
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)
        }

        subjectRelationRepository.delete(relation)
        return Response(
            statusCode = 200,
            message = "Subject relation deleted successfully",
            subjectRelationDTO = subjectRelationDTO
        )
    }
}