package com.be.kotlin.grade.converter

import com.be.kotlin.grade.model.enums.CustomDayOfWeek
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class CustomDayOfWeekDeserializer : JsonDeserializer<List<CustomDayOfWeek>>() {
    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): List<CustomDayOfWeek> {
        val node = parser.readValueAs(List::class.java)
        return node.mapNotNull {
            try {
                CustomDayOfWeek.valueOf(it.toString().uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}