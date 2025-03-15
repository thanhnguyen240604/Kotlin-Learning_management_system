package com.be.kotlin.grade.dto.userDTO

import com.be.kotlin.grade.dto.studentDTO.StudentDTO
import com.be.kotlin.grade.model.Student
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserResponseDTO (
    var id: Long? = null,
    var name: String? = null,
    var faculty: String? = null,
    val role: String? = null,
    val username: String? = null,
    val studentDTO: StudentDTO? = null,
)
