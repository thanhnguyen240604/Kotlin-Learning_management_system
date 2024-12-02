package hcmut.example.gradeportalbe.dto

import jakarta.validation.constraints.NotBlank

data class UserDTO (
    var id: Long,
    @NotBlank(message = "Name is required")
    var name: String,
    var faculty: String? = null,
    @NotBlank(message = "Role is required")
    val role: String,
    @NotBlank(message = "Username is required")
    val username: String
)