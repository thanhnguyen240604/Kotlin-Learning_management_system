package com.be.kotlin.grade.dto.userDTO

import jakarta.validation.constraints.NotBlank

data class UserRequestDTO (
    var id: Long? = null,
    @NotBlank(message = "Name is required")
    var name: String,
    var faculty: String? = null,
    val role: String? = null,
    @NotBlank(message = "Username is required")
    val username: String,
    @NotBlank(message = "Password is required")
    val password: String
)