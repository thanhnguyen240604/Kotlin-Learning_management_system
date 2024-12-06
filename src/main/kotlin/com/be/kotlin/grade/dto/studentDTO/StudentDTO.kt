package com.be.kotlin.grade.dto.studentDTO

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class StudentDTO (
    @NotNull(message = "Student ID is required.")
    val studentId : Long,
    @NotNull(message = "Enrolled course is required.")
    val enrolledCourse: Int,
    @NotBlank(message = "Major is required.")
    val major: String,
)