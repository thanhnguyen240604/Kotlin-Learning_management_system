package hcmut.example.gradeportalbe.model

import jakarta.persistence.*

@Entity
@Table(name = "student")
class Student (
    @Id
    @Column(name = "student_id", nullable = false)
    val studentId: Long? = null,

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    var user: User,

    @Column(name = "major", nullable = false)
    var major: String,

    @Column(name = "enrolled_course", nullable = false)
    var enrolledCourse: Int
)