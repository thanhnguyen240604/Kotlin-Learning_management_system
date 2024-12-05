package com.be.kotlin.grade.dto.classDTO

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ClassDTO(
    var id: Long? = null,

    @NotBlank(message = "Class name is required")
    var name: String = "",

    @NotNull(message = "Subject ID is required")
    var subjectId: String = ""
)
