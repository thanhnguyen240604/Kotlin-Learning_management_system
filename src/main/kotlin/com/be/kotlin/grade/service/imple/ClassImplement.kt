package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.ClassMapper
import com.be.kotlin.grade.repository.ClassRepository
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.repository.UserRepository
import com.be.kotlin.grade.service.interf.ClassInterface
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
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

        val context = SecurityContextHolder.getContext()
        val username = context.authentication.name
        val lecturer = userRepository.findLecturersByUsername(username)
        newClass.lecturers.add(lecturer)

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

        val context = SecurityContextHolder.getContext()
        val username = context.authentication.name
        val lecturer = userRepository.findLecturersByUsername(username)
        if (!existingClass.lecturers.contains(lecturer))
            throw AppException(ErrorCode.CLASS_INVALID)

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

    override fun deleteClass(id: Long): Response {
        val existingClass = classRepository.findById(id)
            .orElseThrow { AppException(ErrorCode.CLASS_NOT_FOUND) }

        val context = SecurityContextHolder.getContext()
        val username = context.authentication.name
        val lecturer = userRepository.findLecturersByUsername(username)
        if (!existingClass.lecturers.contains(lecturer))
            throw AppException(ErrorCode.CLASS_INVALID)

        classRepository.deleteById(id)

        return Response(
            classDTO = classMapper.toClassDTO(existingClass),
            statusCode = 200,
            message = "Class deleted successfully"
        )
    }
}