package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.securityDTO.AuthenticateDTO
import com.be.kotlin.grade.dto.securityDTO.IntrospectDTO
import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.loginDTO.ForgotPasswordRequest
import com.be.kotlin.grade.dto.loginDTO.OtpVerificationRequest
import com.be.kotlin.grade.dto.loginDTO.ResetPasswordRequest
import com.nimbusds.jose.JOSEException
import java.text.ParseException

interface IAuthenticate {
    //Login
    fun authenticate(request: AuthenticateDTO): Response
    @Throws(JOSEException::class, ParseException::class)
    fun introspect(request: IntrospectDTO): Response

    //OTP
    fun sendForgotPasswordEmail(request: ForgotPasswordRequest): Response
    fun verifyOTP(request: OtpVerificationRequest): Response
    fun resetPassword(request: ResetPasswordRequest): Response
}