package com.be.kotlin.grade

import jakarta.persistence.*

@Entity
@Table(name = "grade")
data class Grade (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "score", nullable = false)
    var score: Float = 0f,

    @Column(name = "weight", nullable = false)
    var weight: Float = 0f,

    @Column(name = "study_id", nullable = false)
    var studyId: Long? = null
)