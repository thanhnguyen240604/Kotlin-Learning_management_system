package com.be.kotlin.grade.dto.studyDTO

import jakarta.validation.constraints.NotNull

data class GetStudyDTO(
    @field:NotNull(message = "Subject ID is required.")
    val subjectId: String,

    @field:NotNull(message = "Semester is required.")
    val semester: Int
)
