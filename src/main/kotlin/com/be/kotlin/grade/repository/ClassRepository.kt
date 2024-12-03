package com.be.kotlin.grade.repository

import com.be.kotlin.grade.Class
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClassRepository: JpaRepository<Class, Long> {
}