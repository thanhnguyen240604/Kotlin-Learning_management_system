package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.SubjectDTO
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.service.interf.SubjectInterface

import org.springframework.stereotype.Service

@Service
class SubjectImplement (
    private val subjectRepository: SubjectRepository,
): SubjectInterface {
    override fun addSubject(subject: SubjectDTO): Response {
        return Response(
            statusCode = 200,
            message = "Subject added successfully")
    }
}