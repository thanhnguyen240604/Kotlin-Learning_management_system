package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.dto.userDTO.UserUpdateRequestDTO
import org.springframework.data.domain.Pageable

interface UserInterface {
    fun createLecturer(userRequestDTO: UserRequestDTO): Response
    fun findUserById(id: Long): Response
    fun findAllUser(pageable: Pageable): Response
    fun delUser(username:String):Response;
//    fun updateRole(role: String,username: String): Response
    fun updateInfo(userDTO: UserUpdateRequestDTO): Response
}