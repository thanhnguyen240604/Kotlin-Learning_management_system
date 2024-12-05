package com.be.kotlin.grade.repository

import com.be.kotlin.grade.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    fun findLecturersByUsername(username: String): User
    fun existsByUsername(username: String): Boolean
}