package com.be.kotlin.grade.repository

import com.be.kotlin.grade.Study
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudyRepository: JpaRepository<Study, Long> {
    fun findByStudentStudentIdAndSubjectIdAndStudyClassId(
        studentId: Long,
        subjectId: String,
        studyClassId: Long
    ): Study?
}