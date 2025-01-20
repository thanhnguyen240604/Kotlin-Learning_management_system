package com.be.kotlin.grade.dto.userDTO

import jakarta.validation.constraints.NotBlank

class UserUpdateRequestDTO (
    @NotBlank(message = "Name is required")
    val name: String,
    @NotBlank(message = "Faculty is required")
    val faculty: String? = null,
    @NotBlank(message = "Username is required")
    val username: String
)