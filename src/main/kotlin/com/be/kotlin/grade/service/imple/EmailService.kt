package com.be.kotlin.grade.service.imple

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService (
    private val mailSender: JavaMailSender
){
    fun sendOtpEmail(toEmail: String, otp: String) {
        val message = SimpleMailMessage()
        message.setTo(toEmail)
        message.subject = "Your OTP Code"
        message.text = "Your OTP is $otp"
        mailSender.send(message)
    }
}