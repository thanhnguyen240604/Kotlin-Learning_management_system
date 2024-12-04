package com.be.kotlin.grade.exception

enum class ErrorCode(val code: Int, val message: String) {
    USER_NOT_FOUND(1001, "User not found"),
    USER_EXISTED(1002, "User existed"),
    UNAUTHENTICATED(1003, "Unauthenticated"),
    PASSWORD_INVALID(1004, "Password invalid")
}