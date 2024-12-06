package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectIdDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import com.be.kotlin.grade.service.imple.SubjectImplement
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/subjects")
class SubjectController(private val subjectService: SubjectImplement) {
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping("/add")
    fun addSubject(@RequestBody subject: SubjectDTO): ResponseEntity<Response> {
        val response = subjectService.addSubject(subject)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PutMapping("/update")
    fun updateSubject(@RequestBody subject: SubjectDTO): ResponseEntity<Response> {
        val response = subjectService.updateSubject(subject)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @DeleteMapping("/delete")
    fun deleteSubject(@RequestBody subject: SubjectIdDTO): ResponseEntity<Response> {
        val response = subjectService.deleteSubject(subject)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    fun getSubjectById(@RequestBody subject: SubjectIdDTO): ResponseEntity<Response> {
        val response = subjectService.getSubjectById(subject)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    fun getAllSubjects(
        @RequestParam(defaultValue = "0") page: Int, // Giá trị mặc định là 0
        @RequestParam(defaultValue = "3") size: Int // Giá trị mặc định là 10
    ): ResponseEntity<Response> {
        val response = subjectService.getAllSubjects(page, size)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}