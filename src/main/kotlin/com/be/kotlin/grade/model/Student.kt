package com.be.kotlin.grade.model

import com.be.kotlin.grade.model.User
import jakarta.persistence.*

@Entity
@Table(name = "student")
class Student (
    @Id
    @Column(name = "student_id", nullable = false)
    var studentId: Long = 0,

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    var user: User = User(),

    @Column(name = "major", nullable = false)
    var major: String = "",

    @Column(name = "enrolled_course", nullable = false)
    var enrolledCourse: Int = 0
)