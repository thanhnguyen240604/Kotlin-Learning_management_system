package com.be.kotlin.grade.dto.studyDTO

import com.be.kotlin.grade.model.Grade
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

@JsonInclude(JsonInclude.Include.NON_NULL)
data class StudyDTO(
    val id: Long? = null,

    @NotNull(message = "Student is required")
    @Positive(message = "Student ID must be a positive number")
    val studentId: Long = 0,

    val subjectId: String? = null,
    @NotNull(message = "Class is required")
    @Positive(message = "Class ID must be a positive number")
    val classId: Long? = 0,

    val score: Float? = null,

    val gradeList : MutableList<Grade> = mutableListOf()
)
