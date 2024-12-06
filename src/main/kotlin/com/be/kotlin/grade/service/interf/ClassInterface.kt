package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studentDTO.StudentResponseDto
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import org.springframework.web.bind.annotation.RequestParam

interface ClassInterface {
    fun addClass(classDTO: ClassDTO): Response
    fun updateClass(classDTO: ClassDTO): Response
    fun deleteClass(id: Long): Response
    fun getHighestGradeStudent(@RequestParam classId : Long) : MutableList<StudentResponseDto>
    fun getClassById(id: Long): Response
    fun getAllClasses(page: Int, size: Int): Response
    fun getAllMyClasses(page: Int, size: Int, studentId: Long): Response
}
