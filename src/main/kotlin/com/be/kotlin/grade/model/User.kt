package hcmut.example.gradeportalbe.model

import jakarta.persistence.*

@Entity
@Table(name = "user")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "name", nullable = false)
    var name: String,

    var faculty: String? = null,

    @Column(name = "role", nullable = false)
    val role: String,

    @Column(name = "username", unique = true, nullable = false)
    val username: String,

    @Column(name = "password", nullable = false)
    var password: String
)