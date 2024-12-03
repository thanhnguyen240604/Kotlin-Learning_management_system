package com.be.kotlin.grade.repository

import org.springframework.data.jpa.repository.JpaRepository

interface StudyRepository: JpaRepository<Study, Long> {
}
