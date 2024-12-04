package com.be.kotlin.grade.dto.studyDTO

import jakarta.validation.constraints.NotNull

data class StudyDTO(
    var id: Long? = null,

    @NotNull(message = "Student is required")
    val studentId: Long? = null,

    @NotNull(message = "Subject is required")
    val subjectId: String? = null,

    @NotNull(message = "Class is required")
    val classId: Long? = null,

    @NotNull(message = "Semester is required")
    val semester: Int? = null,

    val score: Float? = null
)
