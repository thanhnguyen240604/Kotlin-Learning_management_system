package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.dto.userDTO.UserUpdateRequestDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.StudentMapper
import com.be.kotlin.grade.mapper.UserMapper
import com.be.kotlin.grade.model.Student
import com.be.kotlin.grade.model.User
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

    override fun createAdmin(userRequestDTO: UserRequestDTO): Response {
        if (userRepository.existsByUsername(userRequestDTO.username)) {
            throw AppException(ErrorCode.USER_EXISTED)
        }

        val user = userMapper.toUser(userRequestDTO)
        user.role = "ADMIN"
        val passwordEncoder = BCryptPasswordEncoder(5)
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)

        return Response (
            statusCode = 200,
            message = "Admin created successfully",
            userDTO = userMapper.toUserDTO(user)
        )
    }

    override fun createLecturer(userRequestDTO: UserRequestDTO): Response {
        if (userRepository.existsByUsername(userRequestDTO.username)) {
            throw AppException(ErrorCode.USER_EXISTED)
        }
        val sanitizedUsername = userRequestDTO.username.trim()

        // Kiểm tra đuôi email
        if (!sanitizedUsername.endsWith("@hcmut.edu.vn")) {
            throw AppException(ErrorCode.UNAUTHENTICATED_USERNAME_DOMAIN)
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

    fun createStudent(username: String, name: String): Response {
        if (!username.endsWith("@hcmut.edu.vn")) {
            throw AppException(ErrorCode.UNAUTHENTICATED_USERNAME_DOMAIN)
        }
        if (userRepository.existsByUsername(username)) {
            throw AppException(ErrorCode.USER_EXISTED)
        }

        val user = User(
            username = username,
            name = name,
            role = "STUDENT",
            isGoogleAccount = true,
        )
        userRepository.save(user)

        val student = Student()
        student.user = user
        studentRepository.save(student)

        //Thieu study progress

        return Response (
            statusCode = 200,
            message = "Student registered successfully"
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
        val existingUser = userRepository.findByUsername(userDTO.username)
            .orElseThrow { AppException(ErrorCode.USER_NOT_FOUND)}

        val sanitizedUsername = userDTO.username.trim()
        // Kiểm tra đuôi email
        if (!sanitizedUsername.endsWith("@hcmut.edu.vn")) {
            throw AppException(ErrorCode.UNAUTHENTICATED_USERNAME_DOMAIN)
        }

        existingUser.name = userDTO.name.toString()
        existingUser.faculty = userDTO.faculty
        existingUser.username = userDTO.username
        userDTO.major?.let {
            val studentGot = existingUser.id?.let { it1 -> studentRepository.findByUserId(it1) }
            if (studentGot != null) {
                studentGot.major = userDTO.major
                studentRepository.save(studentGot)
            }
        }
        userRepository.save(existingUser)
        return Response(
            statusCode = 200,
            message = "Update info successfully"
        )
    }

    override fun getAllLecturers(pageable: Pageable): Response {
        val lecturers = userRepository.findByRole("LECTURER", pageable)
        val listLecturers = lecturers.content.map{ lecturer -> userMapper.toLecturerDTO(lecturer) }
        return Response(
            statusCode = 200,
            message = "Lecturers found successfully",
            lecturers = listLecturers
        )
    }

    override fun getAllStudents(pageable: Pageable): Response {
        val students = userRepository.findByRole("STUDENT", pageable)
        val listStudents = students.content.map{ student -> userMapper.toStudentDTO(student) }
        return Response(
            statusCode = 200,
            message = "Students found successfully",
            lecturers = listStudents
        )
    }

    override fun getAllLecturersUsername(): Response {
        val lecturersUsername = userRepository.findUsernameByRole("LECTURER")
        return Response(
            statusCode = 200,
            message = "Lecturers' username list found successfully",
            lecturersUsername = lecturersUsername
        )
    }
}