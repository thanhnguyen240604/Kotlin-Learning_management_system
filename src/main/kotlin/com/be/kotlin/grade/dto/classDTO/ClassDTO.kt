package com.be.kotlin.grade.dto.classDTO

import com.be.kotlin.grade.model.User
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.DayOfWeek
import java.time.LocalTime

data class ClassDTO(
    val id: Long? = null,

    val lecturersUsernameList: MutableList<String>? = mutableListOf(),

    @NotBlank(message = "Class name is required")
    val name: String = "",

    @NotBlank(message = "Subject ID is required")
    val subjectId: String = "",

    @NotNull(message = "Semester ID is required")
    val semester: Int = 0,

    @NotNull(message = "Start Time is required")
    val startTime: LocalTime = LocalTime.of(0,0),

    @NotNull(message = "End Time is required")
    val endTime: LocalTime = LocalTime.of(0,0),

    @NotNull(message = "Day of week is required")
    val dayOfWeek: MutableList<DayOfWeek> = mutableListOf(),
)
