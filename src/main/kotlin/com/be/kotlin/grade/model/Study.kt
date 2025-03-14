package com.be.kotlin.grade.model

import jakarta.persistence.*

@Entity
@Table(name = "study")
data class Study(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
    var student: Student = Student(),

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    var studyClass: Class = Class(),

    @Column(name = "elective_subject")
    var isElective: Boolean = false,

    var score: Float = 0F,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    var gradesList: MutableList<Grade> = mutableListOf()
)