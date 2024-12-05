package com.be.kotlin.grade.exception

enum class ErrorCode(val code: Int, val message: String) {
    //Resource not match
    PASSWORD_INVALID(400, "Password invalid"),

    //Unauthenticated
    UNAUTHENTICATED(401, "Unauthenticated"),

    //Resource not found
    USER_NOT_FOUND(404, "User not found"),
    SUBJECT_NOT_FOUND(404,"Subject not found"),
    STUDY_NOT_FOUND(404,"Study not found"),

    //Resource already existed
    USER_EXISTED(409, "User existed"),
    SUBJECT_EXISTED(409,"Subject already exists"),
    STUDY_EXISTED(409,"Study already exists"),
}