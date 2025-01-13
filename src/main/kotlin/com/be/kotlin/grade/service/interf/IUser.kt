package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.dto.userDTO.UserUpdateRequestDTO
import org.springframework.data.domain.Pageable

interface IUser {
    fun createLecturer(userRequestDTO: UserRequestDTO): Response
    fun findUserById(id: Long): Response
    fun findAllUser(pageable: Pageable): Response
    fun deleteUser(id: Long):Response;
    fun updateInfo(userDTO: UserUpdateRequestDTO): Response
    fun getMyInfo(): Response
}