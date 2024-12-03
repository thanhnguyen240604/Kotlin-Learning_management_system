package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.Subject
import com.be.kotlin.grade.dto.SubjectDTO
import org.springframework.stereotype.Component

@Component
class SubjectMapper {
    fun toSubject(subjectDTO: SubjectDTO) : Subject {
        return Subject(
            id = subjectDTO.id,
            name = subjectDTO.name,
            code = subjectDTO.code,
            credits = subjectDTO.credits,
            faculty = subjectDTO.faculty,
            major = subjectDTO.major
        );
    }
}