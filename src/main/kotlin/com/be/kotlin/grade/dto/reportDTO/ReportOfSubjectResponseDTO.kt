package com.be.kotlin.grade.dto.reportDTO

import java.time.LocalDateTime

data class ReportOfSubjectResponseDTO(
    val data: Map<String, Long>,
    val totalStudies: Long,
    val subjectId: String,
    val semester: Int? = null,
    val year: Int? = null,
    val timeGenerated: String = LocalDateTime.now().toString()
)