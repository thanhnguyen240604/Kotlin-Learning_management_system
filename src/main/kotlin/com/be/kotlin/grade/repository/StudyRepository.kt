package com.be.kotlin.grade.repository

import com.be.kotlin.grade.model.Class
import com.be.kotlin.grade.model.Study
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudyRepository: JpaRepository<Study, Long> {
    fun findByStudentStudentIdAndSubjectIdAndStudyClassId(
        studentId: Long,
        subjectId: String,
        studyClassId: Long
    ): Study?

    fun findByStudentUserUsernameAndSemester(username: String, semester: Int): List<Study>
    fun findByStudyClass(studyClass : Class) : MutableList<Study>
}