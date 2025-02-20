package com.be.kotlin.grade.repository

import com.be.kotlin.grade.model.StudyProgress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudyProgressRepository: JpaRepository<StudyProgress, Long> {
//    fun findByStudentId(studentId: Long): StudyProgress
}