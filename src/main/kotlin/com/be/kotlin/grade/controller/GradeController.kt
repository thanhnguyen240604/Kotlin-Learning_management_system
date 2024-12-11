package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.gradeDTO.GradeDTO
import com.be.kotlin.grade.dto.gradeDTO.GradeIdDTO
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
    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PostMapping("/add")
    fun addGrade(@RequestBody grade: GradeDTO): ResponseEntity<Response> {
        val response = gradeService.addGrade(grade)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @PutMapping("/update")
    fun updateGrade(@RequestBody grade: GradeIdDTO): ResponseEntity<Response> {
        val response = gradeService.updateGrade(grade)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @DeleteMapping("/delete")
    fun deleteGrade(@RequestBody grade: GradeIdDTO): ResponseEntity<Response> {
        val response = gradeService.deleteGrade(grade)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @PreAuthorize("hasRole('ROLE_LECTURER')")
    @GetMapping("/{id}")
    fun getGradeById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = gradeService.getGradeById(id)
        return ResponseEntity.status(response.statusCode).body(response)
    }

    @GetMapping
    fun getGradeBySubjectIdAndSemester(
        @RequestParam subjectId: String?,
        @RequestParam semester: Int?
    ): ResponseEntity<Response> {
        // Kiểm tra nếu thiếu giá trị
        if (subjectId.isNullOrBlank() || semester == null) {
            return ResponseEntity.status(400).body(
                Response(
                    statusCode = 400,
                    message = "subjectId and semester are required fields."
                )
            )
        }

        // Lúc này subjectId chắc chắn không null
        val subjectIdNonNull: String = subjectId
        val semesterNonNull: Int = semester

        // Gọi dịch vụ xử lý logic
        val response = gradeService.getGradeBySubjectIdAndSemester(subjectIdNonNull, semesterNonNull)
        return ResponseEntity.status(response.statusCode).body(response)
    }
}