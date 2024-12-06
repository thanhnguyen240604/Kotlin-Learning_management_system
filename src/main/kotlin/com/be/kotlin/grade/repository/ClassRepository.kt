package com.be.kotlin.grade.repository

import com.be.kotlin.grade.model.Class
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


import org.springframework.data.domain.Pageable

@Repository
interface ClassRepository: JpaRepository<Class, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(1) > 0 THEN true ELSE false END
        FROM Class c
        JOIN c.lecturers l
        JOIN c.subject s
        WHERE l.id = :lecturerId
          AND s.id = :subjectId
          AND c.name = :className
    """)
    fun existsByLecturerSubjectAndClassName(
        @Param("lecturerId") lecturerId: Long,
        @Param("subjectId") subjectId: String,
        @Param("className") className: String
    ): Boolean

    @Query("SELECT c FROM Class c WHERE c.id IN :classIds")
    fun findClassesByIds(
        @Param("classIds") classIds: List<Long>,
        pageable: Pageable
    ): Page<Class>
}