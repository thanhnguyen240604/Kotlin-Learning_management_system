package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.dto.classDTO.UpdateClassDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.ClassMapper
import com.be.kotlin.grade.mapper.UserMapper
import com.be.kotlin.grade.model.Class
import com.be.kotlin.grade.model.enums.CustomDayOfWeek
import com.be.kotlin.grade.repository.*
import com.be.kotlin.grade.service.interf.IClass
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime

@Service
class ClassService(
    private val classRepository: ClassRepository,
    private val userRepository: UserRepository,
    private val classMapper: ClassMapper,
    private val studyRepository: StudyRepository,
    private val userMapper: UserMapper
) : IClass {

    override fun addClass(classDTO: ClassDTO): Response {

        if (classRepository.findBySubjectAndNameAndSemester(classDTO.subjectId, classDTO.name, classDTO.semester) != null)
            throw AppException(ErrorCode.CLASS_EXISTED)

        if (classDTO.startTime > classDTO.startTime)
            throw AppException(ErrorCode.START_END_TIME_CONFLICT)

        if (classDTO.semester % 10 > 3) {
            throw AppException(ErrorCode.CLASS_SEMESTER_ERROR)
        }

        val existingClasses = classRepository.findAllBySubjectAndSemester(classDTO.subjectId, classDTO.semester)

        val newClass = classMapper.toClass(classDTO)
        if (newClass != null) {
            // Kiểm tra trùng giờ học
            existingClasses.forEach { existingClass ->
                newClass.id?.let {
                    checkTimeOverlap(
                        existingClass,
                        newClass.startTime,
                        newClass.endTime,
                        newClass.dayOfWeek,
                        it
                    )
                }
            }
            classRepository.save(newClass)
        }

        val dataclass = classRepository.findBySubjectAndNameAndSemester(classDTO.subjectId, classDTO.name, classDTO.semester)
        return Response(
            classDTO = dataclass?.let { classMapper.toClassDTO(it) },
            statusCode = 200,
            message = "Class added successfully"
        )
    }

    @Transactional
    override fun updateClass(updateClassDTO: UpdateClassDTO): Response {
        val baseClass = updateClassDTO.id?.let { classRepository.findClassById(it) }
            ?: throw AppException(ErrorCode.CLASS_NOT_FOUND)

        if (updateClassDTO.startTime != null && updateClassDTO.endTime != null && updateClassDTO.dayOfWeek != null) {
            val existingClasses = classRepository.findAllBySubjectAndSemester(baseClass.subject.id, baseClass.semester)
            existingClasses.forEach { existingClass ->
                baseClass.id?.let {
                    checkTimeOverlap(
                        existingClass,
                        updateClassDTO.startTime,
                        updateClassDTO.endTime,
                        updateClassDTO.dayOfWeek,
                        it
                    )
                }

            }
        }
        updateClassDTO.name?.let { baseClass.name = it }
        updateClassDTO.startTime?.let { baseClass.startTime = it }
        updateClassDTO.endTime?.let { baseClass.endTime = it }
        updateClassDTO.dayOfWeek?.let { baseClass.dayOfWeek = it }
        updateClassDTO.lecturersUsernameList?.let {baseClass.lecturersUsername = it }

        classRepository.save(baseClass)
        return Response(
            statusCode = 200,
            message = "Class updated successfully",
//            updateClassDTO = updateClassDTO
        )
    }

    override fun deleteClass(id: Long): Response {
        classRepository.deleteById(id)

        return Response(
            statusCode = 200,
            message = "Class deleted successfully"
        )
    }

    override fun getClassById(id: Long): Response {
        val classGot = classRepository.findById(id)
            .orElseThrow { AppException(ErrorCode.CLASS_NOT_FOUND) }
        val context = SecurityContextHolder.getContext()
        val username = context.authentication?.name

        val user = username?.let {
            userRepository.findByUsername(it).orElseThrow {
                AppException(ErrorCode.USER_NOT_FOUND)

            }
        }
        if (user != null) {
            if (user.role == "LECTURER") {
                if(!classGot.lecturersUsername.contains(username))
                    throw AppException(ErrorCode.CLASS_NOT_BELONG_TO_LECTURER)
            }
        }

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

        val classPage = user?.username?.let { classRepository.findClassByLecturersUsername(it, pageable) }

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

    fun checkTimeOverlap(
        existingClass: Class,
        newStartTime: LocalTime,
        newEndTime: LocalTime,
        newDayOfWeek: List<CustomDayOfWeek>,
        baseClassId: Long
    ) {
        if (existingClass.id == baseClassId) {
            return
        }

        newDayOfWeek.forEach { newDay ->
            if (newDay in existingClass.dayOfWeek) {
                val existingStartDateTime = newStartTime.with(existingClass.startTime)
                val existingEndDateTime = newStartTime.with(existingClass.endTime)

                val isOverlapping = (newStartTime < existingEndDateTime && newEndTime > existingStartDateTime)
                if (isOverlapping) {
                    throw AppException(ErrorCode.CLASS_TIME_CONFLICT)
                }
            }
        }
    }

//    override fun getHighestGradeStudent(classId: Long): Response {
//        val myClass = classRepository.findById(classId).orElse(null)
//        val studyList = studyRepository.findByStudyClass(myClass)
//        val res : MutableList<StudentResponseDTO> = mutableListOf()
//        val n = studyList.size
//        for( i in 0 until n){
//            var swapped = false
//            for( j in 0 until n-i-1){
//                if(studyList[j].score<studyList[j+1].score){
//                    val s = studyList[j]
//                    studyList[j]=studyList[j+1]
//                    studyList[j+1]=s
//                    swapped=true
//                }
//            }
//            if (swapped==false) break
//        }
//        for (i in 0 until minOf(5, studyList.size)) {
//            studyList[i].student?.let { studentMapper.toStudentResponseDto(it, studyList[i].score) }?.let { res.add(it) }
//        }
//
//        return Response (
//            statusCode = 200,
//            message = "Hall of fame fetch successfully",
//            listStudentDTO = res
//        )
//    }

//    override  fun registerLecturerToClass(classId: Long): Response {
//        val username = SecurityContextHolder.getContext().authentication?.name
//
//        val lecturer = username?.let {
//            userRepository.findLecturersByUsername(it)
//        } ?: throw AppException(ErrorCode.USER_NOT_FOUND)
//
//        val classLec = classRepository.findById(classId)
//            .orElseThrow { AppException(ErrorCode.CLASS_NOT_FOUND) }
//
//        if (classLec.lecturers.size >= 2) {
//            throw AppException(ErrorCode.CLASS_ALREADY_HAS_LECTURERS)
//        }
//
//        if (lecturer.faculty != classLec.subject.faculty) {
//            throw AppException(ErrorCode.LECTURER_FACULTY_MISMATCH)
//        }
//        if (classLec.lecturers.any { it.id == lecturer.id }) {
//            throw AppException(ErrorCode.LECTURER_ALREADY_REGISTERED)
//        }
//
//        classLec.lecturers.add(lecturer)
//        classRepository.save(classLec)
//
//        val classDTO = classMapper.toClassDTO(classLec)
//        val lecturersDTO = classLec.lecturers.map { userMapper.toUserDTO(it) }
//        return Response (
//            statusCode = 200,
//            message = "Lecturer registered successfully",
//            classDTO = classDTO,
//            lecturers = lecturersDTO
//        )
//    }
}