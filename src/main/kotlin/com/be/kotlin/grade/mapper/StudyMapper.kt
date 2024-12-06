package com.be.kotlin.grade.mapper
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.model.Study
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
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
    fun toStudy(studyDTO: StudyDTO): Study? {
        val student = studyDTO.studentId.let {
            studentRepository.findById(it).orElseThrow {
                AppException(ErrorCode.STUDENT_NOT_FOUND)
            }
        }
        
        val subject = studyDTO.subjectId.let {
            subjectRepository.findById(it).orElseThrow {
                AppException(ErrorCode.SUBJECT_NOT_FOUND)
            }
        }
        
        val studyClass = studyDTO.classId.let {
            classRepository.findById(it!!).orElseThrow {
                AppException(ErrorCode.CLASS_NOT_FOUND)
            }
        }

        return Study(
            id = studyDTO.id,
            student = student,
            subject = subject,
            studyClass = studyClass,
            semester = studyDTO.semester,
            score = studyDTO.score ?: 0f,
        )
        }

    

    fun toStudyDTO(study: Study): StudyDTO {
        return StudyDTO(
            id = study.id,
            studentId = study.student.studentId,
            subjectId = study.subject.id,
            classId = study.studyClass.id,
            semester = study.semester,
            score = study.score,
            gradeList = study.gradesList
        )
    }
}
