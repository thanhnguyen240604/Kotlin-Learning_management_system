package com.be.kotlin.grade.repository

import hcmut.example.gradeportalbe.model.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudenRepository: JpaRepository<Student, Long> {
}