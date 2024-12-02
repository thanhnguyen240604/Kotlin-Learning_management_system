package hcmut.example.gradeportalbe.model

import jakarta.persistence.*

@Entity
@Table(name = "grade")
class Grade (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "score", nullable = false)
    var score: Float,

    @Column(name = "weight", nullable = false)
    var weight: Float,
)