package com.be.kotlin.grade.dto.userDTO

data class UserResponseDTO (
    var id: Long? = null,
    var name: String? = null,
    var faculty: String? = null,
    val role: String? = null,
    val username: String? = null,
)
