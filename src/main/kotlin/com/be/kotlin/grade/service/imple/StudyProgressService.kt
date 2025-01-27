package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.model.StudyProgress
import com.be.kotlin.grade.repository.StudentRepository
import com.be.kotlin.grade.repository.StudyProgressRepository
import com.be.kotlin.grade.service.interf.IStudyProgress
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StudyProgressService (
    private val studyProgressRepository: StudyProgressRepository,
    private val studentRepository: StudentRepository
) : IStudyProgress {
    override fun addStudyProgress(studentId: Long) {
        val existingStudent = studentRepository.findById(studentId).
                orElseThrow { AppException(ErrorCode.STUDENT_NOT_FOUND) }

        val newStudyProgress = StudyProgress(
            student = existingStudent
        )
        studyProgressRepository.save(newStudyProgress)
    }


}