package com.be.kotlin.grade.dto.userDTO

import jakarta.validation.constraints.NotBlank

data class UserRequestDTO (
    val id: Long? = null,
    @NotBlank(message = "Name is required")
    val name: String,
    val faculty: String? = null,
    val role: String? = null,
    @NotBlank(message = "Username is required")
    val username: String,
    @NotBlank(message = "Password is required")
    val password: String
)