package com.be.kotlin.grade.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SubjectDTO (
    @NotNull(message = "Subject must have unique id")
    var id: Long = 0,

    @NotBlank(message = "Subject name is required")
    var name: String,

    @NotBlank(message = "Subject code is required")
    var code: String,

    @NotNull(message = "Subject credits is required")
    var credits: Int = 0,

    @NotBlank(message = "Subject faculty is required")
    var faculty: String,

    var major: String? = null
)