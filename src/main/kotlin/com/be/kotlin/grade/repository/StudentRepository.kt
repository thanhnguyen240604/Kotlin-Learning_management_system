package com.be.kotlin.grade.repository

import com.be.kotlin.grade.model.Student
import com.be.kotlin.grade.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StudentRepository: JpaRepository<Student, Long> {
    fun findByUserUsername(username: String): Optional<Student>
    fun existsByStudentId(id: Long): Boolean
}