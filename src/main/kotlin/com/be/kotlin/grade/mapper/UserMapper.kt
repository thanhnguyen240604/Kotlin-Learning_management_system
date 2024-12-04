package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.User
import com.be.kotlin.grade.dto.userDTO.UserDTO
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toUser(request: UserDTO): User {
        return User(
            name = request.name,
            faculty = request.faculty,
            username = request.username,
            password = request.password
        )
    }
}