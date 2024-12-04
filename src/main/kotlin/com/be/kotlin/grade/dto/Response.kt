package com.be.kotlin.grade.dto

import com.be.kotlin.grade.dto.securityDTO.AuthenticateDTO
import com.be.kotlin.grade.dto.securityDTO.IntrospectDTO
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import com.be.kotlin.grade.dto.userDTO.UserDTO

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
    var fullSubjectDTO: SubjectDTO? = null,
    var studyDTO: StudyDTO? = null,

    // List DTO response

    // Pagination
    var totalPages: Int? = null,
    var totalElements: Int? = null,
    var currentPage: Int? = null
)
