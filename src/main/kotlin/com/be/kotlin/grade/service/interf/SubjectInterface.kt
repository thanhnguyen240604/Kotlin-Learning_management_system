package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.SubjectDTO
import com.be.kotlin.grade.repository.SubjectRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

interface SubjectInterface {
    fun addSubject(@RequestBody subject: SubjectDTO): Response
    fun updateSubject(@RequestBody subject: SubjectDTO): Response
    fun deleteSubject(@RequestBody subject: SubjectDTO): Response
}