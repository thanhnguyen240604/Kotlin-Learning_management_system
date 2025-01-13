package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.userDTO.UserRequestDTO
import com.be.kotlin.grade.dto.userDTO.UserUpdateRequestDTO
import com.be.kotlin.grade.service.interf.IUser
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: IUser
) {
    @GetMapping("my-info")
    fun getMyInfo(): ResponseEntity<Response> {
        val response = userService.getMyInfo()
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-lecturers")
    fun createLecturer(@RequestBody userRequestDTO: UserRequestDTO): ResponseEntity<Response> {
        val response = userService.createLecturer(userRequestDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = userService.findUserById(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getAllUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Response> {
        val pageable: Pageable = PageRequest.of(page, size)
        val response = userService.findAllUser(pageable)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update")
    fun updateInfo(@RequestBody userDTO: UserUpdateRequestDTO): ResponseEntity<Response>{
        val response = userService.updateInfo(userDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    fun delAccount(@PathVariable id: Long): ResponseEntity<Response>{
        val response = userService.deleteUser(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}