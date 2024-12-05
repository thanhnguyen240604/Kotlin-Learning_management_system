package com.be.kotlin.grade.dto.classDTO

import jakarta.validation.constraints.NotNull

data class ClassIdDTO(
    @NotNull(message = "Class ID is required")
    var id: Long
)