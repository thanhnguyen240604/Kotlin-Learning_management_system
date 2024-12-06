package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.StudyMapper
import com.be.kotlin.grade.mapper.SubjectMapper
import com.be.kotlin.grade.repository.ClassRepository
import com.be.kotlin.grade.repository.StudentRepository
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.service.interf.StudyInterface
import org.springframework.stereotype.Service

@Service
class StudyImplement(
    private val studyRepository: StudyRepository,
    private val studyMapper: StudyMapper,
    private val subjectMapper: SubjectMapper,
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository,
    private val classRepository: ClassRepository
) : StudyInterface {
    override fun addStudyStudent(studyDTO: StudyDTO): Response {
        val newStudy = studyMapper.toStudy(studyDTO)

        val existingStudy = newStudy.student?.studentId?.let {
            newStudy.subject?.id?.let { it1 ->
                newStudy.studyClass?.id?.let { it2 ->
                    studyRepository.findByStudentStudentIdAndSubjectIdAndStudyClassId(
                        it,
                        it1,
                        it2
                    )
                }
            }
        }

        if (existingStudy != null) {
            throw AppException(ErrorCode.STUDY_EXISTED)
        }

        val studentId = newStudy.student?.studentId ?: throw AppException(ErrorCode.STUDENT_ID_INVALID)

        if (!studentRepository.findById(studentId).isPresent) {
            throw AppException(ErrorCode.STUDENT_NOT_FOUND)
        }

        val subjectId = newStudy.subject?.id ?: throw AppException(ErrorCode.SUBJECT_ID_INVALID)
        if (!subjectRepository.existsById(subjectId)) {
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)
        }

        val classId = newStudy.studyClass?.id ?: throw AppException(ErrorCode.CLASS_ID_INVALID)
        if (!classRepository.existsById(classId)) {
            throw AppException(ErrorCode.CLASS_NOT_FOUND)
        }

        val userFaculty = newStudy.student?.user?.faculty
        val subjectFaculty = newStudy.subject?.faculty
        val studentMajor = newStudy.student?.major
        val subjectMajor = newStudy.subject?.major

        if (userFaculty != subjectFaculty && studentMajor != subjectMajor) {
            throw AppException(ErrorCode.FACULTY_MAJOR_MISMATCH)
        }

        if (userFaculty != subjectFaculty) {
            throw AppException(ErrorCode.FACULTY_MISMATCH)
        }

        if (studentMajor != subjectMajor) {
            throw AppException(ErrorCode.MAJOR_MISMATCH)
        }

        studyRepository.save(newStudy)

        return Response(
            statusCode = 200,
            message = "Study added successfully",
            studyDTO = studyMapper.toStudyDTO(newStudy))
    }

    override fun deleteStudyStudent(studyIdD: Long): Response {
        if (!studyRepository.findById(studyIdD).isPresent)
            throw AppException(ErrorCode.STUDY_NOT_FOUND)

        val deletedStudy = studyRepository.findById(studyIdD).get()

        // Xóa Study
        studyRepository.deleteById(studyIdD)
        return Response(
            studyDTO = studyMapper.toStudyDTO(deletedStudy),
            statusCode = 200,
            message = "Study deleted successfully"
        )
    }

    override fun updateStudyStudent(study: StudyDTO): Response {

        val studyId: Long = study.id ?: throw AppException(ErrorCode.STUDY_ID_INVALID)

        if (!studyRepository.findById(studyId).isPresent){
            throw AppException(ErrorCode.STUDY_NOT_FOUND)
        }

        val studentId = study.studentId ?: throw AppException(ErrorCode.STUDENT_ID_INVALID)

        if (!studentRepository.findById(studentId).isPresent) {
            throw AppException(ErrorCode.STUDENT_NOT_FOUND)
        }

        val subjectId = study.subjectId ?: throw AppException(ErrorCode.SUBJECT_ID_INVALID)
        if (!subjectRepository.existsById(subjectId)) {
            throw AppException(ErrorCode.SUBJECT_NOT_FOUND)
        }

        val classId = study.classId ?: throw AppException(ErrorCode.CLASS_ID_INVALID)
        if (!classRepository.existsById(classId)) {
            throw AppException(ErrorCode.CLASS_NOT_FOUND)
        }

        // Chuyển đổi từ DTO sang Entity và lưu vào cơ sở dữ liệu
        val updatedStudy = studyMapper.toStudy(study)

        val userFaculty = updatedStudy.student?.user?.faculty
        val subjectFaculty = updatedStudy.subject?.faculty
        val studentMajor = updatedStudy.student?.major
        val subjectMajor = updatedStudy.subject?.major

        if (userFaculty != subjectFaculty && studentMajor != subjectMajor) {
            throw AppException(ErrorCode.FACULTY_MAJOR_MISMATCH)
        }

        if (userFaculty != subjectFaculty) {
            throw AppException(ErrorCode.FACULTY_MISMATCH)
        }

        if (studentMajor != subjectMajor) {
            throw AppException(ErrorCode.MAJOR_MISMATCH)
        }
        studyRepository.save(updatedStudy)

        return Response(
            studyDTO = study,
            statusCode = 200,
            message = "Study updated successfully"
        )
    }

    override fun getStudyById(studyIdD: Long): Response {
        val studyGot = studyRepository.findById(studyIdD)
            .orElseThrow { AppException(ErrorCode.STUDY_NOT_FOUND) }

        val studyDTO = studyMapper.toStudyDTO(studyGot)
        return Response(
            statusCode = 200,
            message = "Study found successfully",
            studyDTO = studyDTO
        )
    }
}