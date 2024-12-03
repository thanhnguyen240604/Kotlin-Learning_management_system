package com.be.kotlin.grade.dto

data class Response(
    var statusCode: Int,
    var message: String,
    var role: String? = null,
    var token: String? = null,

    // DTO response
    var user: UserDTO? = null,
    var subjectDTO: SubjectDTO? = null,

    // Pagination
    var totalPages: Int? = null,
    var totalElements: Int? = null,
    var currentPage: Int? = null
)
