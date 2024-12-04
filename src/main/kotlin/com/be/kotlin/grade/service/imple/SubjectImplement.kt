package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectIdDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.SubjectMapper
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.service.interf.SubjectInterface
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class SubjectImplement(
    private val subjectRepository: SubjectRepository,
    private val subjectMapper: SubjectMapper,
): SubjectInterface {
    override fun addSubject(subject: SubjectDTO): Response {
        if (subjectRepository.findById(subject.id).isPresent) {
            throw AppException(ErrorCode.SUBJECT_EXISTED)
        }

        val newSubject = subjectMapper.toSubject(subject)
        subjectRepository.save(newSubject)

        return Response(
            fullSubjectDTO = subject,
            statusCode = 200,
            message = "Subject added successfully")
    }

    override fun deleteSubject(@RequestBody subject: SubjectIdDTO): Response {
        if (!subjectRepository.findById(subject.id).isPresent)
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)

        val deletedSubject = subjectRepository.findById(subject.id).get()

        subjectRepository.deleteById(subject.id)
        return Response(
            fullSubjectDTO = subjectMapper.toFullSubjectDTO(deletedSubject),
            statusCode = 200,
            message = "Subject deleted successfully"
        )
    }

    override fun updateSubject(subject: SubjectDTO): Response {
        if (!subjectRepository.findById(subject.id).isPresent)
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)

        val updatedSubject = subjectMapper.toSubject(subject)
        subjectRepository.save(updatedSubject)

        return Response(
            fullSubjectDTO = subject,
            statusCode = 200,
            message = "Subject updated successfully")
    }
}