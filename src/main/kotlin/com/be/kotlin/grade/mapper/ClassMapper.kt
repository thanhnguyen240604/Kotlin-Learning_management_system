package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.dto.classDTO.UpdateClassDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.model.Class
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class ClassMapper (
    private val subjectRepository: SubjectRepository,
    private val userRepository: UserRepository
){
    fun toClass(classDTO: ClassDTO): Class? {
        val subject = subjectRepository.findById(classDTO.subjectId)
            .orElseThrow { AppException(ErrorCode.SUBJECT_NOT_FOUND) }

        return Class(
            name = classDTO.name,
            subject = subject,
            semester = classDTO.semester,
            dayOfWeek = classDTO.dayOfWeek,
            startTime = classDTO.startTime,
            endTime = classDTO.endTime,
        )
    }

    fun toClassDTO(classEntity: Class): ClassDTO {
        return ClassDTO(
            id = classEntity.id,
            name = classEntity.name,
            subjectId = classEntity.subject.id,
            semester = classEntity.semester,
            dayOfWeek = classEntity.dayOfWeek,
            startTime = classEntity.startTime,
            endTime = classEntity.endTime,
        )
    }

    fun toGetAllClassDTO(classEntity: Class): ClassDTO {
        return ClassDTO(
            id = classEntity.id,
            name = classEntity.name,
            subjectId = classEntity.subject.id,
            semester = classEntity.semester,
            lecturersUsername = classEntity.lecturersUsername
        )
    }

//    fun toClass(updateClassDTO: UpdateClassDTO): Class? {
//        val lecturersList = updateClassDTO.lecturersUsernameList?.map { username ->
//            userRepository.findByUsername(username).
//            orElse(throw AppException(ErrorCode.USER_NOT_FOUND))
//        }?.toMutableList() ?: mutableListOf()
//
//        return Class(
//            name =
//        )
//        }
//    }
}