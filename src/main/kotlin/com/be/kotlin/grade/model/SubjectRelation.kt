package com.be.kotlin.grade.model

import jakarta.persistence.*
import com.be.kotlin.grade.model.enums.CreditType

@Entity
@Table(name = "subject_relation")
data class SubjectRelation (
    @EmbeddedId
    val id: SubjectRelationId = SubjectRelationId("", ""),

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_type", nullable = false)
    var creditType: CreditType = CreditType.ELECTIVE,

    var preSubjectId: String? = null,
    var postSubject: String? = null,
)
