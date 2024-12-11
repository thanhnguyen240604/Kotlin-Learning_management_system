package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.reportDTO.ReportOfSubjectRequestDTO
import org.springframework.data.domain.Pageable


interface StudyInterface {
    fun addStudyStudent(studyDTO: StudyDTO): Response
    fun deleteStudyStudent(studyIdD: Long): Response
    fun getStudyById(studyIdD: Long): Response
    fun updateStudyStudent(study: StudyDTO): Response
    fun generateSubjectReport(report: ReportOfSubjectRequestDTO): Response
    fun getStudyByUsernameAndSemester(username: String, semester: Int, pageable: Pageable): Response
    fun getStudyByUsernameAndSemesterCSV(username: String, semester: Int): Response
    fun getGradeBySubjectIdAndSemester(subjectId: String, semester: Int): Response
}

