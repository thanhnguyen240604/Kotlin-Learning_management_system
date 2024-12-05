package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.User
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.dto.userDTO.UserResponseDTO
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toUser(request: UserRequestDTO): User {
        return User(
            name = request.name,
            faculty = request.faculty,
            username = request.username,
            password = request.password
        )
    }

    fun toUserDTO(user: User): UserResponseDTO {
        return UserResponseDTO(
            id = user.id,
            name = user.name,
            faculty = user.faculty,
            role = user.role,
            username = user.username
        )
    }
}