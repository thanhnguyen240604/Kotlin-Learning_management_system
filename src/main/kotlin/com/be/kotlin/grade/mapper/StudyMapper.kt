package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.Study
import com.be.kotlin.grade.dto.StudyDTO
import com.be.kotlin.grade.repository.ClassRepository
import com.be.kotlin.grade.repository.StudentRepository
import com.be.kotlin.grade.repository.SubjectRepository
import org.springframework.stereotype.Component

@Component
class StudyMapper (
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository,
    private val classRepository: ClassRepository
) {
    fun toStudy(studyDTO: StudyDTO): Study {
        val student = studyDTO.studentId?.let { studentRepository.findById(it).get() }
        val subject = studyDTO.subjectId?.let { subjectRepository.findById(it).get() }
        val studyClass = studyDTO.classId?.let { classRepository.findById(it).get() }

        return Study(
            id = studyDTO.id,
            student = student,
            subject = subject,
            studyClass = studyClass,
            semester = studyDTO.semester ?: 0,
            score = studyDTO.score
        )
    }

    fun toStudyDTO(study: Study): StudyDTO {
        return StudyDTO(
            id = study.id,
            studentId = study.student?.studentId,
            subjectId = study.subject?.id,
            classId = study.studyClass?.id,
            semester = study.semester,
            score = study.score
        )
    }
}
