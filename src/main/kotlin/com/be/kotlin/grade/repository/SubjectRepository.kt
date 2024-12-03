package com.be.kotlin.grade.repository

import com.be.kotlin.grade.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository: JpaRepository<Subject, Long> {
}
