package com.be.kotlin.grade.dto.subjectDTO

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SubjectDTO (
    @NotNull(message = "Id is required")
    val id: String = "",

    @NotBlank(message = "Subject name is required")
    val name: String,

    @NotNull(message = "Subject credits is required")
    val credits: Int = 0,

    @NotBlank(message = "Subject faculty is required")
    val faculty: String,

    val major: String? = null
)