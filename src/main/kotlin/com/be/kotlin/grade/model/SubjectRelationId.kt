package com.be.kotlin.grade.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class SubjectRelationId(
    @Column(name = "subject_id", nullable = false)
    val relationSubjectId: String = "",

    @Column(name = "faculty_code", nullable = false)
    val facultyCode: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SubjectRelationId) return false
        return relationSubjectId == other.relationSubjectId && facultyCode == other.facultyCode
    }

    override fun hashCode(): Int {
        return relationSubjectId.hashCode() * 31 + facultyCode.hashCode()
    }
}
