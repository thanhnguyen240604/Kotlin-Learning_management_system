package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.classDTO.ClassDTO

interface ClassInterface {
    fun addClass(classDTO: ClassDTO): Response
    fun updateClass(classDTO: ClassDTO): Response
    fun deleteClass(id: Long): Response
    fun getClassById(id: Long): Response
    fun getAllClasses(page: Int, size: Int): Response
    fun getAllMyClasses(page: Int, size: Int, studentId: Long): Response
}
