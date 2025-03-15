package com.be.kotlin.grade.exception

enum class ErrorCode(val code: Int, val message: String) {
    //AUTHENTICATED INVALID
    PASSWORD_INVALID(400, "Password invalid"),
    OTP_INVALID(400, "OTP invalid"),
    OTP_EXPIRED(400, "OTP has expired"),
    PASSWORD_NOT_MATCH(400, "Your confirm password does not match"),
    PASSWORD_NOT_CHANGE(400, "Your new password cannot be the same as the old one"),

    //STUDY INVALID
    STUDY_ID_INVALID(400,"StudyId cannot be null"),
    GRADE_ID_INVALID(400,"GradeId cannot be null"),
    SCORE_INVALID(400, "Score must be between 0 and 10"),
    WEIGHT_LIMIT_INVALID(400, "Weight exceeds limit"),
    GRADE_NOT_MATCH_INVALID(400, "Grade does not belong to the specified study"),

    //STUDENT INVALID
    STUDENT_ID_INVALID(400,"Student ID cannot be null"),

    //CLASS INVALID
    CLASS_ID_INVALID(400,"Class ID cannot be blank"),
    CLASS_ALREADY_HAS_LECTURERS(400, "Class already has enough lecturers"),
    CLASS_NOT_BELONG_TO_LECTURER(400, "You don't have permission to access this class"),
    LECTURER_FACULTY_MISMATCH(400, "Class faculty must match"),
    LECTURER_ALREADY_REGISTERED(400, "Class already has registered"),
    FACULTY_MISMATCH(400, "User faculty does not match"),
    MAJOR_MISMATCH(400, "Student major does not match"),
    FACULTY_MAJOR_MISMATCH (400, "Student major and faculty does not match"),
    SUBJECT_ID_INVALID(400,"Subject ID cannot be null"),

    //CLASS TIME INVALID
    START_END_TIME_CONFLICT(400, "Start time must be before end time"),
    CLASS_TIME_CONFLICT(400, "This class time conflict with another class"),
    CLASS_SEMESTER_ERROR(400, "Semester can not be greater than 3"),

    //Subject Relations INVALID
    POST_SUBJECT_INVALID(400, "Post-ordered subject can not be the same as subject id"),
    PRE_SUBJECT_INVALID(400, "Pre-ordered subject can not be the same as subject id"),

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
    SUBJECT_RELATION_NOT_FOUND(404,"Subject relation not found"),

    //Resource already existed
    USER_EXISTED(409, "User existed"),
    SUBJECT_EXISTED(409,"Subject already exists"),
    STUDY_EXISTED(409,"Study already exists"),
    STUDENT_ID_EXISTED(409,"Student ID already exists"),
    CLASS_EXISTED(409, "Class already exists"),
    CLASS_NAME_EXISTED(409, "Class name already exists"),
    SUBJECT_RELATION_EXISTS(409, "Subject relation already exists"),
}