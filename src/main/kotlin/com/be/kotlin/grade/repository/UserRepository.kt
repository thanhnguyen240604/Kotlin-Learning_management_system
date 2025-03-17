package com.be.kotlin.grade.repository

import jakarta.transaction.Transactional
import com.be.kotlin.grade.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): Optional<User>
    fun findByRole(s: String, pageable: Pageable): Page<User>

    @Query("""
        SELECT u.username
        FROM User u
        WHERE u.role = :role
    """)
    fun findUsernameByRole(role: String): List<String>
}