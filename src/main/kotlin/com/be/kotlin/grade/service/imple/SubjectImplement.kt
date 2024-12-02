package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.SubjectDTO
import com.be.kotlin.grade.service.interf.SubjectInterface
import hcmut.example.gradeportalbe.dto.Response
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SubjectImplement: SubjectInterface {
    override fun addSubject(subject: SubjectDTO): Response {
        return Response(
            statusCode = 200,
            message = "Subject added successfully")
    }
}