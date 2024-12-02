package com.be.kotlin.grade.repository

import hcmut.example.gradeportalbe.model.Grade
import org.springframework.data.jpa.repository.JpaRepository

interface GradeRepository: JpaRepository<Grade, Long> {
}