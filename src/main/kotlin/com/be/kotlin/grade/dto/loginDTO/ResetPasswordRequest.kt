package com.be.kotlin.grade.dto.loginDTO

import jakarta.validation.constraints.NotBlank

data class ResetPasswordRequest (
    @NotBlank(message = "Mail is required")
    val email: String,
    @NotBlank(message = "New password is required")
    val newPassword: String
)