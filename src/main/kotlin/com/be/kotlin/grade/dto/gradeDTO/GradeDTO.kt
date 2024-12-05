package com.be.kotlin.grade.dto.gradeDTO

import jakarta.validation.constraints.NotNull

class GradeDTO (
    var id:Long ?= null,
    @NotNull(message = "need to enter Score")
    var score:Float,
    @NotNull(message = "need to enter Weight")
    var weight:Float,
    @NotNull(message = "need to enter Study ID")
    var studyId: Long? = null
)