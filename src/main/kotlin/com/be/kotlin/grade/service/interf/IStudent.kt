package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studentDTO.StudentDTO
import com.be.kotlin.grade.dto.studentDTO.StudentUpdateDTO
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO

interface IStudent {
    fun register(userDTO: UserRequestDTO, studentDTO: StudentDTO): Response
    fun updateStudent(studentUpdateDTO: StudentUpdateDTO, username : String): Response
    fun calculateGPA(semester: Int): Response
    fun getStudentList(classId: Long): Response

//    fun getStudentById(userId: Long): Response
}