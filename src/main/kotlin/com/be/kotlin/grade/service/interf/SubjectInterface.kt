package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.SubjectDTO
import hcmut.example.gradeportalbe.dto.Response
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

interface SubjectInterface {
    fun addSubject(@RequestBody subject: SubjectDTO): Response
}