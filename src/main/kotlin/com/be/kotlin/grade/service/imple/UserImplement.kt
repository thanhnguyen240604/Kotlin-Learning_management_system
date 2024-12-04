package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.userDTO.UserDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.UserMapper
import com.be.kotlin.grade.repository.UserRepository
import com.be.kotlin.grade.service.interf.UserInterface
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserImplement (
    private var userRepository: UserRepository,
    private val userMapper: UserMapper
): UserInterface {
    override fun register(request: UserDTO): Response {
        if (userRepository.existsByUsername(request.username)) {
            throw AppException(ErrorCode.USER_EXISTED)
        }

        val user = userMapper.toUser(request)
        user.role = "USER"
        val passwordEncoder = BCryptPasswordEncoder(5)
        user.password = passwordEncoder.encode(user.password)

        userRepository.save(user)

        return Response(
            statusCode = 200,
            message = "User registered",
        )
    }
}