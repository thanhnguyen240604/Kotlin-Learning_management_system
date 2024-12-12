package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studentDTO.StudentResponseDTO
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.ClassMapper
import com.be.kotlin.grade.mapper.StudentMapper
import com.be.kotlin.grade.mapper.UserMapper
import com.be.kotlin.grade.repository.ClassRepository
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.repository.UserRepository
import com.be.kotlin.grade.service.interf.IClass
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class ClassService(
    private val classRepository: ClassRepository,
    private val subjectRepository: SubjectRepository,
    private val userRepository: UserRepository,
    private val classMapper: ClassMapper,
    private val studyRepository: StudyRepository,
    private val studentMapper: StudentMapper,
    private val userMapper: UserMapper
) : IClass {

    override fun addClass(classDTO: ClassDTO): Response {
        val subject = subjectRepository.findById(classDTO.subjectId)
            .orElseThrow { AppException(ErrorCode.SUBJECT_NOT_FOUND) }

        val newClass = classMapper.toClass(classDTO, subject)

        val context = SecurityContextHolder.getContext()
        val username = context.authentication.name
        val lecturer = userRepository.findLecturersByUsername(username)

        if (lecturer.id?.let { classRepository.existsByLecturerSubjectAndClassName(it, subject.id, newClass.name) } == true) {
            throw AppException(ErrorCode.CLASS_EXISTED)
        }

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

    override fun getClassById(id: Long): Response {
        val classGot = classRepository.findById(id)
            .orElseThrow { AppException(ErrorCode.CLASS_NOT_FOUND) }

        val classDTO = classMapper.toClassDTO(classGot)
        return Response(
            statusCode = 200,
            message = "class found successfully",
            classDTO = classDTO
        )
    }

    override fun getAllClasses(page: Int, size: Int): Response {
        // Tạo Pageable object
        val pageable = PageRequest.of(page, size)

        // Lấy danh sách subject từ repository
        val classPage = classRepository.findAll(pageable)

        // Chuyển đổi các entity sang DTO
        val classDTOs = classPage.content.map { classEntity ->
            classMapper.toClassDTO(classEntity)
        }

        // Trả về kết quả phân trang
        return Response(
            statusCode = 200,
            message = "Classes fetched successfully",
            totalPages = classPage.totalPages,  // Lấy tổng số trang
            totalElements = classPage.totalElements,  // Lấy tổng số phần tử
            currentPage = page,
            listClassDTO = classDTOs
        )
    }

    override fun getAllStudentClasses(page: Int, size: Int): Response {
        val context = SecurityContextHolder.getContext()
        val username = context.authentication?.name

        val user = username?.let {
            userRepository.findByUsername(it).orElseThrow {
                AppException(ErrorCode.USER_NOT_FOUND)
            }
        } ?: throw RuntimeException("No username found in SecurityContext")

        val student = userMapper.toUserDTO(user)

        // Lấy Student từ studentRepository và trích xuất ID
        val studentEntity = student.id?.let {
            studyRepository.findById(it).orElseThrow {
                AppException(ErrorCode.STUDENT_NOT_FOUND)
            }
        } ?: throw RuntimeException("Invalid student ID")

        val studentId = studentEntity.student.studentId

        val classIds = studyRepository.findClassIdsByStudentId(studentId)

        if (classIds.isEmpty()) {
            return Response(
                statusCode = 404,
                message = "No classes found for student ID: $studentId"
            )
        }
        // Tạo Pageable object
        val pageable: Pageable = PageRequest.of(page, size)

        // Lấy danh sách class với phân trang từ repository
        val classPage = classRepository.findClassesByIds(classIds, pageable)

        // Chuyển đổi các entity sang DTO
        val classDTOs = classPage.content.map { classEntity ->
            classMapper.toClassDTO(classEntity)
        }

        // Trả về kết quả phân trang
        return Response(
            statusCode = 200,
            message = "Classes fetched successfully",
            totalPages = classPage.totalPages,  // Lấy tổng số trang
            totalElements = classPage.totalElements,  // Lấy tổng số phần tử
            currentPage = page,
            listClassDTO = classDTOs
        )
    }

    override fun getAllLecturerClasses(pageable: Pageable): Response {
        val context = SecurityContextHolder.getContext()
        val username = context.authentication?.name

        val user = username?.let {
            userRepository.findByUsername(it).orElseThrow {
                AppException(ErrorCode.USER_NOT_FOUND)
            }
        }

        val classPage = user?.id?.let { classRepository.findClassByLecturersId(it, pageable) }

        if (classPage == null) { throw AppException(ErrorCode.CLASS_NOT_FOUND)}
        val listClassDTO = classPage.content.map { classEntity ->
            classMapper.toClassDTO(classEntity)
        }

        return Response(
            statusCode = 200,
            message = "Classes fetched successfully",
            totalPages = classPage.totalPages,
            totalElements = classPage.totalElements,
            currentPage = classPage.number,
            listClassDTO = listClassDTO
        )
    }

    override fun getHighestGradeStudent(classId: Long): Response {
        val myClass = classRepository.findById(classId).orElse(null)
        val studyList = studyRepository.findByStudyClass(myClass)
        val res : MutableList<StudentResponseDTO> = mutableListOf()
        val n = studyList.size
        for( i in 0 until n){
            var swapped = false
            for( j in 0 until n-i-1){
                if(studyList[j].score<studyList[j+1].score){
                    val s = studyList[j]
                    studyList[j]=studyList[j+1]
                    studyList[j+1]=s
                    swapped=true
                }
            }
            if (swapped==false) break
        }
        for (i in 0 until minOf(5, studyList.size)) {
            studyList[i].student?.let { studentMapper.toStudentResponseDto(it, studyList[i].score) }?.let { res.add(it) }
        }

        return Response (
            statusCode = 200,
            message = "Hall of fame fetch successfully",
            listStudentDTO = res
        )
    }
}