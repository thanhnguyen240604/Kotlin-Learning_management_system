package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.model.Class
import com.be.kotlin.grade.model.Subject
import org.springframework.stereotype.Component

@Component
class ClassMapper {
    fun toClass(classDTO: ClassDTO, subject: Subject): Class {
        return Class(
            id = classDTO.id,
            name = classDTO.name,
            subject = subject
        )
    }

    fun toClassDTO(classEntity: Class): ClassDTO {
        return ClassDTO(
            id = classEntity.id,
            name = classEntity.name,
            subjectId = classEntity.subject.id
        )
    }
}