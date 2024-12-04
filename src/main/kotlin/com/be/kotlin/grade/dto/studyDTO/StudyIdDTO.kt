package com.be.kotlin.grade.dto.studyDTO

import jakarta.validation.constraints.NotNull

class StudyIdDTO {
    @NotNull(message = "Id is required")
    var id: Long = 0
}