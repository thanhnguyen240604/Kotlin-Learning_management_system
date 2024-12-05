package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import org.springframework.data.domain.Pageable


interface UserInterface {
    fun createLecturer(userRequestDTO: UserRequestDTO): Response
    fun findUserById(id: Long): Response
    fun findAllUser(pageable: Pageable): Response
}