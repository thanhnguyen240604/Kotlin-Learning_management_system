package com.be.kotlin.grade.dto.UserDto

import jakarta.validation.constraints.NotBlank

class UserUpdateRequestDTO (
    @NotBlank(message = "Name is required")
    var name: String,
    @NotBlank(message = "Faculty is required")
    var faculty: String? = null,
    @NotBlank(message = "Username is required")
    val username: String
)