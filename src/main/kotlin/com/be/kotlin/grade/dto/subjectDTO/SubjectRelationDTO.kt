package com.be.kotlin.grade.dto.subjectDTO

import jakarta.validation.constraints.NotBlank

data class SubjectRelationDTO (
    @NotBlank(message = "Subject ID is required")
    val subjectId: String = "",
    @NotBlank(message = "Faculty is required")
    val faculty: String = "",

    val creditType: String? = null,
    val preSubjectId: String? = null,
    val postSubjectId: String? = null,
)