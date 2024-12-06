package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.dto.studentDTO.StudentDTO
import org.springframework.stereotype.Component
import com.be.kotlin.grade.dto.studentDTO.StudentResponseDTO
import com.be.kotlin.grade.model.Student

@Component
class StudentMapper {
    fun toStudentResponseDto(student: Student, score : Float) : StudentResponseDTO{
        return StudentResponseDTO(
            name = student.user.name,
            score = score
        )
    }

    fun toStudentDTO(student: Student): StudentDTO? {
        return StudentDTO(
            studentId = student.studentId,
            enrolledCourse = student.enrolledCourse,
            major = student.major
        )
    }

    fun toStudent(studentDTO: StudentDTO) : Student {
        return Student(
            studentId = studentDTO.studentId,
            enrolledCourse = studentDTO.enrolledCourse,
            major = studentDTO.major
        )
    }
}