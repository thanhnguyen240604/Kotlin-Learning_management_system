package com.be.kotlin.grade.model

import jakarta.persistence.*

@Entity
@Table(name = "grade")
data class Grade (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "score", nullable = false)
    var score: Float,

    @Column(name = "weight", nullable = false)
    var weight: Float,
)