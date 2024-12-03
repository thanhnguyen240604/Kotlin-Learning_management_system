package com.be.kotlin.grade.repository

import com.be.kotlin.grade.Grade
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GradeRepository: JpaRepository<Grade, Long> {
}