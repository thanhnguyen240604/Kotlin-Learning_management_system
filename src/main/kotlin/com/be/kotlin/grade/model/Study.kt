package hcmut.example.gradeportalbe.model

import jakarta.persistence.*

@Entity
@Table(name = "study")
class Study (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
    val student: Student,

    @OneToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    val subject: Subject,

    @OneToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    val studyClass: Class,

    @Column(name = "semester", nullable = false)
    val semester: Int,

    var score: Float? = null,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")  // Chỉ định cột khóa ngoại trong bảng Grade
    var gradesList: MutableList<Grade> = mutableListOf()
)