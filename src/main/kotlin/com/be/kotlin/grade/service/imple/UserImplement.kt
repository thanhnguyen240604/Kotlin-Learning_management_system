package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.dto.userDTO.UserUpdateRequestDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.UserMapper
import com.be.kotlin.grade.repository.UserRepository
import com.be.kotlin.grade.service.interf.UserInterface
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserImplement (
    private var userRepository: UserRepository,
    private val userMapper: UserMapper
): UserInterface {
    override fun createLecturer(userRequestDTO: UserRequestDTO): Response {
        if (userRepository.existsByUsername(userRequestDTO.username)) {
            throw AppException(ErrorCode.USER_EXISTED)
        }

        val user = userMapper.toUser(userRequestDTO)
        user.role = "LECTURER"
        val passwordEncoder = BCryptPasswordEncoder(5)
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)

        return Response (
            statusCode = 200,
            message = "Lecturer created successfully",
            userDTO = userMapper.toUserDTO(user)
        )
    }

    override fun findUserById(id: Long): Response {
        val userGot = userRepository.findById(id)
            .orElseThrow { AppException(ErrorCode.SUBJECT_NOT_FOUND) }
        val userDTO = userMapper.toUserDTO(userGot)
        return Response(
            statusCode = 200,
            message = "Study found successfully",
            userDTO = userDTO
        )
    }

    override fun findAllUser(pageable: Pageable): Response {
        val userPage = userRepository.findAll(pageable)
        val userDTOList = userPage.content.map { userMapper.toUserDTO(it) }

        return Response(
            statusCode = 200,
            message = "Users found successfully",
            listUserDTO = userDTOList,
            currentPage = userPage.number,
            totalElements = userPage.totalElements,
            totalPages = userPage.totalPages
        )
    }

    override fun delUser(username : String):Response{
        if(userRepository.findByUsername(username).orElse(null)==null){
            return Response(
                statusCode = 300,
                message = "User doesn't exist"
            )
        }
        userRepository.deleteByUsername(username);
        return Response(
            statusCode = 200,
            message = "Subject added successfully"
        )
    }
//    override fun updateRole(role: String, username: String): Response {
//        if(userRepository.findByUsername(username).orElse(null)==null){
//            return Response(
//                statusCode = 300,
//                message = "User doesn't exist"
//            )
//        }
//        userRepository.updateUserRole(role,username)
//        return Response(
//            statusCode = 200,
//            message = "Update role successfully"
//        )
//    }

    override fun updateInfo(userDTO: UserUpdateRequestDTO): Response {
        if (userRepository.existsByUsername(userDTO.username)) {
            throw AppException(ErrorCode.USER_EXISTED)
        }
        //userDTO.faculty?.let { userRepository.updateUserInfo(it, userDTO.username) }
        userDTO.faculty?.let { userRepository.updateUserInfo(userDTO.name,it,userDTO.username) }
        return Response(
            statusCode = 200,
            message = "Update info successfully"
        )
    }
}