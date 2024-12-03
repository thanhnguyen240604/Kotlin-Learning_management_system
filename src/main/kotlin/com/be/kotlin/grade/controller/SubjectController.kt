package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.SubjectDTO
import com.be.kotlin.grade.service.imple.SubjectImplement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/subjects")
class SubjectController(private val subjectService: SubjectImplement) {
    @PostMapping("/add")
    fun addSubject(@RequestBody subject: SubjectDTO): ResponseEntity<Response> {
        val response = subjectService.addSubject(subject)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PutMapping("/update")
    fun updateSubject(@RequestBody subject: SubjectDTO): ResponseEntity<Response> {
        val response = subjectService.updateSubject(subject)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @DeleteMapping("/delete")
    fun deleteSubject(@RequestBody subject: SubjectDTO): ResponseEntity<Response> {
        val response = subjectService.deleteSubject(subject)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}