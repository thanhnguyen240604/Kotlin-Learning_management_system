package com.be.kotlin.grade.model

import jakarta.persistence.*


@Entity
@Table(name = "class")
data class Class (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "class_name", nullable = false)
    var name: String = "",

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    var lecturers: MutableList<User> = mutableListOf(),

    @OneToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    val subject: Subject = Subject(),
)