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
import com.be.kotlin.grade.service.interf.IStudent
import com.be.kotlin.grade.service.interf.IStudyProgress
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentMapper: StudentMapper,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val studentRepository: StudentRepository,
    private val studyRepository: StudyRepository,
    private val studyProgressService: IStudyProgress
) : IStudent {
    override fun register(userDTO: UserRequestDTO, studentDTO: StudentDTO): Response {
        val sanitizedUsername = userDTO.username.trim()

        // Kiểm tra đuôi email, bỏ qua nếu thuộc danh sách ngoại lệ
        if ( !sanitizedUsername.endsWith("@hcmut.edu.vn")) {
            throw AppException(ErrorCode.UNAUTHENTICATED_USERNAME_DOMAIN)
        }

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

        studyProgressService.addStudyProgress(studentDTO.studentId)

        return Response(
            statusCode = 200,
            message = "Student registered"
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

    override fun getStudentList(classId: Long): Response {
        // Lấy danh sách studentId tương ứng với classId
        val studentIdList = studyRepository.findStudentIdByClassId(classId)

        // Nếu không tìm thấy bất kỳ studentId nào, trả về thông báo lỗi
        if (studentIdList.isEmpty()) {
            return Response(
                statusCode = 404,
                message = "No studies found for classId $classId",
                listStudent = emptyList()
            )
        }

        // Lấy danh sách student từ studentIdList
        val studentList = studentRepository.findStudentByStudentIdList(studentIdList)

        // Chuyển đổi danh sách studentEntity thành studentDTO
        val studentListDTO = studentList.mapNotNull { studentEntity ->
            studentMapper.toStudentDTO(studentEntity)
        }

        // Trả về danh sách studentDTO
        return Response(
            statusCode = 200,
            message = "Get Student List successfully",
            listStudent = studentListDTO
        )
    }

//    override fun getStudentById(userId: Long): Response {
//        val studentGot = studentRepository.findById(userId)
//            .orElseThrow { AppException(ErrorCode.STUDENT_NOT_FOUND) }
//
//        val studentDTO = studentMapper.toStudentDTO(studentGot)
//        return Response(
//            statusCode = 200,
//            message = "Student found successfully",
//            studentDTO = studentDTO
//        )
//    }

//    override fun updateStudent(studentUpdateDTO: StudentUpdateDTO, username : String): Response {
//        val student = studentRepository.findByUserUsername(username).orElseThrow { AppException(ErrorCode.STUDENT_NOT_FOUND) }
//        if (studentRepository.existsByStudentId(studentUpdateDTO.studentId)) {
//            throw AppException(ErrorCode.STUDENT_ID_EXISTED)
//        }
//        student.user.name = studentUpdateDTO.studentName
//        student.user.faculty = studentUpdateDTO.faculty
//        student.studentId = studentUpdateDTO.studentId
//        studentRepository.save(student)
//
//        return Response(
//            statusCode = 200,
//            message = "Student updated successfully",
//        )
//    }
}