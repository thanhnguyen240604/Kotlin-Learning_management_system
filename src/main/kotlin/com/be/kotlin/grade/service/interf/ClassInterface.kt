package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.dto.classDTO.ClassIdDTO

interface ClassInterface {
    fun addClass(classDTO: ClassDTO): Response
    fun updateClass(classDTO: ClassDTO): Response
    fun deleteClass(classIdDTO: ClassIdDTO): Response
}
