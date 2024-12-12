package com.be.kotlin.grade.dto.loginDTO

import jakarta.validation.constraints.NotBlank

class OtpVerificationRequest (
    @NotBlank(message = "Mail is required")
    val email: String,
    @NotBlank(message = "OTP is required")
    val otp: String
)