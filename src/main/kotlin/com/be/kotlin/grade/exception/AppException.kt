package com.be.kotlin.grade.exception

class AppException(
    var errorCode: ErrorCode
) : RuntimeException(errorCode.message)
