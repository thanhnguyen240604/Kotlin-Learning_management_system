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

    @Query("SELECT c FROM Class c WHERE c.id IN :classIds")
    fun findClassesByIds(
        @Param("classIds") classIds: List<Long>,
        pageable: Pageable
    ): Page<Class>

    @Query("""
        SELECT c FROM Class c 
        WHERE CAST(c.lecturersUsername AS string) LIKE CONCAT('%', :lecturerUsername, '%')
    """)
    fun findClassByLecturersUsername(
        @Param("lecturerUsername") lecturerUsername: String,
        pageable: Pageable
    ): Page<Class>

    @Query("""
        SELECT DISTINCT c 
        FROM Class c
        WHERE c.name = :className AND c.subject.id = :subjectId AND c.semester = :semester
    """)
    fun findBySubjectAndNameAndSemester(
        @Param("subjectId") subjectId: String,
        @Param("className") className: String,
        @Param("semester") semester: Int
    ): Class?

    @Query("""
        SELECT c 
        FROM Class c 
        WHERE c.subject.id = :subjectId AND c.semester = :semester
    """)
    fun findAllBySubjectAndSemester(
        @Param("subjectId") subjectId: String,
        @Param("semester") semester: Int
    ): List<Class>

    fun findClassById(id: Long): Class?
}