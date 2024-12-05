package com.be.kotlin.grade.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import  com.be.kotlin.grade.Class
import java.util.Optional

@Repository
interface ClassRepository: JpaRepository<Class, Long> {
   // fun findById(id : Long) : Optional<Class>
}