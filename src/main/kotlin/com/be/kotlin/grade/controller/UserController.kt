package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.service.interf.UserInterface
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserInterface
) {
    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-lecturers")
    fun createLecturer(@RequestBody userRequestDTO: UserRequestDTO): ResponseEntity<Response> {
        val response = userService.createLecturer(userRequestDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = userService.findUserById(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getAllUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Response> {
        val pageable: Pageable = PageRequest.of(page, size)
        val response = userService.findAllUser(pageable)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}