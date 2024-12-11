package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.reportDTO.ReportOfSubjectRequestDTO
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.dto.Response

interface StudyInterface {
    fun addStudyStudent(studyDTO: StudyDTO): Response
    fun deleteStudyStudent(studyIdD: Long): Response
    fun getStudyById(studyIdD: Long): Response
    fun updateStudyStudent(study: StudyDTO): Response
    fun getStudyByUsernameAndSemester(username: String, semester: Int): Response
    fun generateSubjectReport(report: ReportOfSubjectRequestDTO): Response
}

