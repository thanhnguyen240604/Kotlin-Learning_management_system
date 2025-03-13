package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.subjectDTO.SubjectRelationDTO
import com.be.kotlin.grade.model.Study
import com.be.kotlin.grade.service.imple.SubjectRelationService
import com.be.kotlin.grade.service.interf.ISubjectRelation
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subject-relations")
class SubjectRelationController (
    private val subjectRelationService: ISubjectRelation
){
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    fun addSubjectRelations(@RequestBody subjectRelationDTO: SubjectRelationDTO): ResponseEntity<Response> {
        val response = subjectRelationService.addSubjectRelation(subjectRelationDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    fun updateSubjectRelations(@RequestBody subjectRelationDTO: SubjectRelationDTO): ResponseEntity<Response> {
        val response = subjectRelationService.updateSubjectRelation(subjectRelationDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    fun deleteSubjectRelations(@RequestBody subjectRelationDTO: SubjectRelationDTO): ResponseEntity<Response> {
        val response = subjectRelationService.deleteSubjectRelation(subjectRelationDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}