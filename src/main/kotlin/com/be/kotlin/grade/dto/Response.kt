package com.be.kotlin.grade.dto

import com.be.kotlin.grade.dto.gradeDTO.GradeDTO
import com.be.kotlin.grade.dto.securityDTO.AuthenticateDTO
import com.be.kotlin.grade.dto.securityDTO.IntrospectDTO
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import com.be.kotlin.grade.dto.userDTO.UserResponseDTO
import com.be.kotlin.grade.dto.classDTO.ClassDTO

data class Response(
    var statusCode: Int = 0,
    var message: String = "",
    var role: String? = null,
    var token: String? = null,
    var authenticated: Boolean? = null,

    // DTO response
    var authenticateDTO: AuthenticateDTO? = null,
    var introspectDTO: IntrospectDTO? = null,
    var userDTO: UserResponseDTO? = null,
    var subjectDTO: SubjectDTO? = null,
    var studyDTO: StudyDTO? = null,
    var gradeDTO: GradeDTO? = null,
    var classDTO: ClassDTO? = null,
  
    // List DTO response
    var listUserDTO: List<UserResponseDTO>? = null,

    // Pagination
    var totalPages: Int? = null,
    var totalElements: Long? = null,
    var currentPage: Int? = null
)
