package com.be.kotlin.grade

import jakarta.persistence.*

@Entity
@Table(name = "subject")
data class Subject (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "subject_name", nullable = false)
    val name: String = "",

    @Column(name = "subject_code", nullable = false)
    val code: String = "",

    @Column(name = "subject_credits", nullable = false)
    var credits: Int = 0,

    @Column(name = "subject_faculty", nullable = false)
    var faculty: String = "",

    @Column(name = "class_grade", nullable = true)
    var major: String? = ""
)