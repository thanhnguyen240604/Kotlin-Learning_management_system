package com.be.kotlin.grade.model

import com.be.kotlin.grade.model.Grade
import jakarta.persistence.*

@Entity
@Table(name = "study")
data class Study(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
    val student: Student = Student(),

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    val subject: Subject = Subject(),

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    val studyClass: Class = Class(),

    @Column(name = "semester", nullable = false)
    val semester: Int = 0,

    var score: Float = 13F,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    var gradesList: MutableList<Grade> = mutableListOf()
)