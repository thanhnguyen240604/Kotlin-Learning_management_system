package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.classDTO.ClassDTO
import com.be.kotlin.grade.dto.classDTO.UpdateClassDTO
import com.be.kotlin.grade.service.imple.ClassService
import com.be.kotlin.grade.service.interf.IClass
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/classes")
class ClassController(
    private val classService: IClass
) {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    fun addClass(@RequestBody classDTO: ClassDTO): ResponseEntity<Response> {
        val response = classService.addClass(classDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    fun updateClass(@RequestBody updateClassDTO: UpdateClassDTO): ResponseEntity<Response> {
        val response = classService.updateClass(updateClassDTO)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    fun deleteClass(@PathVariable id: Long): ResponseEntity<Response> {
        val response = classService.deleteClass(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    fun getClassById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = classService.getClassById(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all-admin")
    fun getAllClasses(
        @RequestParam(defaultValue = "0") page: Int, // Giá trị mặc định là 0
        @RequestParam(defaultValue = "3") size: Int // Giá trị mặc định là 10
    ): ResponseEntity<Response> {
        val response = classService.getAllClasses(page, size)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/all-student")
    fun getAllStudentClasses(
        @RequestParam(defaultValue = "0") page: Int, // Giá trị mặc định là 0
        @RequestParam(defaultValue = "3") size: Int, // Giá trị mặc định là 10
    ): ResponseEntity<Response> {
        val response = classService.getAllStudentClasses(page, size)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @GetMapping("/all-lecturer")
    fun getAllLecturerClasses(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "3") size: Int,
    ): ResponseEntity<Response> {
        val pageable: Pageable = PageRequest.of(page, size)
        val response = classService.getAllLecturerClasses(pageable)
        return ResponseEntity.status(response.statusCode).body(response)
    }

//    @PreAuthorize("hasRole('ROLE_LECTURER') or hasRole('ROLE_ADMIN')")
//    @GetMapping("/get/hallOfFame")
//    fun getHallOfFame(@RequestParam id : Long): ResponseEntity<Response>{
//        val response = classService.getHighestGradeStudent(id)
//        return ResponseEntity.status(response.statusCode).body(response)
//    }

    //Sẽ fix
//    @PreAuthorize("hasRole('ROLE_LECTURER')")
//    @PostMapping("/register/{classId}")
//    fun registerLecturerToClass(@PathVariable classId: Long): ResponseEntity<Response> {
//        val response = classService.registerLecturerToClass(classId)
//        return ResponseEntity.status(response.statusCode).body(response)
//    }
}
