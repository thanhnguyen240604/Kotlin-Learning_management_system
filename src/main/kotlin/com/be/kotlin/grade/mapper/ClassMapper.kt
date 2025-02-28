package com.be.kotlin.grade.mapper

import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.dto.classDTO.UpdateClassDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.model.Class
import com.be.kotlin.grade.model.Subject
import com.be.kotlin.grade.model.SubjectRelation
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.repository.UserRepository
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class ClassMapper (
    private val subjectRepository: SubjectRepository,
    private val userRepository: UserRepository
){
    fun toClass(classDTO: ClassDTO): Class? {
        val subject = subjectRepository.findById(classDTO.subjectId)
            .orElseThrow { AppException(ErrorCode.SUBJECT_NOT_FOUND) }

        val lecturersList = classDTO.lecturersUsernameList?.map { username ->
            userRepository.findByUsername(username).
                    orElse(throw AppException(ErrorCode.USER_NOT_FOUND))
        }?.toMutableList() ?: mutableListOf()

        return Class(
            name = classDTO.name,
            subject = subject,
            semester = classDTO.semester,
            daysOfWeek = classDTO.dayOfWeek,
            startTime = classDTO.startTime,
            endTime = classDTO.endTime,
            lecturers = lecturersList
        )
    }

    fun toClassDTO(classEntity: Class): ClassDTO {
        val lecturersUsernameList = classEntity.lecturers.map{ lecturers ->
            lecturers.username
        }.toMutableList()
        return ClassDTO(
            id = classEntity.id,
            name = classEntity.name,
            subjectId = classEntity.subject.id,
            semester = classEntity.semester,
            dayOfWeek = classEntity.daysOfWeek,
            startTime = classEntity.startTime,
            endTime = classEntity.endTime,
            lecturersUsernameList = lecturersUsernameList
        )
    }

    fun toClass(updateClassDTO: UpdateClassDTO): Class? {
        val subject = updateClassDTO.subjectId?.let {
            subjectRepository.findById(it)
                .orElseThrow { AppException(ErrorCode.SUBJECT_NOT_FOUND) }
        }

        val lecturersList = updateClassDTO.lecturersUsernameList?.map { username ->
            userRepository.findByUsername(username).
            orElse(throw AppException(ErrorCode.USER_NOT_FOUND))
        }?.toMutableList() ?: mutableListOf()

        return updateClassDTO.name?.let {
            updateClassDTO.semester?.let { it1 ->
                updateClassDTO.dayOfWeek?.let { it2 ->
                    updateClassDTO.startTime?.let { it3 ->
                        updateClassDTO.endTime?.let { it4 ->
                            subject?.let { it5 ->
                                Class(
                                    name = it,
                                    subject = it5,
                                    semester = it1,
                                    daysOfWeek = it2,
                                    startTime = it3,
                                    endTime = it4,
                                    lecturers = lecturersList
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}