package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.userDTO.UserDTO
import com.be.kotlin.grade.service.interf.UserInterface
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserInterface
) {
    @PostMapping("/register")
    fun register(@RequestBody user: UserDTO): ResponseEntity<Response> {
        val response = userService.register(user)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}