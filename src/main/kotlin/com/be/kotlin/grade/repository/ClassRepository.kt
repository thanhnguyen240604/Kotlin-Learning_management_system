package com.be.kotlin.grade.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import  com.be.kotlin.grade.Class
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

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
}