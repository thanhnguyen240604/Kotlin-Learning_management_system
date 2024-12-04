package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.model.Subject
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import org.springframework.stereotype.Component

@Component
class SubjectMapper {
    fun toSubject(fullSubjectDTO: SubjectDTO) : Subject {
        return Subject(
            id = fullSubjectDTO.id,
            name = fullSubjectDTO.name,
            credits = fullSubjectDTO.credits,
            faculty = fullSubjectDTO.faculty,
            major = fullSubjectDTO.major
        )
    }

    fun toSubjectDTO(subject: Subject) : SubjectDTO {
        return SubjectDTO(
            id = subject.id,
            name = subject.name,
            credits = subject.credits,
            faculty = subject.faculty,
            major = subject.major
        )
    }
}