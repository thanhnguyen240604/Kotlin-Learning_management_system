package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.gradeDTO.GradeDTO
import com.be.kotlin.grade.dto.gradeDTO.GradeIdDTO

interface GradeInterface {
    fun addGrade(grade: GradeDTO): Response
    fun deleteGrade(grade: GradeIdDTO): Response
    fun updateGrade(grade: GradeIdDTO): Response
    fun getGradeById(id: Long): Response
    fun getGradeBySubjectIdAndSemester(subjectId: String, semester: Int): Response
}