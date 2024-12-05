package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.Student
import com.be.kotlin.grade.User
import com.be.kotlin.grade.dto.studentDTO.StudentDTO
import com.be.kotlin.grade.dto.userDTO.UserResponseDTO
import org.springframework.stereotype.Component

@Component
class StudentMapper {
    fun toStudent (request: StudentDTO): Student {
        return Student(
            studentId = request.studentId,
            enrolledCourse = request.enrolledCourse,
            major = request.major
        )
    }

    fun toStudentDTO(student: Student): StudentDTO? {
        return student.studentId?.let {
            StudentDTO(
                studentId = it,
                enrolledCourse = student.enrolledCourse,
                major = student.major
            )
        }
    }
}