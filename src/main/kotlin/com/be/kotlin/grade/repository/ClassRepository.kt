package com.be.kotlin.grade.repository

import org.springframework.data.jpa.repository.JpaRepository

interface ClassRepository: JpaRepository<Class, Long> {
}
