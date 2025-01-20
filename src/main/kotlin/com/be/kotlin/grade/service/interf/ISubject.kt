package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectIdDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectRegisterDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

interface ISubject {
    fun addSubject(subject: SubjectDTO): Response
    fun updateSubject(subject: SubjectDTO): Response
    fun deleteSubject(subject: SubjectIdDTO): Response
    fun getSubjectById(subject: SubjectIdDTO): Response
    fun getAllSubjects(page: Int, size: Int): Response
    fun getNextSemester(): Response
    fun registerSubject(subjectRegister: SubjectRegisterDTO): Response
}