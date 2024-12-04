package com.be.kotlin.grade

import jakarta.persistence.*

@Entity
@Table(name = "subject")
data class Subject (
    @Id
    var id: String = "",

    @Column(name = "subject_name", nullable = false)
    var name: String = "",

    @Column(name = "class_credits", nullable = false)
    var credits: Int = 0,

    @Column(name = "class_faculty", nullable = false)
    var faculty: String = "",

    @Column(name = "class_grade", nullable = true)
    var major: String? = ""
)