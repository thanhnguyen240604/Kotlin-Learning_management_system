package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.gradeDTO.Grade_DTO
import com.be.kotlin.grade.dto.gradeDTO.Grade_DTO_ID

import org.springframework.web.bind.annotation.RequestBody

interface GradeInterface {
    fun addGrade(grade: Grade_DTO): Response
    fun deleteGrade(grade: Grade_DTO_ID): Response
    fun updateGrade(grade: Grade_DTO_ID): Response
}