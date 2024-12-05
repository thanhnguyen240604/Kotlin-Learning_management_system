package com.be.kotlin.grade.exception

class AppException(
    val errorCode: ErrorCode,
    message: String? = null
) : RuntimeException(message ?: errorCode.message)
