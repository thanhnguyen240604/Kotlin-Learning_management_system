package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.User
import com.be.kotlin.grade.dto.securityDTO.AuthenticateDTO
import com.be.kotlin.grade.dto.securityDTO.IntrospectDTO
import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.repository.UserRepository
import com.be.kotlin.grade.service.interf.AuthenticateInterface
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
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class AuthenticateImplement(
    private val userRepository: UserRepository,
    @Value("\${jwt.signerKey}") private val signerKey: String
) : AuthenticateInterface {

    override fun authenticate(request: AuthenticateDTO): Response {
        val user = userRepository.findByUsername(request.username)
            .orElseThrow { AppException(ErrorCode.USER_NOT_FOUND) }

        val passwordEncoder = BCryptPasswordEncoder(5)

        val authenticated = passwordEncoder.matches(request.password, user.password)
        if (!authenticated) {
            throw AppException(ErrorCode.UNAUTHENTICATED)
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

    private fun generateToken(user: User): String {
        val jwsHeader = JWSHeader(JWSAlgorithm.HS512)

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
}