package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.StudyDTO
import com.be.kotlin.grade.service.imple.StudyImplement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/study")
class StudyController (private val studyService: StudyImplement) {
    @PostMapping("/add")
    fun addStudyStudent(@RequestBody study: StudyDTO): ResponseEntity<Response> {
        val response = studyService.addStudyStudent(study)
        return ResponseEntity.status(response.statusCode).body(response)
    }
    @PostMapping("/update")
    fun updateStudyStudent(@RequestBody study: StudyDTO): ResponseEntity<Response> {
        val response = studyService.updateStudyStudent(study)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PostMapping("/delete")
    fun deleteStudyStudent(@RequestBody study: StudyDTO): ResponseEntity<Response> {
        val response = studyService.deleteStudyStudent(study)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}