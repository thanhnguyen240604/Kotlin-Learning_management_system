package com.be.kotlin.grade.exception

enum class ErrorCode(val code: Int, val message: String) {
    //Resource not match
    PASSWORD_INVALID(400, "Password invalid"),
    STUDY_ID_INVALID(400,"StudyId cannot be null"),
    GRADE_ID_INVALID(400,"GradeId cannot be null"),
    STUDENT_ID_INVALID(400,"Student ID cannot be null"),
    SUBJECT_ID_INVALID(400,"Subject ID cannot be null"),
    CLASS_ID_INVALID(400,"Class ID cannot be blank"),
    SCORE_INVALID(400, "Score must be between 0 and 10"),
    WEIGHT_LIMIT_INVALID(400, "Weight exceeds limit"),
    GRADE_NOT_MATCH_INVALID(400, "Grade does not belong to the specified study"),
    OTP_INVALID(400, "OTP invalid"),
    OTP_EXPIRED(400, "OTP has expired"),
    CLASS_ALREADY_HAS_LECTURERS(400, "Class already has enough lecturers"),
    LECTURER_FACULTY_MISMATCH(400, "Class faculty must match"),
    LECTURER_ALREADY_REGISTERED(400, "Class already has registered"),
    FACULTY_MISMATCH(400, "User faculty does not match"),
    MAJOR_MISMATCH(400, "Student major does not match"),
    FACULTY_MAJOR_MISMATCH (400, "Student major and faculty does not match"),
    PASSWORD_NOT_MATCH(400, "Your confirm password does not match"),
    PASSWORD_NOT_CHANGE(400, "Your new password cannot be the same as the old one"),
    CLASS_TIME_CONFLICT(400, "Class time conflict"),

    //Unauthenticated
    UNAUTHENTICATED_USERNAME_PASSWORD(401, "Please check email or password again"),
    UNAUTHENTICATED_USERNAME(401, "Please check email again"),
    UNAUTHENTICATED_USERNAME_DOMAIN(401, "Please enter @hcmut.edu.vn email "),

    //Resource not found
    USER_NOT_FOUND(404, "User not found"),
    SUBJECT_NOT_FOUND(404,"Subject not found"),
    STUDY_NOT_FOUND(404,"Study not found"),
    GRADE_NOT_FOUND(404,"Grade not found"),
    CLASS_NOT_FOUND(404,"Class not found"),
    STUDENT_NOT_FOUND(404,"Student not found"),

    //Resource already existed
    USER_EXISTED(409, "User existed"),
    SUBJECT_EXISTED(409,"Subject already exists"),
    STUDY_EXISTED(409,"Study already exists"),
    STUDENT_ID_EXISTED(409,"Student ID already exists"),
    CLASS_EXISTED(409, "Class already exists"),
}