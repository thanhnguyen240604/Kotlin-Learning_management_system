package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.SubjectDTO
import com.be.kotlin.grade.service.interf.SubjectInterface
import hcmut.example.gradeportalbe.dto.Response
import hcmut.example.gradeportalbe.model.Subject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/subjects")
class SubjectController(
    private val subjectService: SubjectInterface
) {
    @PostMapping("/add")
    fun addSubject(@RequestBody subject: SubjectDTO): ResponseEntity<Response> {
        val response = subjectService.addSubject(subject)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}