package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.controller.StudentController
import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.dto.userDTO.UserUpdateRequestDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.StudentMapper
import com.be.kotlin.grade.mapper.UserMapper
import com.be.kotlin.grade.repository.StudentRepository
import com.be.kotlin.grade.repository.UserRepository
import com.be.kotlin.grade.service.interf.IUser
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private var userRepository: UserRepository,
    private val userMapper: UserMapper,
    private var studentRepository: StudentRepository,
    private var studentMapper: StudentMapper
): IUser {
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
            message = "User found successfully",
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

    override fun deleteUser(id: Long):Response{
        if (!userRepository.existsById(id)){
            throw AppException(ErrorCode.USER_NOT_FOUND)
        }
        userRepository.deleteById(id);
        return Response(
            statusCode = 200,
            message = "Subject added successfully"
        )
    }

    override fun updateInfo(userDTO: UserUpdateRequestDTO): Response {
        if (!userRepository.existsByUsername(userDTO.username)) {
            throw AppException(ErrorCode.USER_NOT_FOUND)
        }
        //userDTO.faculty?.let { userRepository.updateUserInfo(it, userDTO.username) }
        userDTO.faculty?.let { userRepository.updateUserInfo(userDTO.name,it,userDTO.username) }
        return Response(
            statusCode = 200,
            message = "Update info successfully"
        )
    }

    override fun getMyInfo(): Response {
        val context = SecurityContextHolder.getContext()
        val username = context.authentication.name
        val user = userRepository.findByUsername(username)
        .orElseThrow { AppException(ErrorCode.USER_NOT_FOUND) }
        if (user.role == "STUDENT"){
            val student = studentRepository.findByUserUsername(username).orElseThrow{ AppException(ErrorCode.STUDENT_NOT_FOUND) }
            return Response(
                statusCode = 200,
                message = "My info fetch successfully",
                userDTO = userMapper.toUserDTO(user),
                studentDTO = studentMapper.toStudentDTO(student)
            )
        }
        return Response(
            statusCode = 200,
            message = "My info fetch successfully",
            userDTO = userMapper.toUserDTO(user),
        )
    }
}