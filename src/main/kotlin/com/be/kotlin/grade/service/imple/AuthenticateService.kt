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
import jakarta.servlet.http.HttpServletRequest
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class AuthenticateService(
    private val userRepository: UserRepository,
    private val userService: UserService,
    @Value("\${jwt.signerKey}")
    private val signerKey: String,

    private val emailService: EmailService,

    @Value("\${spring.security.oauth2.client.registration.google.client-id}")
    private val clientId: String,

    @Value("\${spring.security.oauth2.client.registration.google.client-secret}")
    private val clientSecret: String,

    @Value("\${spring.security.oauth2.client.provider.google.authorization-uri}")
    private val authUrl: String,

    @Value("\${spring.security.oauth2.client.registration.google.redirect-uri}")
    private val redirectUri: String,

    @Value("\${spring.security.oauth2.client.provider.google.token-uri}")
    private val tokenUri: String,

    @Value("\${spring.security.oauth2.client.provider.google.user-info-uri}")
    private val userInfoUri: String,
) : IAuthenticate {

    override fun authenticate(request: AuthenticateDTO,isGoogleLogin: Boolean): Response {
        val specialUsers = System.getProperty("SPECIAL_USERS")?.split(",") ?: emptyList()

        val sanitizedUsername = request.username.trim()

        // Kiểm tra đuôi email, bỏ qua nếu thuộc danh sách ngoại lệ
        if (!specialUsers.contains(sanitizedUsername) && !sanitizedUsername.endsWith("@hcmut.edu.vn")) {
            throw AppException(ErrorCode.UNAUTHENTICATED_USERNAME_DOMAIN)
        }

        val user = userRepository.findByUsername(request.username)
            .orElseThrow { AppException(ErrorCode.UNAUTHENTICATED_USERNAME_PASSWORD) }

        var authenticated = false
        if (!isGoogleLogin) {
            if (user.isGoogleAccount) {
                throw AppException(ErrorCode.UNAUTHENTICATED_LOGIN)
            }
            val passwordEncoder = BCryptPasswordEncoder(5)
            authenticated = passwordEncoder.matches(request.password, user.password)
            if (!authenticated) {
                throw AppException(ErrorCode.UNAUTHENTICATED_LOGIN)
            }
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

        if (passwordEncoder.matches(request.newPassword, user.password)) {
            throw AppException(ErrorCode.PASSWORD_NOT_CHANGE)
        }

        val encodedNewPassword = passwordEncoder.encode(request.newPassword)
        user.password = encodedNewPassword
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

    override fun generateAuthUrl(request: HttpServletRequest, state: String): Response {
        val url: String
        if (state == "login") {
            url = "$authUrl?client_id=$clientId&redirect_uri=$redirectUri&response_type=code&scope=email&state=$state"
        }
        else {
            url = "$authUrl?client_id=$clientId&redirect_uri=$redirectUri&response_type=code&scope=email+profile&state=$state"
        }
        return Response(
            statusCode = 200,
            message = "Url generated successful",
            url = url
        )
    }

    override fun getAccessToken(code: String, state: String): Response {
        val restTemplate = RestTemplate()
        val tokenUrl = tokenUri

        val requestBody = LinkedMultiValueMap<String, String>().apply {
            add("client_id", clientId)
            add("client_secret", clientSecret)
            add("code", code)
            add("grant_type", "authorization_code")
            add("redirect_uri", redirectUri)
        }

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val requestEntity = HttpEntity(requestBody, headers)
        val response = restTemplate.postForEntity(tokenUrl, requestEntity, Map::class.java)

        val accessToken = response.body?.get("access_token") as? String
        if (!response.statusCode.is2xxSuccessful || response.body == null || accessToken == null) {
            throw AppException(ErrorCode.TOKEN_FETCHED_FAIL)
        }

        return getUserInfo(accessToken, state)
    }

    fun getUserInfo(accessToken: String, state: String): Response {
        val restTemplate = RestTemplate()
        val userInfoUrl = userInfoUri

        val headers = HttpHeaders().apply {
            set("Authorization", "Bearer $accessToken")
        }

        val userRequest = HttpEntity<Void>(headers)
        val userResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userRequest, Map::class.java)

        val userInfo = userResponse.body
        if (!userResponse.statusCode.is2xxSuccessful || userInfo == null) {
            throw AppException(ErrorCode.USERINFO_FETCHED_FAIL)
        }

        if (state == "login") {
            return authenticate(AuthenticateDTO(userInfo["email"] as? String ?: "Unknown"), true)
        } else if (state == "register") {
            return userService.createStudent(userInfo["email"] as? String ?: "Unknown",userInfo["name"] as? String ?: "Unknown")
        } else {
            return Response(
                statusCode = 200,
                message = "User info fetched successfully",
                data = userInfo
            )
        }
    }
}