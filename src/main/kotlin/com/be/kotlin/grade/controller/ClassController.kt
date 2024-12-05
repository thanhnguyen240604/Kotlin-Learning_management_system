package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.StudentDTO.StudentResponseDto
import com.be.kotlin.grade.service.imple.ClassImplement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/classes")
class ClassController(private  val classImplement: ClassImplement) {
    @GetMapping("/get/hallOfFame")
    fun getHallOfFame(@RequestParam id : Long):MutableList<StudentResponseDto>{
        return classImplement.getHighestGradeStudent(id)
    }
}