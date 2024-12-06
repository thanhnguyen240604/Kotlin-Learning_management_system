package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studentDTO.StudentDTO
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO

interface StudentInterface {
    fun register(userDTO: UserRequestDTO, studentDTO: StudentDTO): Response
}