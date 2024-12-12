package com.be.kotlin.grade.service.imple

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender
) {
    fun sendOtpEmail(toEmail: String, otp: String) {
        val mimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true, "UTF-8") // Đảm bảo sử dụng UTF-8

        helper.setTo(toEmail)
        helper.setSubject("Xác nhận OTP - Hệ thống tra cứu điểm")

        val content = """
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; background-color: #f9f9f9;">
                <div style="max-width: 600px; margin: 20px auto; padding: 20px; background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
                    <h2 style="color: #0066cc; text-align: center;">Xác nhận OTP</h2>
                    <p>Kính chào,</p>
                    <p>Đây là mã OTP để đặt lại mật khẩu của bạn:</p>
                    <div style="text-align: center; margin: 20px 0;">
                        <span style="display: inline-block; font-size: 24px; font-weight: bold; color: #d9534f; padding: 10px 20px; border: 2px dashed #d9534f; border-radius: 5px;">
                            $otp
                        </span>
                    </div>
                    <p>Mã OTP này sẽ hết hạn sau <b>3 phút</b>. Vui lòng không chia sẻ mã này với bất kỳ ai.</p>
                    <p>Trân trọng,<br>Đội ngũ hỗ trợ - Hệ thống Tra cứu Điểm</p>
                </div>
            </body>
            </html>
        """.trimIndent()

        helper.setText(content, true) // true để gửi nội dung HTML
        mailSender.send(mimeMessage)
    }
}
