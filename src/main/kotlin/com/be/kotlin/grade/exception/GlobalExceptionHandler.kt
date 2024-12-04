package com.be.kotlin.grade.exception

import com.be.kotlin.grade.dto.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun handleAppException(e: AppException): ResponseEntity<Response> {
        val errorCode = e.errorCode
        val response = Response().apply {
            statusCode = errorCode.code
            message = errorCode.message
        }

        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ResponseEntity<Response> {
        val enumKey = e.fieldError?.defaultMessage
        val error = ErrorCode.valueOf(enumKey ?: "UNKNOWN_ERROR")

        val response = Response().apply {
            statusCode = error.code
            message = error.message
        }

        return ResponseEntity.badRequest().body(response)
    }
}