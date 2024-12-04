package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.Subject
import com.be.kotlin.grade.dto.SubjectDTO.FullSubjectDTO
import org.springframework.stereotype.Component

@Component
class SubjectMapper {
    fun toSubject(fullSubjectDTO: FullSubjectDTO) : Subject {
        return Subject(
            id = fullSubjectDTO.id,
            name = fullSubjectDTO.name,
            credits = fullSubjectDTO.credits,
            faculty = fullSubjectDTO.faculty,
            major = fullSubjectDTO.major
        );
    }

    fun toFullSubjectDTO(subject: Subject) : FullSubjectDTO {
        return FullSubjectDTO(
            id = subject.id,
            name = subject.name,
            credits = subject.credits,
            faculty = subject.faculty,
            major = subject.major
        )
    }
}