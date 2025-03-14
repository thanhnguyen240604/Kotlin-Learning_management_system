package com.be.kotlin.grade.dto.studyDTO

import jakarta.validation.constraints.NotNull

data class GetStudyDTO(
    @NotNull(message = "Subject ID is required.")
    val subjectId: String,

    @NotNull(message = "Semester is required.")
    val semester: Int
)
