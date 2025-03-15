package com.be.kotlin.grade.dto.userDTO

import jakarta.validation.constraints.NotBlank

class UserUpdateRequestDTO (
    @NotBlank(message = "Username is required")
    val username: String,
    val name: String? = null,
    val faculty: String? = null,
    val major: String? = null
)