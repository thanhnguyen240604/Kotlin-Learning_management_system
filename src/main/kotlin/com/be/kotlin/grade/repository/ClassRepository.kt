package com.be.kotlin.grade.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import  com.be.kotlin.grade.model.Class

@Repository
interface ClassRepository: JpaRepository<Class, Long> {
}