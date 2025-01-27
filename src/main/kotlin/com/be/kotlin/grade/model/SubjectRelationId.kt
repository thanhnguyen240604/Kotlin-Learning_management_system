package com.be.kotlin.grade.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class SubjectRelationId(
    @Column(name = "subject_id", nullable = false)
    val subjectId: String = "",

    @Column(name = "faculty_code", nullable = false)
    val faculty: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SubjectRelationId) return false
        return subjectId == other.subjectId && faculty == other.faculty
    }

    override fun hashCode(): Int {
        return subjectId.hashCode() * 31 + faculty.hashCode()
    }
}
