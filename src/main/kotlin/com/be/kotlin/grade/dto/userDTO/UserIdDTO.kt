package com.be.kotlin.grade.dto.userDTO

import jakarta.validation.constraints.NotNull

class UserIdDTO (
    @NotNull(message = "Id is required")
    var id: Long = 0
)