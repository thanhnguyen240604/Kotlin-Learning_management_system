package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectIdDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import org.springframework.web.bind.annotation.RequestBody

interface SubjectInterface {
    fun addSubject(@RequestBody subject: SubjectDTO): Response
    fun updateSubject(@RequestBody subject: SubjectDTO): Response
    fun deleteSubject(@RequestBody subject: SubjectIdDTO): Response
    fun getSubjectById(subject: SubjectIdDTO): Response
}