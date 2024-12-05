package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.Student
import com.be.kotlin.grade.dto.StudentDTO.StudentResponseDto
import org.springframework.web.bind.annotation.RequestParam

interface ClassInterface {
    fun getHighestGradeStudent(@RequestParam classId : Long) : MutableList<StudentResponseDto>
}