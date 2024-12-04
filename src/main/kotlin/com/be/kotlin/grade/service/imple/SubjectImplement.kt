package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.DeleteSubjectDTO
import com.be.kotlin.grade.dto.subjectDTO.FullSubjectDTO
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
    override fun addSubject(subject: FullSubjectDTO): Response {
        if (subjectRepository.findById(subject.id).isPresent) {
            return Response(
                statusCode = 300,
                message = "Subject already exists"
            )
        }

        val newSubject = subjectMapper.toSubject(subject)
        subjectRepository.save(newSubject)

        return Response(
            fullSubjectDTO = subject,
            statusCode = 200,
            message = "Subject added successfully")
    }

    override fun deleteSubject(@RequestBody subject: DeleteSubjectDTO): Response {
        if (!subjectRepository.findById(subject.id).isPresent)
            return Response(
                statusCode = 404,
                message = "Subject not found"
            )

        val deletedSubject = subjectRepository.findById(subject.id).get()

        subjectRepository.deleteById(subject.id)
        return Response(
            fullSubjectDTO = subjectMapper.toFullSubjectDTO(deletedSubject),
            statusCode = 200,
            message = "Subject deleted successfully"
        )
    }

    override fun updateSubject(subject: FullSubjectDTO): Response {
        if (!subjectRepository.findById(subject.id).isPresent)
            return Response(
                statusCode = 404,
                message = "Subject not found"
            )

        val updatedSubject = subjectMapper.toSubject(subject)
        subjectRepository.save(updatedSubject)

        return Response(
            fullSubjectDTO = subject,
            statusCode = 200,
            message = "Subject updated successfully")
    }
}