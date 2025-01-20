package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.model.Class
import com.be.kotlin.grade.model.Subject
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class ClassMapper {
    fun toClass(classDTO: ClassDTO, subject: Subject): Class? {
        return classDTO.lecturers?.let {
            Class(
                name = classDTO.name,
                subject = subject,
                semester = classDTO.semester,
                daysOfWeek = classDTO.dayOfWeek,
                startTime = classDTO.startTime,
                endTime = classDTO.endTime,
                lecturers = it
            )
        }
    }

    fun toClassDTO(classEntity: Class): ClassDTO {
        return ClassDTO(
            name = classEntity.name,
            subjectId = classEntity.subject.id,
            semester = classEntity.semester,
            dayOfWeek = classEntity.daysOfWeek,
            startTime = classEntity.startTime,
            endTime = classEntity.endTime,
            lecturers = classEntity.lecturers
        )
    }
}