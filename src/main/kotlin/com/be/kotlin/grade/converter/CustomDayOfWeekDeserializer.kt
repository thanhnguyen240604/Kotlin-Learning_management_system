package com.be.kotlin.grade.converter

import com.be.kotlin.grade.model.enums.CustomDayOfWeek
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class CustomDayOfWeekDeserializer : JsonDeserializer<List<CustomDayOfWeek>>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): List<CustomDayOfWeek> {
        val dayIntList = p.readValueAs(List::class.java) as List<Int>  // Đọc List<Int> từ JSON
        return dayIntList.map { CustomDayOfWeek.fromValue(it) }  // Chuyển sang List<CustomDayOfWeek>
    }
}