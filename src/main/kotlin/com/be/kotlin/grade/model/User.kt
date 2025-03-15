package com.be.kotlin.grade.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "name", nullable = false)
    var name: String = "",

    var faculty: String? = null,

    @Column(name = "role", nullable = false)
    var role: String = "",

    @Column(name = "username", unique = true, nullable = false)
    var username: String = "",

    @Column(name = "password", nullable = false)
    var password: String = "",

    @Column
    var otp: String? = "",

    @Column
    var otpExpiry: LocalDateTime? = null
)