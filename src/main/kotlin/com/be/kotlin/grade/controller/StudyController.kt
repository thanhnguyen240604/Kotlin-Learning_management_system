package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studyDTO.StudyDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectDTO
import com.be.kotlin.grade.dto.subjectDTO.SubjectIdDTO
import com.be.kotlin.grade.service.interf.StudyInterface
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/study")
class StudyController (
    private val studyService: StudyInterface
){
    //    @PreAuthorize("hasRole('ROLE_LECTURER')")
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


    //    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @DeleteMapping("/delete/{id}")
    fun deleteStudyStudent(@PathVariable id: Long): ResponseEntity<Response> {
        val response = studyService.deleteStudyStudent(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    //    @PreAuthorize("hasRole('ROLE_LECTURER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    fun getStudyById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = studyService.getStudyById(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}