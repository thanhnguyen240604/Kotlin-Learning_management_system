package com.be.kotlin.grade.dto.subjectDTO

import jakarta.validation.constraints.NotBlank

class SubjectIdDTO (
    @NotBlank(message = "Id is required")
    var id: String = ""
)