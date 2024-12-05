package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.dto.classDTO.ClassIdDTO
import com.be.kotlin.grade.service.imple.ClassImplement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/classes")
class ClassController(private val classService: ClassImplement) {
    @PostMapping("/add")
    fun addClass(@RequestBody classDTO: ClassDTO): ResponseEntity<Response> {
        val response = classService.addClass(classDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PutMapping("/update")
    fun updateClass(@RequestBody classDTO: ClassDTO): ResponseEntity<Response> {
        val response = classService.updateClass(classDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @DeleteMapping("/delete")
    fun deleteClass(@RequestBody classIdDTO: ClassIdDTO): ResponseEntity<Response> {
        val response = classService.deleteClass(classIdDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

}