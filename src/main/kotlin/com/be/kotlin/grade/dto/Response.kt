package hcmut.example.gradeportalbe.dto

import com.be.kotlin.grade.dto.SubjectDTO

data class Response(
    var statusCode: Int,
    var message: String,
    var role: String? = null,
    var token: String? = null,

    // DTO response
    var userDTO: UserDTO? = null,
    var subjectDTO: SubjectDTO? = null,

    // Pagination
    var totalPages: Int? = null,
    var totalElements: Int? = null,
    var currentPage: Int? = null
)
