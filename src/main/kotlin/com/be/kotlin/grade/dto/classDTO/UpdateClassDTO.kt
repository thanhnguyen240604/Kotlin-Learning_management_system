package com.be.kotlin.grade.dto.classDTO

import jakarta.validation.constraints.NotNull
import java.time.DayOfWeek
import java.time.LocalTime

data class UpdateClassDTO(
    @NotNull(message = "Class ID is required")
    val id: Long? = null,

    val lecturersUsernameList: MutableList<String>? = mutableListOf(),

    val name: String? = null,

    val subjectId: String? = null,

    val semester: Int? = null,

    val startTime: LocalTime? = LocalTime.of(0,0),

    val endTime: LocalTime? = LocalTime.of(0,0),

    val dayOfWeek: MutableList<DayOfWeek>? = mutableListOf(),
)
