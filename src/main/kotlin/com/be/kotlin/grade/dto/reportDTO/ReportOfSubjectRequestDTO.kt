package com.be.kotlin.grade.dto.reportDTO

import jakarta.validation.constraints.NotNull

data class ReportOfSubjectRequestDTO (
    @NotNull(message = "Id is required")
    val subjectId: String,
    val semester: Int?,
    val year: Int?
)