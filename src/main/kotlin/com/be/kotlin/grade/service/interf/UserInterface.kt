package com.be.kotlin.grade.service.interf

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.UserDto.UserUpdateInfoDto
import com.be.kotlin.grade.dto.subjectDTO.FullSubjectDTO
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface UserInterface {
    fun delUser(username:String):Response;
    fun updateRole(@RequestParam role: String,username: String): Response
    fun updateInfo(@RequestBody userUpdateInfoDto: UserUpdateInfoDto, username: String):Response

}