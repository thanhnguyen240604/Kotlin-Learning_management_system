package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.model.User
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.dto.userDTO.UserResponseDTO
import com.be.kotlin.grade.repository.StudentRepository
import org.springframework.stereotype.Component

@Component
class UserMapper (
    private val studentRepository: StudentRepository,
    private val studentMapper: StudentMapper
){
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

    fun toLecturerDTO(user: User): UserResponseDTO {
        return UserResponseDTO(
            id = user.id,
            name = user.name,
            username = user.username,
            faculty = user.faculty
        )
    }

    fun toStudentDTO(user: User): UserResponseDTO {
        val student = user.id?.let { studentRepository.findByUserId(it) }
        return UserResponseDTO(
            id = user.id,
            name = user.name,
            username = user.username,
            faculty = user.faculty,
            studentDTO = student?.let { studentMapper.toStudentDTO(it) }
        )
    }
}