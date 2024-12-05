package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.gradeDTO.Grade_DTO
import com.be.kotlin.grade.dto.gradeDTO.Grade_DTO_ID
import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.service.imple.GradeImplement
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/grades")
class GradeController(private val gradeService: GradeImplement) {
//  @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping("/add")
    fun addGrade(@RequestBody grade: Grade_DTO): ResponseEntity<Response> {
        val response = gradeService.addGrade(grade)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    //  @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PutMapping("/update")
    fun updateGrade(@RequestBody grade: Grade_DTO_ID): ResponseEntity<Response> {
        val response = gradeService.updateGrade(grade)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    //  @PreAuthorize("hasRole('ROLE_LECTURER')")
    @DeleteMapping("/delete")
    fun deleteGrade(@RequestBody grade: Grade_DTO_ID): ResponseEntity<Response> {
        val response = gradeService.deleteGrade(grade)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}