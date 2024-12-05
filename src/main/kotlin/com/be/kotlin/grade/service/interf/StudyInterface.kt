package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.StudyDTO
import com.be.kotlin.grade.dto.Response
import org.springframework.web.bind.annotation.RequestBody

interface StudyInterface {
    fun addStudyStudent(@RequestBody studyDTO: StudyDTO): Response
    fun updateStudyStudent(@RequestBody study: StudyDTO): Response
    fun deleteStudyStudent(@RequestBody study: StudyDTO): Response
}

