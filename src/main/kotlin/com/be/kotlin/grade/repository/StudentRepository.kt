package com.be.kotlin.grade.repository

import com.be.kotlin.grade.model.Student
import com.be.kotlin.grade.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository: JpaRepository<Student, Long> {
    fun findByUser(user: User): Student
    fun findByUserUsername(username: String): Student
    fun existsByStudentId(id: Long): Boolean
}