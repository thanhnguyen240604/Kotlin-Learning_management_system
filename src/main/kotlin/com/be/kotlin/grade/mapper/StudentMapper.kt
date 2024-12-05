package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.Student
import com.be.kotlin.grade.dto.StudentDTO.StudentResponseDto

class StudentMapper {
    fun toStudentResponseDto(student: Student, score : Float) : StudentResponseDto{
        return StudentResponseDto(
            name = student.user.name,
            score = score
        )
    }
}