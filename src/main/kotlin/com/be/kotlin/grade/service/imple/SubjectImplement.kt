package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectIdDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import com.be.kotlin.grade.exception.AppException
import com.be.kotlin.grade.exception.ErrorCode
import com.be.kotlin.grade.mapper.SubjectMapper
import com.be.kotlin.grade.repository.SubjectRepository
import com.be.kotlin.grade.service.interf.SubjectInterface
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class SubjectImplement(
    private val subjectRepository: SubjectRepository,
    private val subjectMapper: SubjectMapper,
): SubjectInterface {
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
}