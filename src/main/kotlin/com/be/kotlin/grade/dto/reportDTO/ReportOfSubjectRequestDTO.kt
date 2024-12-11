package com.be.kotlin.grade.dto.reportDTO

import jakarta.validation.constraints.NotNull

data class ReportOfSubjectRequestDTO (
    @NotNull(message = "Id is required")
    var subjectId: String,
    var semester: Int?,
    var year: Int?
)