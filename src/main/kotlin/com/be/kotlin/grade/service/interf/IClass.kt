package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.dto.classDTO.UpdateClassDTO
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RequestParam

interface IClass {
    fun addClass(classDTO: ClassDTO): Response
    fun updateClass(updateClassDTO: UpdateClassDTO): Response
    fun deleteClass(id: Long): Response
    fun getHighestGradeStudent(@RequestParam classId : Long) : Response
    fun getClassById(id: Long): Response
    fun getAllClasses(page: Int, size: Int): Response
    fun getAllStudentClasses(page: Int, size: Int): Response
    fun getAllLecturerClasses(pageable: Pageable): Response
    fun registerLecturerToClass(classId: Long): Response
}
