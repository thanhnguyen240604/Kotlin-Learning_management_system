package com.be.kotlin.grade.dto.studyDTO

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class StudyDTO(
    var id: Long? = null,

    @NotNull(message = "Student is required")
    @Positive(message = "Student ID must be a positive number")
    val studentId: Long? = null,

    @NotNull(message = "Subject is required")
    val subjectId: String? = null,

    @NotNull(message = "Class is required")
    @Positive(message = "Class ID must be a positive number")
    val classId: Long? = null,

    @NotNull(message = "Semester is required")
    @Positive(message = "Semester must be a positive number")
    val semester: Int? = null,

    val score: Float? = null
)
