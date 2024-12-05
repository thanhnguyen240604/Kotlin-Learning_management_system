package com.be.kotlin.grade.service.imple

import com.be.kotlin.grade.dto.Response
import com.be.kotlin.grade.dto.UserDto.UserUpdateInfoDto
import com.be.kotlin.grade.dto.subjectDTO.FullSubjectDTO
import com.be.kotlin.grade.repository.UserRepository
import com.be.kotlin.grade.service.interf.UserInterface

class UserImplement(private val userRepository: UserRepository) : UserInterface {
    override fun delUser(username : String):Response{
        if(userRepository.findByUsername(username).orElse(null)==null){
            return Response(
                statusCode = 300,
                message = "User doen't exist"
            )
        }
        userRepository.deleteByUsername(username);
        return Response(
            statusCode = 200,
            message = "Subject added successfully"
        )
    }
    override fun updateRole(role: String, username: String): Response {
        if(userRepository.findByUsername(username).orElse(null)==null){
            return Response(
                statusCode = 300,
                message = "User doen't exist"
            )
        }
        userRepository.updateUserRole(role,username)
        return Response(
            statusCode = 200,
            message = "Update role successfully"
        )
    }

    override fun updateInfo(userUpdateInfoDto: UserUpdateInfoDto,username: String): Response {
        userRepository.updateUserInfo(userUpdateInfoDto.name,userUpdateInfoDto.faculty,username)
        return Response(
            statusCode = 200,
            message = "Update info successfully"
        )
    }
}