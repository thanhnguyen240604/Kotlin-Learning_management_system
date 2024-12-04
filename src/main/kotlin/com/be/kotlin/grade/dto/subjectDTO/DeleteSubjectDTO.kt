package com.be.kotlin.grade.dto.subjectDTO

import jakarta.validation.constraints.NotNull

class DeleteSubjectDTO {
    @NotNull(message = "Id is required for deletion")
    var id: String = ""
}