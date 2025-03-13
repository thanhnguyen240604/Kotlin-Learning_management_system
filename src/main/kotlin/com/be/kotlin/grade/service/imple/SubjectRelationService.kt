package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectRelationDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.SubjectRelationMapper
import com.be.kotlin.grade.model.enums.CreditType
import com.be.kotlin.grade.repository.SubjectRelationRepository
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.service.interf.ISubjectRelation
import org.springframework.stereotype.Service

@Service
class SubjectRelationService (
    private val subjectRelationRepository: SubjectRelationRepository,
    private val subjectRelationMapper: SubjectRelationMapper,
    private val subjectRepository: SubjectRepository
): ISubjectRelation {
    override fun addSubjectRelation(subjectRelationDTO: SubjectRelationDTO): Response {
        val relation = subjectRelationRepository.findById(
            subjectRelationDTO.subjectId,
            subjectRelationDTO.faculty
        )
        if (!subjectRepository.findById(subjectRelationDTO.subjectId).isPresent) {
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)
        }
        if (relation != null) {
            throw AppException(ErrorCode.SUBJECT_RELATION_EXISTS)
        }
        subjectRelationDTO.preSubjectId?.let {
            if (subjectRelationDTO.subjectId == subjectRelationDTO.preSubjectId) {
                throw AppException(ErrorCode.PRE_SUBJECT_INVALID)
            }
        }
        subjectRelationDTO.postSubjectId?.let {
            if (subjectRelationDTO.subjectId == subjectRelationDTO.postSubjectId) {
                throw AppException(ErrorCode.POST_SUBJECT_INVALID)
            }
        }

        val newRelation = subjectRelationMapper.toSubjectRelation(subjectRelationDTO)
        if (newRelation != null) {
            subjectRelationRepository.save(newRelation)
        }

        return Response(
            statusCode = 200,
            message = "Subject relation added successfully",
            subjectRelationDTO = subjectRelationDTO
        )
    }

    override fun updateSubjectRelation(subjectRelationDTO: SubjectRelationDTO): Response {
        val relation = subjectRelationRepository.findById(
            subjectRelationDTO.subjectId,
            subjectRelationDTO.faculty
        )
        if (!subjectRepository.findById(subjectRelationDTO.subjectId).isPresent) {
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)
        }
        if (relation == null) {
            throw AppException(ErrorCode.SUBJECT_RELATION_NOT_FOUND)
        }
        subjectRelationDTO.preSubjectId?.let {
            if (subjectRelationDTO.subjectId == subjectRelationDTO.preSubjectId) {
                throw AppException(ErrorCode.PRE_SUBJECT_INVALID)
            }
        }
        subjectRelationDTO.postSubjectId?.let {
            if (subjectRelationDTO.subjectId == subjectRelationDTO.postSubjectId) {
                throw AppException(ErrorCode.POST_SUBJECT_INVALID)
            }
        }

        subjectRelationDTO.creditType?.let { creditType -> relation.creditType = CreditType.valueOf(creditType) }
        subjectRelationDTO.preSubjectId?.let {subject -> relation.preSubjectId = subject}
        subjectRelationDTO.postSubjectId?.let {subject -> relation.postSubject = subject}
        subjectRelationRepository.save(relation)
        return Response(
            statusCode = 200,
            message = "Subject relation updated successfully",
            subjectRelationDTO = subjectRelationMapper.toSubjectRelationDTO(relation)
        )
    }

    override fun deleteSubjectRelation(subjectRelationDTO: SubjectRelationDTO): Response {
        val relation = subjectRelationRepository.findById(
            subjectRelationDTO.subjectId,
            subjectRelationDTO.faculty
        )
        if (!subjectRepository.findById(subjectRelationDTO.subjectId).isPresent) {
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)
        }
        if (relation == null) {
            throw AppException(ErrorCode.SUBJECT_RELATION_NOT_FOUND)
        }

        subjectRelationRepository.delete(relation)
        return Response(
            statusCode = 200,
            message = "Subject relation deleted successfully"
        )
    }
}