package com.be.kotlin.grade

import com.be.kotlin.grade.model.Subject
import jakarta.persistence.*

@Entity
@Table(name = "study")
data class Study (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
    val student: Student = Student(),

    @OneToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    val subject: Subject = Subject(),

    @OneToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    val studyClass: Class = Class(),

    @Column(name = "semester", nullable = false)
    val semester: Int = 0,

    var score: Float? = null,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")  // Chỉ định cột khóa ngoại trong bảng Grade
    var gradesList: MutableList<Grade> = mutableListOf()
)