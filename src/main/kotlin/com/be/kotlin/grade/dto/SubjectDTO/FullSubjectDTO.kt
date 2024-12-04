package com.be.kotlin.grade.dto.SubjectDTO

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class FullSubjectDTO (
    @NotNull(message = "Id is required")
    var id: String = "",

    @NotBlank(message = "Subject name is required")
    var name: String,

    @NotNull(message = "Subject credits is required")
    var credits: Int = 0,

    @NotBlank(message = "Subject faculty is required")
    var faculty: String,

    var major: String? = null
)