package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.SubjectDTO
import com.be.kotlin.grade.mapper.SubjectMapper
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.service.interf.SubjectInterface
import org.springframework.stereotype.Service

@Service
class SubjectImplement(private val subjectRepository: SubjectRepository, private val subjectMapper: SubjectMapper): SubjectInterface {
    override fun addSubject(subject: SubjectDTO): Response {
        val newSubject = subjectMapper.toSubject(subject)
        subjectRepository.save(newSubject);

        return Response(
            statusCode = 200,
            message = "Subject added successfully")
    }

    override fun deleteSubject(subject: SubjectDTO): Response {
        if (!subjectRepository.findById(subject.id).isPresent)
            return Response(
                statusCode = 404,
                message = "Subject not found"
            )

        subjectRepository.deleteById(subject.id);
        return Response(
            statusCode = 200,
            message = "Subject deleted successfully"
        )
    }

    override fun updateSubject(subject: SubjectDTO): Response {
        if (!subjectRepository.findById(subject.id).isPresent)
            return Response(
                statusCode = 404,
                message = "Subject not found"
            )

        val newSubject = subjectMapper.toSubject(subject)
        subjectRepository.save(newSubject);

        return Response(
            statusCode = 200,
            message = "Subject updated successfully")
    }
}