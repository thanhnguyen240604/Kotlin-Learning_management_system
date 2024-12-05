package com.be.kotlin.grade.dto.studyDTO

import com.be.kotlin.grade.model.Grade
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class StudyDTO(
    var id: Long? = null,

    @NotNull(message = "Student is required")
    @Positive(message = "Student ID must be a positive number")
    val studentId: Long = 0,

    @NotNull(message = "Subject is required")
    val subjectId: String = "",

    @NotNull(message = "Class is required")
    @Positive(message = "Class ID must be a positive number")
    val classId: Long = 0,

    @NotNull(message = "Semester is required")
    @Positive(message = "Semester must be a positive number")
    val semester: Int = 0,

    val score: Float? = null,

    val gradeList : MutableList<Grade> = mutableListOf()
)
