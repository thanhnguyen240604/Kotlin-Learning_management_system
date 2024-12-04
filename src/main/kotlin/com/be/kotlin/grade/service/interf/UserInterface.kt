package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.UserDTO


interface UserInterface {
    fun register(request: UserDTO): Response
}