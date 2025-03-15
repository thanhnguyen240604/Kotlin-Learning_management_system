package com.be.kotlin.grade.model

import com.be.kotlin.grade.converter.CustomDayOfWeekConverter
import com.be.kotlin.grade.converter.StringListToStringConverter
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

    @Column(name = "lecturers", columnDefinition = "TEXT")
    @Convert(converter = StringListToStringConverter::class)
    var lecturersUsername: List<String> = listOf(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    var subject: Subject = Subject(),

    @Column(name = "semester", nullable = false)
    var semester: Int = 0,

    @Column(name = "days_of_week", nullable = false)
    @Convert(converter = CustomDayOfWeekConverter::class)
    var dayOfWeek: List<CustomDayOfWeek> = mutableListOf(),

    @Column(name = "start_time")
    var startTime: LocalTime = LocalTime.of(0, 0),

    @Column(name = "end_time")
    var endTime: LocalTime = LocalTime.of(0, 0),

    @Column(name = "max_student")
    var maxStudent: Int? = 0,
)