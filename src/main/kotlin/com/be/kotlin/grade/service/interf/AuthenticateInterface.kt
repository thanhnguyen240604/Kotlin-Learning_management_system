package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.AuthenticateDTO
import com.be.kotlin.grade.dto.IntrospectDTO
import com.be.kotlin.grade.dto.Response
import com.nimbusds.jose.JOSEException
import java.text.ParseException

interface AuthenticateInterface {
    fun authenticate(request: AuthenticateDTO): Response
    @Throws(JOSEException::class, ParseException::class)
    fun introspect(request: IntrospectDTO): Response
}