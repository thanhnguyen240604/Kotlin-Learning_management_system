package com.be.kotlin.grade.dto

import com.be.kotlin.grade.dto.subjectDTO.FullSubjectDTO

data class Response(
    var statusCode: Int,
    var message: String,
    var role: String? = null,
    var token: String? = null,

    // DTO response
    var userDTO: UserDTO? = null,
    var fullSubjectDTO: FullSubjectDTO? = null,

    // List DTO response

    // Pagination
    var totalPages: Int? = null,
    var totalElements: Int? = null,
    var currentPage: Int? = null
)
