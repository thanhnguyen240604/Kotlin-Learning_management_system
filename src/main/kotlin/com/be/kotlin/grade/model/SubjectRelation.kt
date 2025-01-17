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
    val creditType: CreditType = CreditType.OPTIONAL,

    @ManyToOne
    @JoinColumn(name = "pre_subject_id", nullable = true)
    val preSubject: Subject? = null,

    @ManyToOne
    @JoinColumn(name = "post_subject_id", nullable = true)
    val postSubject: Subject? = null,
)
