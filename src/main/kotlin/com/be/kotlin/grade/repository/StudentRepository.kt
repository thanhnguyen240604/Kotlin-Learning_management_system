package com.be.kotlin.grade.repository

import com.be.kotlin.grade.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StudentRepository: JpaRepository<Student, Long> {
    fun findByUserUsername(username: String): Optional<Student>
    fun existsByStudentId(id: Long): Boolean

    @Query("SELECT s FROM Student s WHERE s.studentId IN :studentIdList")
    fun findStudentByStudentIdList(@Param("studentIdList") studentIdList: List<Long>): List<Student>

    fun findByUserId(userId: Long): Student?
}