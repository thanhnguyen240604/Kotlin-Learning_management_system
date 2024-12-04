package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.SubjectDTO.DeleteSubjectDTO
import com.be.kotlin.grade.dto.SubjectDTO.FullSubjectDTO
import org.springframework.web.bind.annotation.RequestBody

interface SubjectInterface {
    fun addSubject(@RequestBody subject: FullSubjectDTO): Response
    fun updateSubject(@RequestBody subject: FullSubjectDTO): Response
    fun deleteSubject(@RequestBody subject: DeleteSubjectDTO): Response
}