package com.be.kotlin.grade.repository

import com.be.kotlin.grade.Class
import com.be.kotlin.grade.Study
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface StudyRepository: JpaRepository<Study, Long> {
    fun findByStudyClass(studyClass:Class):MutableList<Study>
}