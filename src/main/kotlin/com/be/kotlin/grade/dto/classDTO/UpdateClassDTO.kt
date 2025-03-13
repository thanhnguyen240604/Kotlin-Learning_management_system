package com.be.kotlin.grade.dto.classDTO

import com.be.kotlin.grade.model.enums.CustomDayOfWeek
import jakarta.validation.constraints.NotNull
import java.time.LocalTime

data class UpdateClassDTO(
    @NotNull(message = "Class ID is required")
    val id: Long? = null,

    val lecturersUsernameList: MutableList<String>? = mutableListOf(),

    val name: String? = null,

    val startTime: LocalTime?= null,

    val endTime: LocalTime? = null,

    val dayOfWeek: MutableList<CustomDayOfWeek>? = null
)
