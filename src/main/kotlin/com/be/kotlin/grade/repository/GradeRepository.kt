package com.be.kotlin.grade.repository

import com.be.kotlin.grade.model.Grade
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface GradeRepository: JpaRepository<Grade, Long> {

    @Query("SELECT g FROM Grade g WHERE g.studyId IN :studyId")
    fun findGradeByStudyID(@Param("studyId") studyId: List<Long>): List<Grade>

}