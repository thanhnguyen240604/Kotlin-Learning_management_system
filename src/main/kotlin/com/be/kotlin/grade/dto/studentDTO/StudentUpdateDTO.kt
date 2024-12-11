package com.be.kotlin.grade.dto.studentDTO

import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.dto.userDTO.UserUpdateRequestDTO

data class StudentUpdateDTO(
    val studentName: String,
    val faculty: String,
    val studentId : Long,
)
