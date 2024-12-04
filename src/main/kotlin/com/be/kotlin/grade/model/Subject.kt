package com.be.kotlin.grade.model

import jakarta.persistence.*

@Entity
@Table(name = "subject")
data class Subject (
    @Id
    //Subject_code
    var id: String = "",

    @Column(name = "subject_name", nullable = false)
    var name: String = "",

    @Column(name = "subject_credits", nullable = false)
    var credits: Int = 0,

    @Column(name = "subject_faculty", nullable = false)
    var faculty: String = "",

    @Column(name = "subject_major", nullable = true)
    var major: String? = ""
)