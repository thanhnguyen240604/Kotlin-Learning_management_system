package com.be.kotlin.grade.dto.subjectDTO

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class SubjectRegisterDTO (
    @NotNull(message = "Semester is required")
    val semester: Int,

    @NotBlank(message = "Subject ID is required")
    val subjectId: String,
)