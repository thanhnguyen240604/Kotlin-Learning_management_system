package com.be.kotlin.grade.controller

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.UserDto.UserUpdateInfoDto
import com.be.kotlin.grade.service.imple.UserImplement
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.nio.file.attribute.UserPrincipal

@RestController
@RequestMapping("/users")
class UserController(private val userImplement: UserImplement) {
    @PostMapping("/update/role")
    fun updateRole(@RequestParam role : String,@RequestParam username : String):Response{
        return userImplement.updateRole(role,username)
    }
    @PatchMapping("")
    fun updateInfo(@RequestBody userUpdateInfoDto: UserUpdateInfoDto,principal: UserPrincipal):Response{
        return userImplement.updateInfo(userUpdateInfoDto,principal.name)
    }
    @DeleteMapping("delete/account")
    fun delAccount(@RequestParam username: String):Response{
        return userImplement.delUser(username)
    }
}