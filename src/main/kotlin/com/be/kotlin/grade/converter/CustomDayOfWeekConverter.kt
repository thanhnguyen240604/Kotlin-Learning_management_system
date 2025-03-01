package com.be.kotlin.grade.converter

import com.be.kotlin.grade.model.enums.CustomDayOfWeek
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class CustomDayOfWeekConverter : AttributeConverter<List<CustomDayOfWeek>, String> {

    override fun convertToDatabaseColumn(attribute: List<CustomDayOfWeek>?): String {
        return attribute?.joinToString(",") { it.value.toString() } ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): List<CustomDayOfWeek> {
        return dbData?.takeIf { it.isNotEmpty() }
            ?.split(",")
            ?.map { CustomDayOfWeek.fromValue(it.toInt()) }
            ?: emptyList()
    }
}