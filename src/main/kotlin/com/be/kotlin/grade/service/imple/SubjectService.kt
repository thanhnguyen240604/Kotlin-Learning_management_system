package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectIdDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectRegisterDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.SubjectMapper
import com.be.kotlin.grade.model.Class
import com.be.kotlin.grade.model.Study
import com.be.kotlin.grade.repository.ClassRepository
import com.be.kotlin.grade.repository.StudentRepository
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.service.interf.ISubject
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val subjectMapper: SubjectMapper,
    private val classRepository: ClassRepository,
    private val studentRepository: StudentRepository,
    private val studyRepository: StudyRepository
): ISubject {
    override fun addSubject(subject: SubjectDTO): Response {
        if (subjectRepository.findById(subject.id).isPresent) {
            throw AppException(ErrorCode.SUBJECT_EXISTED)
        }

        val newSubject = subjectMapper.toSubject(subject)
        subjectRepository.save(newSubject)

        return Response(
            subjectDTO = subject,
            statusCode = 200,
            message = "Subject added successfully")
    }

    override fun deleteSubject(subject: SubjectIdDTO): Response {
        if (!subjectRepository.findById(subject.id).isPresent)
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)

        val deletedSubject = subjectRepository.findById(subject.id).get()

        subjectRepository.deleteById(subject.id)
        return Response(
            subjectDTO = subjectMapper.toSubjectDTO(deletedSubject),
            statusCode = 200,
            message = "Subject deleted successfully"
        )
    }

    override fun updateSubject(subject: SubjectDTO): Response {
        if (!subjectRepository.findById(subject.id).isPresent)
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)

        val updatedSubject = subjectMapper.toSubject(subject)
        subjectRepository.save(updatedSubject)

        return Response(
            subjectDTO = subject,
            statusCode = 200,
            message = "Subject updated successfully")
    }

    override fun getSubjectById(subject: SubjectIdDTO): Response {
        val subjectGot = subjectRepository.findById(subject.id)
            .orElseThrow { AppException(ErrorCode.SUBJECT_NOT_FOUND) }

        val subjectDTO = subjectMapper.toSubjectDTO(subjectGot);

        return Response(
            statusCode = 200,
            message = "Subject found successfully",
            subjectDTO = subjectDTO
        )
    }

    override fun getAllSubjects(page: Int, size: Int): Response {
        // Tạo Pageable object
        val pageable = PageRequest.of(page, size)

        // Lấy danh sách subject từ repository
        val subjectPage = subjectRepository.findAll(pageable)

        // Chuyển đổi các entity sang DTO
        val subjectDTOs = subjectPage.content.map { subject -> subjectMapper.toSubjectDTO(subject) }

        // Trả về kết quả phân trang
        return Response(
            statusCode = 200,
            message = "Subjects fetched successfully",
            totalPages = subjectPage.totalPages,  // Lấy tổng số trang
            totalElements = subjectPage.totalElements,  // Lấy tổng số phần tử
            currentPage = page,
            listSubjectDTO = subjectDTOs
        )
    }

    override fun getNextSemester(): Response {
        val time = LocalDateTime.now()
        val nextSemester: Int
        nextSemester = when (time.month) {
            in Month.JULY..Month.AUGUST -> time.year % 100 * 10 + 1 // Năm tiếp theo, kỳ 1
            in Month.SEPTEMBER..Month.DECEMBER -> time.year % 100 * 10 + 2 // Kỳ 2 của năm hiện tại
            in Month.JANUARY..Month.JUNE -> (time.year % 100 - 1) * 10 + 3 // Kỳ 3 của năm hiện tại
            else -> throw IllegalStateException("Invalid month")
        }
        return Response(
            statusCode = 200,
            message = "This is the next semester",
            nextSemester = nextSemester
        )
    }

    override fun registerSubject(subjectRegister: SubjectRegisterDTO): Response {
        val existingSubject = subjectRepository.findById(subjectRegister.subjectId).
            orElseThrow { AppException(ErrorCode.SUBJECT_NOT_FOUND) }

        var newClass = classRepository.findBySubjectAndNameAndSemester(
            subjectRegister.subjectId,
            "L00",
            subjectRegister.semester
        )

        if (newClass == null) {
            newClass = Class (
                name = "L00",
                subject = existingSubject,
                semester = subjectRegister.semester
            )
            classRepository.save(newClass)
        }

        val username = SecurityContextHolder.getContext().authentication.name
        val student = studentRepository.findByUserUsername(username).orElse(null)
            ?: throw AppException(ErrorCode.STUDENT_NOT_FOUND)

        val newStudy = Study(
            student = student,
            studyClass = newClass,
            score = 0F
        )
        studyRepository.save(newStudy)
        return Response (
            statusCode = 200,
            message = "Subject registered successfully",
            subjectRegisterDTO = subjectRegister
        )
    }

    override fun getRegisterNumber(subjectRegister: SubjectRegisterDTO): Response {
        val existingClass = classRepository.findBySubjectAndNameAndSemester(
            subjectRegister.subjectId,
            "L00",
            subjectRegister.semester
        )

        val registerNum = existingClass?.id?.let { studyRepository.countByClassId(it) } ?: 0

        return Response(
            statusCode = 200,
            message = if (existingClass == null) {
                "No student has registered this subject this semester yet"
            } else {
                "Number of student registered fetch successfully"
            },
            registerNum = registerNum
        )
    }
}