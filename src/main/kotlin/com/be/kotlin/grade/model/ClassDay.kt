package com.be.kotlin.grade.model

import jakarta.persistence.*
import java.time.DayOfWeek

@Entity
@Table(name = "class_days")
data class ClassDay(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    var ofClass: Class = Class(),

    @Column(name = "day_of_week", nullable = false)
    var dayOfWeek: DayOfWeek = DayOfWeek.MONDAY
)
