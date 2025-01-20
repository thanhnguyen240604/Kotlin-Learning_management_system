package com.be.kotlin.grade.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "study_progress")
data class StudyProgress (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name= "student_id")
    val student: Student = Student(),

    @Column(name = "total_major_credits", nullable = false)
    var totalMajorCredits: Int = 0,

    @Column(name = "total_general_credits", nullable = false)
    var totalGeneralCredits: Int = 0,

    @Column(name = "major_GPA", nullable = false)
    var majorGPA: Double = 0.0,

    @Column(name = "general_GPA", nullable = false)
    var generalGPA: Double = 0.0,

    @Column(name = "elective_credits", nullable = false)
    var electiveCredits: Int = 0,

    @Column(name = "major_elective_credits", nullable = false)
    var majorElectiveCredits: Int = 0,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate = LocalDate.now(),

    @Column(name = "last_updated", nullable = false)
    var lastUpdated: LocalDateTime = LocalDateTime.now(),

    )