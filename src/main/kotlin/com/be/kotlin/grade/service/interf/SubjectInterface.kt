package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.SubjectDTO

interface SubjectInterface {
    fun addSubject(subject: SubjectDTO): Response
}