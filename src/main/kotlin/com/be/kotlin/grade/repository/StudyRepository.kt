package com.be.kotlin.grade.repository

import com.be.kotlin.grade.Study
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StudyRepository: JpaRepository<Study, Long> {
    fun findByStudentStudentIdAndSubjectIdAndStudyClassId(
        studentId: Long,
        subjectId: String,
        studyClassId: Long
    ): Study?

    @Query("SELECT s.studyClass.id FROM Study s WHERE s.student.studentId = :studentId")
    fun findClassIdsByStudentId(@Param("studentId") studentId: Long): List<Long>
}