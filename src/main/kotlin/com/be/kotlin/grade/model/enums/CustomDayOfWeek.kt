package com.be.kotlin.grade.model.enums

enum class CustomDayOfWeek(val value: Int) {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    companion object {
        fun fromValue(value: Int): CustomDayOfWeek {
            return entries.find { it.value == value } ?: throw IllegalArgumentException("Invalid day: $value")
        }
    }
}