package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.StudyMapper
import com.be.kotlin.grade.mapper.SubjectMapper
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.service.interf.StudyInterface
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class StudyImplement(
    private val studyRepository: StudyRepository,
    private val studyMapper: StudyMapper,
    private val subjectMapper: SubjectMapper
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
        val studyId: Long = study.id ?: throw IllegalArgumentException("Study ID cannot be null")
        if (!studyRepository.findById(studyId).isPresent)
            throw AppException(ErrorCode.STUDY_NOT_FOUND)

        // Chuyển đổi từ DTO sang Entity và lưu vào cơ sở dữ liệu
        val updatedStudy = studyMapper.toStudy(study)
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