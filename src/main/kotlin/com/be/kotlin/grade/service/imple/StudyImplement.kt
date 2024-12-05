package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.StudyDTO
import com.be.kotlin.grade.mapper.StudyMapper
import com.be.kotlin.grade.repository.StudyRepository
import com.be.kotlin.grade.service.interf.StudyInterface
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class StudyImplement(
    private val studyRepository: StudyRepository,
    private val studyMapper: StudyMapper
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
            return Response(
                statusCode = 300,
                message = "Study already exists"
            )
        }
        studyRepository.save(newStudy)

        return Response(
            statusCode = 200,
            message = "Study added successfully",
            studyDTO = studyMapper.toStudyDTO(newStudy))
    }


    override fun deleteStudyStudent(@RequestBody study: StudyDTO): Response {
        val studyId: Long = study.id ?: throw IllegalArgumentException("Study ID cannot be null")

        if (!studyRepository.findById(studyId).isPresent)
            return Response(
                statusCode = 404,
                message = "Study not found"
            )

        val deletedStudy = studyRepository.findById(studyId).get()

        // Xóa Study
        studyRepository.deleteById(studyId)
        return Response(
            studyDTO = studyMapper.toStudyDTO(deletedStudy),
            statusCode = 200,
            message = "Study deleted successfully"
        )
    }

    override fun updateStudyStudent(study: StudyDTO): Response {
        val studyId: Long = study.id ?: throw IllegalArgumentException("Study ID cannot be null")
        if (!studyRepository.findById(studyId).isPresent)
            return Response(
                statusCode = 404,
                message = "Study not found"
            )

        // Chuyển đổi từ DTO sang Entity và lưu vào cơ sở dữ liệu
        val updatedStudy = studyMapper.toStudy(study)
        studyRepository.save(updatedStudy)

        return Response(
            studyDTO = study,
            statusCode = 200,
            message = "Study updated successfully"
        )
    }
}