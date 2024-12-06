package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.studentDTO.StudentResponseDTO
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.service.imple.ClassImplement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/classes")
class ClassController(private val classService: ClassImplement) {
//    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping("/add")
    fun addClass(@RequestBody classDTO: ClassDTO): ResponseEntity<Response> {
        val response = classService.addClass(classDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    //    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PutMapping("/update")
    fun updateClass(@RequestBody classDTO: ClassDTO): ResponseEntity<Response> {
        val response = classService.updateClass(classDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    //    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @DeleteMapping("/delete/{id}")
    fun deleteClass(@PathVariable id: Long): ResponseEntity<Response> {
        val response = classService.deleteClass(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @GetMapping("/{id}")
    fun getClassById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = classService.getClassById(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @GetMapping("/all")
    fun getAllClasses(
        @RequestParam(defaultValue = "0") page: Int, // Giá trị mặc định là 0
        @RequestParam(defaultValue = "3") size: Int // Giá trị mặc định là 10
    ): ResponseEntity<Response> {
        val response = classService.getAllClasses(page, size)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @GetMapping("/all/{id}")
    fun getAllMyClasses(
        @RequestParam(defaultValue = "0") page: Int, // Giá trị mặc định là 0
        @RequestParam(defaultValue = "3") size: Int, // Giá trị mặc định là 10
        @PathVariable id: Long,
    ): ResponseEntity<Response> {
        val response = classService.getAllMyClasses(page, size, id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @GetMapping("/get/hallOfFame")
    fun getHallOfFame(@RequestParam id : Long): ResponseEntity<Response>{
        val response = classService.getHighestGradeStudent(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}
