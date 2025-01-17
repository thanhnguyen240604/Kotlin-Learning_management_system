package com.be.kotlin.grade.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "class")
data class Class (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,

    @Column(name = "class_name", nullable = false)
    var name: String = "",

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "class_lecturers",
        joinColumns = [JoinColumn(name = "class_id")],
        inverseJoinColumns = [JoinColumn(name = "lecturer_id")]
    )
    var lecturers: MutableList<User> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    var subject: Subject = Subject(),

    @Column(name = "start_time", nullable = false)
    var startTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "end_time", nullable = false)
    var endTime: LocalDateTime = LocalDateTime.now(),
)