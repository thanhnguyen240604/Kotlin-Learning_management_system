package com.be.kotlin.grade.exception

enum class ErrorCode(val code: Int, val message: String) {
    //Resource not match
    PASSWORD_INVALID(400, "Password invalid"),
    STUDY_ID_INVALID(400,"StudyId cannot be null"),
    GRADE_ID_INVALID(400,"GradeId cannot be null"),
    SCORE_INVALID(400, "Score must be between 0 and 10"),
    WEIGHT_LIMIT_INVALID(400, "Weight exceeds limit"),
    GRADE_NOT_MATCH_INVALID(400, "Grade does not belong to the specified study"),

    //Unauthenticated
    UNAUTHENTICATED(401, "Unauthenticated"),

    //Resource not found
    USER_NOT_FOUND(404, "User not found"),
    SUBJECT_NOT_FOUND(404,"Subject not found"),
    STUDY_NOT_FOUND(404,"Study not found"),
    GRADE_NOT_FOUND(404,"Grade not found"),

    //Resource already existed
    USER_EXISTED(409, "User existed"),
    SUBJECT_EXISTED(409,"Subject already exists"),
    STUDY_EXISTED(409,"Study already exists"),
}