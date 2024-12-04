package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.AuthenticateDTO
import com.be.kotlin.grade.dto.IntrospectDTO
import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.service.interf.AuthenticateInterface
import com.fasterxml.jackson.core.io.JsonEOFException
import java.text.ParseException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthenticateController(
    private val authenticateService: AuthenticateInterface
) {

    @PostMapping("/login")
    fun authenticate(@RequestBody request: AuthenticateDTO): ResponseEntity<Response> {
        val response = authenticateService.authenticate(request)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PostMapping("/introspect")
    @Throws(ParseException::class, JsonEOFException::class)
    fun introspect(@RequestBody request: IntrospectDTO): ResponseEntity<Response> {
        val response = authenticateService.introspect(request)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}