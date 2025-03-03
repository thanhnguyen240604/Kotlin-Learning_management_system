package com.be.kotlin.grade.converter

import com.be.kotlin.grade.model.enums.CustomDayOfWeek
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class CustomDayOfWeekConverter : AttributeConverter<List<CustomDayOfWeek>, String> {
    override fun convertToDatabaseColumn(p0: List<CustomDayOfWeek>?): String {
        return p0?.joinToString(",") { it.name } ?: ""
    }

    override fun convertToEntityAttribute(p0: String?): List<CustomDayOfWeek> {
        return p0?.split(",")?.mapNotNull {
            try { CustomDayOfWeek.valueOf(it) } catch (e: IllegalArgumentException) { null }
        } ?: emptyList()
    }
}