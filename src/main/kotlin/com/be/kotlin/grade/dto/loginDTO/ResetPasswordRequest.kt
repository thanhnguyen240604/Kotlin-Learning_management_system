package com.be.kotlin.grade.dto.loginDTO

import jakarta.validation.constraints.NotBlank

data class ResetPasswordRequest (
    @NotBlank(message = "Please try again later")
    val email: String,
    @NotBlank(message = "Please enter your password")
    val newPassword: String,
    @NotBlank(message = "Please confirm your password again")
    val confirmPassword: String
)