package com.be.kotlin.grade.repository

import com.be.kotlin.grade.model.SubjectRelation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SubjectRelationRepository: JpaRepository<SubjectRelation, Int> {
    @Query("""
        SELECT DISTINCT s 
        FROM SubjectRelation s
        WHERE s.id.subjectId = :subjectId AND s.id.faculty = :faculty
    """)
    fun findBySubjectRelationId(
        @Param("subjectId") subjectId: String,
        @Param("faculty") faculty: String
    ): SubjectRelation?
}