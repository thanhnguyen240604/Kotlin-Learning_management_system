package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectIdDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectRegisterDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.ClassMapper
import com.be.kotlin.grade.mapper.SubjectMapper
import com.be.kotlin.grade.model.Class
import com.be.kotlin.grade.model.Study
import com.be.kotlin.grade.repository.ClassRepository
import com.be.kotlin.grade.repository.StudentRepository
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.service.interf.ISubject
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val subjectMapper: SubjectMapper,
    private val classRepository: ClassRepository,
    private val studentRepository: StudentRepository,
    private val studyRepository: StudyRepository,
    private val classMapper: ClassMapper
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
        ) ?: throw AppException(ErrorCode.CLASS_NOT_FOUND)

        val registerNum = existingClass.id?.let { studyRepository.countByClassId(it) }

        return Response(
            statusCode = 200,
            message = if (registerNum == 0) {
                "No student has registered this subject in this semester yet"
            } else {
                "Number of student registered this subject in this semester fetch successfully"
            },
            registerNum = registerNum
        )
    }

    @Transactional
    override fun openClasses(subjectRequest: SubjectRegisterDTO): Response {
        val baseClass = classRepository.findBySubjectAndNameAndSemester(
            subjectRequest.subjectId,
            "L00",
            subjectRequest.semester
        ) ?: throw AppException(ErrorCode.CLASS_NOT_FOUND)

        val registeredStudies = studyRepository.findByStudyClass(baseClass)
        val registerNum = registeredStudies.size

        val maxClass = if (registerNum / subjectRequest.maxStudent!! < 1) {
            registerNum / subjectRequest.maxStudent!! + 1
        } else {
            registerNum / subjectRequest.maxStudent!!
        }

        val averageStudentPerClass = registerNum / maxClass
        val leftOverStudent = registerNum % maxClass

        val newClasses = mutableListOf<Class>()
        val studentIterator = registeredStudies.iterator()

        for (i in 1..maxClass) {
            // Tạo lớp mới
            val className = "L0$i"
            val newClass = Class(
                name = className,
                subject = baseClass.subject,
                semester = baseClass.semester,
                maxStudent = subjectRequest.maxStudent
            )
            val savedClass = classRepository.save(newClass) // Lưu lớp
            classRepository.flush() // Đồng bộ hóa lớp mới vào DB
            newClasses.add(savedClass)

            // Phân bổ sinh viên vào lớp
            val studentLimit = averageStudentPerClass + if (i <= leftOverStudent) 1 else 0
            var count = 0
            while (studentIterator.hasNext() && count < studentLimit) {
                val study = studentIterator.next()
                study.studyClass = savedClass // Gán lớp học mới
                studyRepository.save(study) // Lưu lại Study
                count++
            }
        }
        baseClass.id?.let { classRepository.deleteById(it) }

        return Response(
            statusCode = 200,
            message = "Classes opened successfully",
            listClassDTO = newClasses.map { classMapper.toClassDTO(it) }
        )
    }


}