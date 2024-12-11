package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studentDTO.StudentDTO
import com.be.kotlin.grade.dto.studentDTO.StudentUpdateDTO
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.StudentMapper
import com.be.kotlin.grade.mapper.UserMapper
import com.be.kotlin.grade.repository.StudentRepository
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.repository.UserRepository
import com.be.kotlin.grade.service.interf.StudentInterface
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class StudentImplement(
    private val studentMapper: StudentMapper,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val studentRepository: StudentRepository,
    private val studyRepository: StudyRepository
) : StudentInterface {
    override fun register(userDTO: UserRequestDTO, studentDTO: StudentDTO): Response {

        if (userRepository.existsByUsername(userDTO.username) || studentRepository.existsByStudentId(studentDTO.studentId)) {
            throw AppException(ErrorCode.USER_EXISTED)
        }

        val user = userMapper.toUser(userDTO)
        user.role = "STUDENT"
        val passwordEncoder = BCryptPasswordEncoder(5)
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)

        val student = studentMapper.toStudent(studentDTO)
        student.user = user
        studentRepository.save(student)

        return Response(
            statusCode = 200,
            message = "Student registered",
            studentDTO = studentDTO,
            userDTO = userMapper.toUserDTO(user)
        )
    }

    override fun getStudentById(userId: Long): Response {
        val studentGot = studentRepository.findById(userId)
            .orElseThrow { AppException(ErrorCode.STUDENT_NOT_FOUND) }

        val studentDTO = studentMapper.toStudentDTO(studentGot)
        return Response(
            statusCode = 200,
            message = "Student found successfully",
            studentDTO = studentDTO
        )
    }

    override fun updateStudent(studentUpdateDTO: StudentUpdateDTO, username : String): Response {
        var student = studentRepository.findByUserUsername(username).orElseThrow { AppException(ErrorCode.STUDENT_NOT_FOUND) }

        student.user.name = studentUpdateDTO.studentName
        student.user.faculty = studentUpdateDTO.faculty

        studentRepository.save(student)

        return Response(
            statusCode = 200,
            message = "Student updated successfully",
          )
    }

    override fun calculateGPA(semester: Int): Response {
        val username = SecurityContextHolder.getContext().authentication.name

        val student = studentRepository.findByUserUsername(username).orElse(null)
            ?: throw AppException(ErrorCode.STUDENT_NOT_FOUND)

        // Lấy danh sách các môn học cho sinh viên trong học kỳ
        val studies = studyRepository.findByStudentUserUsernameAndSemester(username, semester)

        if (studies.isEmpty()) {
            throw AppException(ErrorCode.STUDY_NOT_FOUND)
        }
        var totalWeight = 0f
        var totalPoints = 0f

        // Duyệt qua từng môn học
        studies.forEach { study ->
            // Duyệt qua danh sách điểm của từng môn học
            study.gradesList.forEach { grade ->
                val score = grade.score // Điểm số
                val weight = grade.weight // Trọng số

                if (score > 0 && weight > 0) { // Chỉ tính điểm và trọng số hợp lệ
                    totalWeight += weight
                    totalPoints += score * weight
                }
            }
        }

        val gpa = if (totalWeight > 0) totalPoints / totalWeight else 0f

        return Response(
            statusCode = 200,
            message = "GPA calculated successfully",
            gpa = gpa
          )
    }
}