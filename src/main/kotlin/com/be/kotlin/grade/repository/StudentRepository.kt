package com.be.kotlin.grade.repository

import com.be.kotlin.grade.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository: JpaRepository<Student, Long> {
}