package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.loginDTO.ForgotPasswordRequest
import com.be.kotlin.grade.dto.loginDTO.OtpVerificationRequest
import com.be.kotlin.grade.dto.loginDTO.ResetPasswordRequest
import com.be.kotlin.grade.dto.securityDTO.AuthenticateDTO
import com.be.kotlin.grade.dto.securityDTO.IntrospectDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.model.User
import com.be.kotlin.grade.repository.UserRepository
import com.be.kotlin.grade.service.interf.IAuthenticate
import com.nimbusds.jose.*
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class AuthenticateService(
    private val userRepository: UserRepository,
    @Value("\${jwt.signerKey}") private val signerKey: String,
    private val emailService: EmailService
) : IAuthenticate {

    override fun authenticate(request: AuthenticateDTO): Response {
        val specialUsers = System.getProperty("SPECIAL_USERS")?.split(",") ?: emptyList()

        val sanitizedUsername = request.username.trim()

        // Kiểm tra đuôi email, bỏ qua nếu thuộc danh sách ngoại lệ
        if (!specialUsers.contains(sanitizedUsername) && !sanitizedUsername.endsWith("@hcmut.edu.vn")) {
            throw AppException(ErrorCode.UNAUTHENTICATED_USERNAME_DOMAIN)
        }

        val user = userRepository.findByUsername(request.username)
            .orElseThrow { AppException(ErrorCode.UNAUTHENTICATED_USERNAME_PASSWORD) }

        val passwordEncoder = BCryptPasswordEncoder(5)

        val authenticated = passwordEncoder.matches(request.password, user.password)
        if (!authenticated) {
            throw AppException(ErrorCode.UNAUTHENTICATED_USERNAME_PASSWORD)
        }

        val token = generateToken(user)

        return Response(
            statusCode = 200,
            message = "Login successfully",
            authenticated = authenticated,
            token = token,
        )
    }

    override fun introspect(request: IntrospectDTO): Response {
        val token = request.token

        val verifier = MACVerifier(signerKey.toByteArray())
        val signedJWT = SignedJWT.parse(token)

        val expirationTime = signedJWT.jwtClaimsSet.expirationTime
        val verified = signedJWT.verify(verifier)

        return Response(
            statusCode = 200,
            message = if (verified && expirationTime.after(Date())) "Token is valid" else "Token is invalid",
            token = null
        )
    }

    override fun sendForgotPasswordEmail(request: ForgotPasswordRequest): Response {
        val user = userRepository.findByUsername(request.email)
            .orElseThrow { AppException(ErrorCode.UNAUTHENTICATED_USERNAME) }
        val otp = generateOTP()
        user.otp = otp
        user.otpExpiry = LocalDateTime.now().plusMinutes(3)
        userRepository.save(user)

        emailService.sendOtpEmail(request.email, otp)
        return Response(
            statusCode = 200,
            message = "Password reset email sent successfully",
            forgotPasswordDTO = request
        )
    }

    override fun verifyOTP(request: OtpVerificationRequest): Response {
        val user = userRepository.findByUsername(request.email)
            .orElseThrow { AppException(ErrorCode.UNAUTHENTICATED_USERNAME) }
        if (user.otpExpiry!! < LocalDateTime.now()) {
            throw AppException(ErrorCode.OTP_EXPIRED)
        }
        if (user.otp != request.otp) {
            throw AppException(ErrorCode.OTP_INVALID)
        }
        return Response(
            statusCode = 200,
            message = "OTP verified successfully",
            forgotPasswordDTO = ForgotPasswordRequest(user.username)
        )
    }

    override fun resetPassword(request: ResetPasswordRequest): Response {
        val user = userRepository.findByUsername(request.email)
            .orElseThrow { AppException(ErrorCode.USER_NOT_FOUND) }
        if (request.confirmPassword != request.newPassword) {
            throw AppException(ErrorCode.PASSWORD_NOT_MATCH)
        }

        val passwordEncoder = BCryptPasswordEncoder(5)
        val encodedNewPassword = passwordEncoder.encode(request.newPassword)
        if (user.password != encodedNewPassword) {
            throw AppException(ErrorCode.PASSWORD_NOT_CHANGE)
        } else {
            user.password = encodedNewPassword
        }
        userRepository.save(user)
        return Response(
            statusCode = 200,
            message = "Password reset successfully",
            forgotPasswordDTO = ForgotPasswordRequest(user.username)
        )
    }

    private fun generateToken(user: User): String {
        val jwsHeader = JWSHeader(JWSAlgorithm.HS256)

        val jwtClaimsSet = JWTClaimsSet.Builder()
            .subject(user.username)
            .issuer("MyApp")
            .issueTime(Date())
            .expirationTime(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
            .jwtID(UUID.randomUUID().toString())
            .claim("scope", buildScope(user))
            .build()

        val payload = Payload(jwtClaimsSet.toJSONObject())
        val jwsObject = JWSObject(jwsHeader, payload)

        try {
            jwsObject.sign(MACSigner(signerKey.toByteArray()))
            return jwsObject.serialize()
        } catch (e: JOSEException) {
            log.error("Cannot create token", e)
            throw RuntimeException(e)
        }
    }

    private fun buildScope(user: User): String {
        return "ROLE_${user.role}"
    }

    private fun generateOTP(): String {
        // Generate a random 6-digit OTP
        val random = Random()
        val otp = 100000 + random.nextInt(900000)
        return otp.toString()
    }
}