package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.service.interf.StudyInterface
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/study")
class StudyController (
    private val studyService: StudyInterface
){
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping("/add")
    fun addStudyStudent(@RequestBody study: StudyDTO): ResponseEntity<Response> {
        val response = studyService.addStudyStudent(study)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping("/update")
    fun updateStudyStudent(@RequestBody study: StudyDTO): ResponseEntity<Response> {
        val response = studyService.updateStudyStudent(study)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @DeleteMapping("/delete/{id}")
    fun deleteStudyStudent(@PathVariable id: Long): ResponseEntity<Response> {
        val response = studyService.deleteStudyStudent(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    fun getStudyById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = studyService.getStudyById(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/result/{semester}")
    fun getStudiesByUsernameAndSemester(@PathVariable semester: Int) : ResponseEntity<Response> {
        val context = SecurityContextHolder.getContext()
        val username: String = context.authentication.name

        val response = studyService.getStudyByUsernameAndSemester(username, semester)

        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/result/get-csv/{semester}")
    fun getSemesterCSV(@PathVariable semester: Int) : ResponseEntity<FileSystemResource> {
        val context = SecurityContextHolder.getContext()
        val username: String = context.authentication.name

        val response = studyService.getStudyByUsernameAndSemester(username, semester)
        val resource = response.file

        val headers = HttpHeaders()
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${resource?.filename}")
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource)
    }
}

