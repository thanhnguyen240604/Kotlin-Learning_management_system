package com.be.kotlin.grade.dto.SubjectDTO

import jakarta.validation.constraints.NotNull

class DeleteSubjectDTO {
    @NotNull(message = "Subject must have unique id")
    var id: String = ""
}