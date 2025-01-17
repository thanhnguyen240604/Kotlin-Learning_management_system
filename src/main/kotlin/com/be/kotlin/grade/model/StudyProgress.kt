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
    val totalMajorCredits: Int = 0,

    @Column(name = "total_general_credits", nullable = false)
    val totalGeneralCredits: Int = 0,

    @Column(name = "major_GPA", nullable = false)
    val majorGPA: Double = 0.0,

    @Column(name = "general_GPA", nullable = false)
    val generalGPA: Double = 0.0,

    @Column(name = "elective_credits", nullable = false)
    val electiveCredits: Int = 0,

    @Column(name = "major_elective_credits", nullable = false)
    val majorElectiveCredits: Int = 0,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate,

    @Column(name = "last_updated", nullable = false)
    val lastUpdated: LocalDate,

    )