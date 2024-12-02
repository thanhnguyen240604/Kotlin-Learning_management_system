package hcmut.example.gradeportalbe.dto

data class Response (
    val statusCode: Int,
    val message: String,
    val role: String,
    val token: String,

    //DTO response

    //Pagination
    val totalPages: Int,
    val totalElements: Int,
    val currentPage: Int
)