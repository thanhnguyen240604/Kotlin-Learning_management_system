package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.dto.Response
import org.springframework.web.bind.annotation.RequestBody

interface StudyInterface {
<<<<<<< HEAD
    fun addStudyStudent(@RequestBody studyDTO: StudyDTO): Response
    fun updateStudyStudent(@RequestBody study: StudyDTO): Response
    fun deleteStudyStudent(@RequestBody study: StudyDTO): Response
=======
    fun addStudyStudent(studyDTO: StudyDTO): Response
    fun deleteStudyStudent(studyIdD: Long): Response
    fun getStudyById(studyIdD: Long): Response
>>>>>>> main
}

