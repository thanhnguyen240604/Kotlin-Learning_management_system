package com.be.kotlin.grade.model

import jakarta.persistence.*

@Entity
@Table(name = "class")
data class Class (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,

    @Column(name = "class_name", nullable = false)
    var name: String = "",

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    var lecturers: MutableList<User> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    var subject: Subject = Subject()
)