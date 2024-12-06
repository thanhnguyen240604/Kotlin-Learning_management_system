package com.be.kotlin.grade.repository

import jakarta.transaction.Transactional
import com.be.kotlin.grade.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findLecturersByUsername(username: String): User
    fun existsByUsername(username: String): Boolean
    fun deleteByUsername(username:String)
    fun findByUsername(username: String): Optional<User>

//    @Transactional
//    @Modifying
//    @Query("update User a set a.role=:role where a.username=:username")
//    fun updateUserRole(@Param("role") role:String,
//                       @Param("username") username: String)

    @Transactional
    @Modifying
    @Query("update User a set a.name=:name, a.faculty=:faculty where a.username=:username")
    fun updateUserInfo(@Param("name") name:String,
        @Param("faculty") faculty:String,
                       @Param("username") username: String)
}