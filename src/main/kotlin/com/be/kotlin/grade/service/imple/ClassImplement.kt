package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.dto.classDTO.ClassIdDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.ClassMapper
import com.be.kotlin.grade.repository.ClassRepository
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.repository.UserRepository
import com.be.kotlin.grade.service.interf.ClassInterface
import org.springframework.stereotype.Service

@Service
class ClassImplement(
    private val classRepository: ClassRepository,
    private val subjectRepository: SubjectRepository,
    private val userRepository: UserRepository,
    private val classMapper: ClassMapper
) : ClassInterface {

    override fun addClass(classDTO: ClassDTO): Response {
        val subject = subjectRepository.findById(classDTO.subjectId)
            .orElseThrow { AppException(ErrorCode.SUBJECT_NOT_FOUND) }

        val newClass = classMapper.toClass(classDTO, subject)
        classRepository.save(newClass)

        return Response(
            classDTO = classMapper.toClassDTO(newClass),
            statusCode = 200,
            message = "Class added successfully"
        )
    }

    override fun updateClass(classDTO: ClassDTO): Response {
        val existingClass = classRepository.findById(classDTO.id!!)
            .orElseThrow { AppException(ErrorCode.CLASS_NOT_FOUND) }

        val subject = subjectRepository.findById(classDTO.subjectId)
            .orElseThrow { AppException(ErrorCode.SUBJECT_NOT_FOUND) }

        val updatedClass = classMapper.toClass(classDTO, subject)
        classRepository.save(updatedClass)

        return Response(
            classDTO = classMapper.toClassDTO(updatedClass),
            statusCode = 200,
            message = "Class updated successfully"
        )
    }

    override fun deleteClass(classIdDTO: ClassIdDTO): Response {
        val existingClass = classRepository.findById(classIdDTO.id)
            .orElseThrow { AppException(ErrorCode.CLASS_NOT_FOUND) }

        classRepository.delete(existingClass)

        return Response(
            classDTO = null,
            statusCode = 200,
            message = "Class deleted successfully"
        )
    }
}