package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.RegisterDTO
import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studentDTO.StudentDTO
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.service.interf.StudentInterface
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/students")
class StudentController (
    private val studentService: StudentInterface
) {
    @PostMapping("/register")
    fun register(@RequestBody registerDTO: RegisterDTO): ResponseEntity<Response> {
        val response = studentService.register(registerDTO.userDTO, registerDTO.studentDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_LECTURER')")
    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = studentService.getStudentById(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}