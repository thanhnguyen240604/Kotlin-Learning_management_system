package com.be.kotlin.grade.dto.loginDTO

import jakarta.validation.constraints.NotBlank

data class ForgotPasswordRequest (
    @NotBlank(message = "Mail is required")
    val email: String
)