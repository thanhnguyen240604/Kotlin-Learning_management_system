package com.be.kotlin.grade.model

import com.be.kotlin.grade.model.Grade
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
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    var subject: Subject = Subject(),

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    val studyClass: Class = Class(),

    @Column(name = "semester", nullable = false)
    val semester: Int = 0,

    @Column(name = "elective_study", nullable = false)
    val isElective: Boolean = false,

    var score: Float = 13F,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    var gradesList: MutableList<Grade> = mutableListOf()
)