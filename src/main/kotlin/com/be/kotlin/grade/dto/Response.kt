package com.be.kotlin.grade.dto

import com.be.kotlin.grade.dto.SubjectDTO

data class Response(
    var statusCode: Int = 0,
    var message: String = "",
    var role: String? = null,
    var token: String? = null,
    var authenticated: Boolean? = null,

    // DTO response
    var authenticateDTO: AuthenticateDTO? = null,
    var introspectDTO: IntrospectDTO? = null,
    var userDTO: UserDTO? = null,
    var subjectDTO: SubjectDTO? = null,

    // List DTO response

    // Pagination
    var totalPages: Int? = null,
    var totalElements: Int? = null,
    var currentPage: Int? = null
)
