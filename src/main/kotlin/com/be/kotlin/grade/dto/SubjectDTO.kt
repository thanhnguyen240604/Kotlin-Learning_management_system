package com.be.kotlin.grade.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SubjectDTO (
    var id: Long? = null,
    @NotBlank(message = "Name is required")
    var name: String,

    @NotBlank(message = "Name is required")
    var code: String,

    @NotNull(message = "Sided type is required")
    var credits: Int? = null,

    @NotBlank(message = "faculty is required")
    var faculty: String,

    var major: String? = null
)