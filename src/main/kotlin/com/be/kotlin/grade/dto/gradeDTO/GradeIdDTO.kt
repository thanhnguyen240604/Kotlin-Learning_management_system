package com.be.kotlin.grade.dto.gradeDTO

import jakarta.validation.constraints.NotNull

class GradeIdDTO (
    val id:Long?=null,
    @NotNull(message = "need to enter Score")
    val score:Float,
    @NotNull(message = "need to enter Weight")
    val weight:Float,
    @NotNull(message = "need to enter Study ID")
    val studyId: Long? = null
)