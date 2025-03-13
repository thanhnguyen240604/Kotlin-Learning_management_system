package com.be.kotlin.grade.model

import com.be.kotlin.grade.converter.CustomDayOfWeekConverter
import com.be.kotlin.grade.model.enums.CustomDayOfWeek
import jakarta.persistence.*
import java.time.LocalTime

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

    @Column(name = "semester", nullable = false)
    var semester: Int = 0,

    @Column(name = "days_of_week", nullable = false)
    @Convert(converter = CustomDayOfWeekConverter::class)
    var dayOfWeek: MutableList<CustomDayOfWeek> = mutableListOf(),

    @Column(name = "start_time")
    var startTime: LocalTime = LocalTime.of(0, 0),

    @Column(name = "end_time")
    var endTime: LocalTime = LocalTime.of(0, 0),

    @Column(name = "max_student")
    var maxStudent: Int? = 0,
)