package com.be.kotlin.grade.repository

import com.be.kotlin.grade.model.Study
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.ResponseStatus

@Repository
interface StudyRepository: JpaRepository<Study, Long> {
}