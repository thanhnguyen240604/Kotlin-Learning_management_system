package com.be.kotlin.grade.dto

import com.be.kotlin.grade.dto.gradeDTO.GradeDTO
import com.be.kotlin.grade.dto.securityDTO.AuthenticateDTO
import com.be.kotlin.grade.dto.securityDTO.IntrospectDTO
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import com.be.kotlin.grade.dto.userDTO.UserResponseDTO
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.dto.loginDTO.ForgotPasswordRequest
import com.be.kotlin.grade.dto.reportDTO.ReportOfSubjectResponseDTO
import com.be.kotlin.grade.dto.studentDTO.StudentDTO
import com.be.kotlin.grade.dto.studentDTO.StudentResponseDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectRegisterDTO
import org.springframework.core.io.FileSystemResource
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response(
    var statusCode: Int = 0,
    var message: String = "",
    val role: String? = null,
    val token: String? = null,
    val authenticated: Boolean? = null,

    // DTO response
    val authenticateDTO: AuthenticateDTO? = null,
    val introspectDTO: IntrospectDTO?= null,
    val userDTO: UserResponseDTO? = null,
    val subjectDTO: SubjectDTO? = null,
    val studyDTO: StudyDTO? = null,
    val gradeDTO: GradeDTO? = null,
    val classDTO: ClassDTO? = null,
    val studentDTO: StudentDTO? = null,
    val reportSubjectDTO: ReportOfSubjectResponseDTO? = null,
    val forgotPasswordDTO: ForgotPasswordRequest? = null,
    val subjectRegisterDTO: SubjectRegisterDTO? = null,

    // List DTO response
    val listUserDTO: List<UserResponseDTO>? = null,
    val listSubjectDTO: List<SubjectDTO>? = null,
    val listClassDTO: List<ClassDTO>? = null,
    val listStudyDTO: List<StudyDTO>? = null,
    val listGradeDTO: List<GradeDTO>? = null,
    val listStudent: List<StudentDTO>? = null,
    val listStudentDTO: MutableList<StudentResponseDTO>? = null,
    val lecturers: List<UserResponseDTO>? = null,
    val totalCredits : Int? = null,

    //Next semester
    val nextSemester: Int? = null,

    //GPA
    val gpa : Float? = null,

    // Pagination
    val totalPages: Int? = null,
    val totalElements: Long? = null,
    val currentPage: Int? = null,

    // File attachment
    @JsonIgnore
    val file : FileSystemResource? = null
)
