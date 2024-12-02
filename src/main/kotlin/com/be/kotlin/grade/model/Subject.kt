package hcmut.example.gradeportalbe.model

import jakarta.persistence.*

@Entity
@Table(name = "subject")
class Subject (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "subject_name", nullable = false)
    val name: String,

    @Column(name = "subject_code", nullable = false)
    val code: String,

    @Column(name = "class_credits", nullable = false)
    var credits: Int,

    @Column(name = "class_faculty", nullable = false)
    var faculty: String,

    @Column(name = "class_grade", nullable = true)
    var major: String
)