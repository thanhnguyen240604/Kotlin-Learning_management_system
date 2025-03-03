## API Specification

### Authentication APIs

#### 1. Login
- **URL**: `POST /grade-portal/auth/login`
- **Description**: Đăng nhập vào hệ thống
- **Request Body**:
  ```json
  {
      "username": "admin",
      "password": "admin"
  }
  ```
- **Response**:
  - `200 OK`: Returns an authentication token.
    ```json
    {
    "statusCode": 200,
    "message": "Login successfully",
    "role": null,
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNeUFwcCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzM0MzY2NzA5LCJpYXQiOjE3MzQzNTk1MDksImp0aSI6IjYwMjVhNzJmLTdmYWQtNGYxYS04Y2FjLTllNjExOTkzMWM0NCIsInNjb3BlIjoiUk9MRV9BRE1JTiJ9.RhGhtazMVhvoFi952G_VnYB-hCd3nbqkEGE7u9wcNK8",
    "authenticated": true
    }
    ```
  - `401 Unauthorized`
    ```json
      {
        "statusCode": 401,
        "message": "Unauthenticated"     
      }
    ```
  - `404 User not found`
    ```json
      {
        "statusCode": 404,
        "message": "User not found"     
      }
    ```

#### 2. Forgot Password
- **URL**: `POST /grade-portal/auth/forgot-password`
- **Description**: Khi người dùng quên mật khẩu, một otp sẽ được gửi đến email của người dùng. Người dùng nhập otp để tiến hành reset password.
- **Request Body**:
  ```json
  {
      "email": "thanh.nguyen2213132@hcmut.edu.vn"
  }
  ```
- **Response**:
  - `200 OK`: Email with OTP sent.
    ```json
      {
        "statusCode": 200,
        "message": "Password reset email sent successfully"     
      }
    ```
  - `400 Bad Request`: Email not found.
      ```json
        {
          "statusCode": 401,
          "message": "Unauthenticated"     
        }
      ```
  - `404 User not found`
      ```json
        {
          "statusCode": 404,
          "message": "User not found"     
        }
      ```

#### 3. Verify OTP
- **URL**: `POST /grade-portal/auth/verify-otp`
- **Description**: Kiểm tra otp đã gửi cho người dùng có đúng không.
- **Request Body**:
  ```json
  {
      "email": "thanh.nguyen2213132@hcmut.edu.vn",
      "otp": "123456"
  }
  ```
- **Response**:
  - `200 OK`: OTP verified.
      ```json
        {
           "statusCode": 200,
           "message": "OTP verified successfully"
        }
      ```
  - `400 Bad Request`: Invalid OTP.
    ```json
      {
         "statusCode": 400,
         "message": "OTP invalid"
      }
    ```
  - `400 Bad Request`: Expired OTP.
    ```json
      {
         "statusCode": 400,
         "message": "OTP has expired"
      }
    ```
  - `404 User not found`
    ```json
      {
        "statusCode": 404,
        "message": "User not found"     
      }
    ```

#### 4. Reset Password
- **URL**: `POST /grade-portal/auth/reset-password`
- **Description**: Tạo password mới khi xác nhận otp thành công.
- **Request Body**:
  ```json
  {
      "email": "thanh.nguyen2213132@hcmut.edu.vn",
      "newPassword": "new-password"
  }
  ```
- **Response**:
    - `200 OK`: Password updated.
        ```json
          {
             "statusCode": 200,
             "message": "Password reset successfully"
          }
        ```
    - `400 Bad Request`: Reset failed.
        ```json
          {
             "statusCode": 400,
             "message": "OTP invalid"
          }
        ```
    - `404 User not found`
      ```json
        {
          "statusCode": 404,
          "message": "User not found"     
        }
      ```

### Class Management APIs

#### 1. Add Class
- **URL**: `POST /grade-portal/classes/add`
- **Authorization**: `ADMIN`
- **Description**: Thêm một lớp mới.
- **Request Body**:
  ```json
  {
      "name": "Class Name",
      "subjectId": "SubjectID",
      "semester": 242,
      "startTime": "09:00",
      "endTime": "10:50",
      "dayOfWeek": ["MONDAY"]
  }
  ```
- **Response**:
    - `200 Created`: Class added successfully.
    ```json
      {
        "statusCode": 200,
        "message": "Class added successfully",    
        "classDTO": {
            "id": 1,
            "name": "Class Name",
            "subjectId": "SubjectID",
            "semester": 242,
            "startTime": "09:00",
            "endTime": "10:50",
            "daysOfWeek": ["MONDAY"]
        } 
      }
    ```
    - `404 Subject not found`
    ```json
      {
        "statusCode": 404,
        "message": "Subject not found"     
      }
    ```
    - `409 Class already exists`
      ```json
        {
          "statusCode": 409,
          "message": "Class already exists"     
        }
      ```

#### 2. Update Class
- **URL**: `PUT /grade-portal/classes/update`
- **Authorization**: `ADMIN`
- **Description**: Điều chỉnh name hoặc mã môn học của lớp.
- **Request Body**:
  ```json
  {
      "id": 1,
      "name": "Updated Class Name",
      "subjectId": "UpdatedSubjectID",
      "semester": 242,
      "startTime": "07:00",
      "endTime": "08:50",
      "dayOfWeek": ["MONDAY"],
      "lecturersUsernameList": ["lecturer1", "lecturer2"]
  }
  ```
- **Response**:
    - `200 OK`: Class added successfully.
    ```json
      {
        "statusCode": 200,
        "message": "Class updated successfully",    
        "classDTO": {
            "id": 1,
            "name": "Updated Class Name",
            "subjectId": "UpdatedSubjectID",
            "semester": 242,
            "startTime": "07:00",
            "endTime": "08:50",
            "dayOfWeek": ["MONDAY"],
            "lecturersUsernameList": ["lecturer1", "lecturer2"]
        } 
      }
    ```
    - `404 Subject not found`: Không tìm thấy môn học
    ```json
      {
        "statusCode": 404,
        "message": "Subject not found"     
      }
    ```
    - `404 Class not found`
      ```json
        {
          "statusCode": 404,
          "message": "Class not found"     
        }
      ```
    - `400 Class Invalid`: Lớp không thuộc quyền của giảng viên này
      ```json
        {
          "statusCode": 400,
          "message": "Class not belong to the specified lecturer"     
        }
      ```

#### 3. Delete Class
- **URL**: `GET /grade-portal/classes/delete/{id}`
- **Authorization**: `ADMIN`
- **Description**: Xóa lớp học
- **Request Parameters**: `id` (path): ID của lớp học cần xóa.
- **Response**
  - `200 OK`: Class added successfully.
      ```json
        {
          "statusCode": 200,
          "message": "Class deleted successfully",    
          "classDTO": {
              "id": 1,
              "name": "Class Name",
              "subjectId": "SubjectID"
          } 
        }
      ```
  - `404 Class not found`
    ```json
      {
        "statusCode": 404,
        "message": "Class not found"     
      }
    ```
  - `400 Class Invalid`: Lớp không thuộc quyền của giảng viên này
    ```json
      {
        "statusCode": 400,
        "message": "Class not belong to the specified lecturer"     
      }
    ```

#### 4. Get Class By ID
- **URL**: `GET /grade-portal/classes/{id}`
- **Authorization**: `LECTURER` hoặc `ADMIN`
- **Description**: Tìm lớp học bằng id
- **Request Parameters**: `id` (path): ID của lớp học cần tìm.
- **Response**
    - `200 OK`: Class found successfully.
        ```json
          {
            "statusCode": 200,
            "message": "Class found successfully",    
            "classDTO": {
                "id": 1,
                "name": "Class Name",
                "subjectId": "SubjectID"
            } 
          }
        ```
    - `404 Class not found`
      ```json
        {
          "statusCode": 404,
          "message": "Class not found"     
        }
      ```


#### 5. Get All Classes By Admin
- **URL**: `GET /grade-portal/classes/all-admin`
- **Authorization**: `ADMIN`
- **Description**: Xem danh sách các lớp đang có với pagination
- **Request Parameters**:
    - `page` (integer): Page number (default: 0).
    - `size` (integer): Page size (default: 3).
- **Response**
  - `200 OK`: Class fetched successfully.
      ```json
        {
          "statusCode": 200,
          "message": "Class fetched successfully",
          "totalPages": "tổng số trang", 
          "totalElements": "tổng số lớp",  
          "currentPage": "trang hiện tại",
          "listClassDTO": [
            {
                "id": 1,
                "name": "Class Name",
                "subjectId": "SubjectID"
            },
            {
                "id": 2,
                "name": "Class Name",
                "subjectId": "SubjectID"
            }
          ]
        }
      ```

#### 6. Get All Student Classes
- **URL**: `GET /grade-portal/classes/all-student`
- **Authorization**: `STUDENT`
- **Description**: Xem danh sách các lớp đang mà student đang học với pagination
- **Request Parameters**:
    - `page` (integer): Page number (default: 0).
    - `size` (integer): Page size (default: 3).
- **Response**
  - `200 OK`: Class fetched successfully.
      ```json
        {
          "statusCode": 200,
          "message": "Class fetched successfully",
          "totalPages": "tổng số trang", 
          "totalElements": "tổng số lớp",  
          "currentPage": "trang hiện tại",
          "listClassDTO": [
            {
                "id": 1,
                "name": "Class Name",
                "subjectId": "SubjectID"
            },
            {
                "id": 2,
                "name": "Class Name",
                "subjectId": "SubjectID"
            }
          ]
        }
      ```
  - `404 User not found`
    ```json
      {
        "statusCode": 404,
        "message": "User not found"     
      }
    ```
  - `404 Student not found`
    ```json
    {
      "statusCode": 404,
      "message": "Student not found"     
    }
    ```
  - Response khi không tìm thấy username trong SecurityContext
    ```json
    {
        "statusCode": 500,
        "message": "No username found in SecurityContext"
    }
    ```
  - Response khi không tìm thấy class nào cho student
    ```json
    {
        "statusCode": 404,
        "message": "No classes found for student ID: {studentId}"
    }
    ```


#### 7. Get All Lecturer Classes
- **URL**: `GET /grade-portal/classes/all-lecturer`
- **Authorization**: `LECTURER`
- **Description**: Xem danh sách các lớp đang mà lecturer đang dạy với pagination
- **Request Parameters**: 
  - `page` (integer): Page number (default: 0).
  - `size` (integer): Page size (default: 3).
- **Response**
    - `200 OK`: Class fetched successfully.
        ```json
          {
            "statusCode": 200,
            "message": "Class fetched successfully",
            "totalPages": "tổng số trang", 
            "totalElements": "tổng số lớp",  
            "currentPage": "trang hiện tại",
            "listClassDTO": [
              {
                  "id": 1,
                  "name": "Class Name",
                  "subjectId": "SubjectID"
              },
              {
                  "id": 2,
                  "name": "Class Name",
                  "subjectId": "SubjectID"
              }
            ]
          }
        ```
    - `404 User not found`
      ```json
        {
          "statusCode": 404,
          "message": "User not found"     
        }
      ```
    - Response khi không tìm thấy class của giảng viên trong `classRepository`
      ```json
        {
            "statusCode": 404,
            "message": "Class not found"
        }
      ```

#### 8. Get Highest Grade Students
- **URL**: `GET /grade-portal/classes/get/hallOfFame`
- **Authorization**: `LECTURER` hoặc `ADMIN`
- **Description**: Tìm 5 student có điểm cao nhất trong lớp có Id được truyền vào.
- **Request Parameters**: `id` (path): ID of the class.
- **Response**
    - Response khi không tìm thấy `classId` trong `classRepository`
        ```json
        {
            "statusCode": 404,
            "message": "Class not found for ID: <classId>"
        }
        ```
    - Response khi danh sách `studyList` rỗng
      ```json
        {
            "statusCode": 404,
            "message": "No students found for class ID: <classId>"
        }
      ```
    - Response thành công khi lấy danh sách sinh viên điểm cao nhất
        ```json
        {
            "statusCode": 200,
            "message": "Hall of fame fetched successfully",
            "listStudentDTO": [
                {
                    "name": "Tên",        
                    "score": "Điểm"     
                },
                {
                    "name": "Tên",        
                    "score": "Điểm"     
                }
            ]
        }
        ```
      - Response lỗi chung (Runtime Exception hoặc logic bị sai)
      ```json
      {
          "statusCode": 500,
          "message": "Internal server error"
      }
      ```

#### 9. Register Lecturer to Class
- **URL**: `POST /grade-portal/classes//register/{classId}`
- **Description**: Đăng ký lecturer cho lớp nếu hợp lệ.
- **Request Parameters**: `classId` (path): ID of the class.
- **Response**
    - Response khi không tìm thấy `username` trong SecurityContext
    ```json
    {
        "statusCode": 404,
        "message": "User not found"
    }
    ```
    - Response khi không tìm thấy lớp học (`classId`)
    ```json
    {
        "statusCode": 404,
        "message": "Class not found for ID: <classId>"
    }
    ```
    - Response khi lớp học đã đủ số lượng giảng viên (giới hạn là 2)
    ```json
    {
        "statusCode": 400,
        "message": "Class already has maximum number of lecturers"
    }
    ```
    - Response khi giảng viên không thuộc cùng khoa với môn học của lớp
    ```json
    {
        "statusCode": 400,
        "message": "Lecturer's faculty does not match class's subject faculty"
    }
    ```
    - Response khi giảng viên đã đăng ký vào lớp học
    ```json
    {
        "statusCode": 400,
        "message": "Lecturer is already registered for this class"
    }
    ```
    - Response thành công khi giảng viên đăng ký vào lớp
    ```json
    {
        "statusCode": 200,
        "message": "Lecturer registered successfully",
        "classDTO": {
          "id": 1,
          "name": "Class Name",
          "subjectId": "SubjectID"
        },
        "lecturers": [
          {
            "id": "ID của lecturer",
            "name": "Tên lecturer",
            "faculty": "Tên khoa",
            "role": "role",
            "username": "username"
          },
          {
            "id": "ID của lecturer",
            "name": "Tên lecturer",
            "faculty": "Tên khoa",
            "role": "role",
            "username": "username"
          }
        ]
    }
    ```
    - Response lỗi chung (Runtime Exception hoặc logic bị sai)
    ```json
    {
    "statusCode": 500,
    "message": "Internal server error"
    }
    ```

### Grade APIs

#### 1. Add Grade
- **URL**: `POST /grade-portal/grades/add`
- **Description**: Thêm một điểm mới vào.
- **Authorization**: `LECTURER`
- **Request Body**:
  ```json
  {
      "score": 10,
      "weight": 10,
      "studyId": 1
  }
  ```
- **Responses:**

| Status Code | Description                            | Example Response                                                                    |
|-------------|----------------------------------------|-------------------------------------------------------------------------------------|
| 200         | Grade được thêm thành công             | `{ "statusCode": 200, "message": "Grade added successfully", "gradeDTO": { ... } }` |
| 400         | Trọng số vượt giới hạn                 | `{ "statusCode": 400, "message": "Total weight exceeds 100. Remaining weight: 15" }` |
| 400         | Điểm số không hợp lệ                   | `{ "statusCode": 400, "message": "Score is invalid" }`                              |
| 404         | Study không tồn tại                    | `{ "statusCode": 404, "message": "Study not found" }`                               |


#### 2. Update Grade
- **URL**: `PUT /grade-portal/grades/update`
- **Description**: Cập nhật thông tin một điểm.
- **Authorization**: `LECTURER`
- **Request Body**:
  ```json
  {
      "score": 10,
      "weight": 1,
      "studyId": 1,
      "id": 20
  }
  ```
**Responses:**

| Status Code | Description                            | Example Response                                         |
|-------------|----------------------------------------|---------------------------------------------------------|
| 200         | Grade được cập nhật thành công         | `{ "statusCode": 200, "message": "Grade updated successfully", "gradeDTO": { ... } }` |
| 400         | Trọng số vượt giới hạn                 | `{ "statusCode": 400, "message": "Total weight exceeds 100. Remaining weight: 10" }` |
| 400         | Điểm số không hợp lệ                   | `{ "statusCode": 400, "message": "Score is invalid" }`  |
| 404         | Grade không tồn tại                    | `{ "statusCode": 404, "message": "Grade not found" }`   |
| 400         | Grade không thuộc Study được chỉ định  | `{ "statusCode": 400, "message": "Grade does not match the provided study ID" }` |


#### 3. Delete Grade
- **URL**: `DELETE grade-portal/grades/delete`
- **Description:** Xóa một điểm khỏi hệ thống.
- **Authorization:** `LECTURER`
- **Request Body**:
  ```json
  {
      "studyId": 1,
      "id": 20
  }
  ```
- **Responses:**
- 
| Status Code | Description                            | Example Response                                         |
|-------------|----------------------------------------|---------------------------------------------------------|
| 200         | Grade được xóa thành công              | `{ "statusCode": 200, "message": "Grade deleted successfully", "gradeDTO": { ... } }` |
| 404         | Grade không tồn tại                    | `{ "statusCode": 404, "message": "Grade not found" }`   |
| 400         | Grade không thuộc Study được chỉ định  | `{ "statusCode": 400, "message": "Grade does not match the provided study ID" }` |

#### 4. Get Grade By ID
- **URL:** `GET /grade-portal/grades/{id}`
- **Description:** Lấy thông tin chi tiết của một điểm dựa trên ID.
- **Authorization:** `LECTURER`
- **Path Parameter:** id ID của Grade cần lấy
- **Responses:**

| Status Code | Description                   | Example Response                                         |
|-------------|-------------------------------|---------------------------------------------------------|
| 200         | Thông tin Grade được tìm thấy | `{ "statusCode": 200, "message": "Grade found successfully", "gradeDTO": { ... } }` |
| 404         | Grade không tồn tại           | `{ "statusCode": 404, "message": "Grade not found" }`   |

### Student APIs

#### 1. Register Student
- **URL:** `POST /grade-portal/students/register`
- **Description**: Cho phép sinh viên đăng ký tài khoản mới trong hệ thống.

**Request Body**:
```json
{
  "userDTO": {
    "name": "CT",
    "faculty": "Engineering",
    "username": "thanh.nguyen2213132@hcmut.edu.vn",
    "password": "password123"
  },
  "studentDTO": {
    "studentId": 1234567,
    "enrolledCourse": 22,
    "major": "Computer Science"
  }
}
```

**Response**:

- **200 OK**: Sinh viên đã được đăng ký thành công.
```json
{
  "statusCode": 200,
  "message": "Student registered successfully"
}
```
- **409 Conflict**: Tài khoản người dùng đã tồn tại.
```json
{
  "statusCode": 409,
  "message": "User already exists"
}
```


#### 2. Get Student by ID 
- **URL**: `GET /grade-portal/students/{id}`
- **Description**: Cho phép Admin lấy thông tin chi tiết của một sinh viên dựa trên ID.
- **Authorization**: `ADMIN`

**Path Variable**:
- **id**: ID của sinh viên (kiểu Long).

**Response**:
- **200 OK**: Trả về thông tin sinh viên nếu tìm thấy.
```json
{
  "statusCode": 200,
  "message": "Student found successfully",
  "student": {
    "studentId": 1234567,
    "name": "CT",
    "faculty": "Engineering",
    "major": "Computer Science",
    "enrolledCourse": 22,
    "username": "thanh.nguyen2213132@hcmut.edu.vn"
  }
}
```
- **404 Not Found**: Nếu không tìm thấy sinh viên với ID cung cấp.
```json
{
  "statusCode": 404,
  "message": "Student not found"
}
```


#### 3. Update Student 
- **URL**: `PUT /grade-portal/students/update`
- **Description**: Cho phép sinh viên cập nhật thông tin cá nhân.
- **Authorization**: `STUDENT`

**Request Body**:
```json
{
  "studentName": "TanNguyen",
  "faculty": "KTMT",
  "studentId": 2
}
```

**Response**:
- **200 OK**: Sinh viên đã cập nhật thông tin thành công.
```json
{
  "statusCode": 200,
  "message": "Student updated successfully"
}
```
- **404 Not Found**: Sinh viên không tồn tại.
```json
{
  "statusCode": 404,
  "message": "Student not found"
}
```
- **409 Conflict**: ID sinh viên đã tồn tại.
```json
{
  "statusCode": 409,
  "message": "Student ID already exists"
}
```

#### 4. Calculate GPA by Semester 
- **URL**: `GET /grade-portal/students/gpa/{semester}`
- **Description**: Cho phép sinh viên tính toán điểm GPA của một học kỳ cụ thể.
- **Authorization**: `STUDENT`

**Path Variable**:
- **semester**: Số thứ tự của học kỳ (kiểu Int).

**Response**:
- **200 OK**: Tính toán GPA thành công cho học kỳ.
```json
{
  "statusCode": 200,
  "message": "GPA calculated successfully",
  "gpa": 3.8
}
```
- **404 Not Found**: Nếu không tìm thấy sinh viên.
```json
{
  "statusCode": 404,
  "message": "Student not found"
}
```
- **404 Not Found**: Nếu không tìm thấy thông tin học kỳ.
```json
{
  "statusCode": 404,
  "message": "Study not found"
}
```

### Study APIs

#### 1. Add Study Student 
- **URL**: `POST /grade-portal/study/add`
- **Description**: Cho phép Giảng viên thêm thông tin học tập của một sinh viên vào hệ thống.
- **Authorization**: `LECTURER`

**Request Body**:
```json
{
  "studentId": 2213132,
  "classId": 1
}
```

**Response**:
- **200 OK**: Thông tin học tập đã được thêm thành công.
```json
{
  "statusCode": 200,
  "message": "Study added successfully"
}
```
- **409 Conflict**: Thông tin học tập đã tồn tại.
```json
{
  "statusCode": 409,
  "message": "Study already exists"
}
```
- **404 Not Found**: Nếu không tìm thấy sinh viên.
```json
{
  "statusCode": 404,
  "message": "Student not found"
}
```
- **404 Not Found**: Nếu không tìm thấy môn học.
```json
{
  "statusCode": 404,
  "message": "Subject not found"
}
```
- **404 Not Found**: Nếu không tìm thấy lớp học.
```json
{
  "statusCode": 404,
  "message": "Class not found"
}
```
- **400 Bad Request**: Chuyên ngành và khoa của sinh viên không phù hợp.
```json
{
  "statusCode": 400,
  "message": "Student major and faculty does not match"
}
```
- **400 Bad Request**: Chuyên ngành của sinh viên không phù hợp với môn học.
```json
{
  "statusCode": 400,
  "message": "Student major does not match"
}
```
- **400 Bad Request**: Khoa của sinh viên không phù hợp.
```json
{
  "statusCode": 400,
  "message": "User faculty does not match"
}
```

#### 2. Update Study Student 
- **URL**: `PUT /grade-portal/study/update`
- **Description**: Cho phép Giảng viên cập nhật thông tin học tập của một sinh viên.
- **Authorization**: `LECTURER`

**Request Body**:
```json
{
  "id": 1,
  "studentId": 2213136,
  "subjectId": "UpdatedStudyId",
  "classId": 1,
  "semester": 242,
  "gradesList": [
    {
      "gradeType": "Midterm",
      "gradeValue": 40.0
    },
    {
      "gradeType": "Final",
      "gradeValue": 45.5
    }
  ]
}
```

**Response**:
- **200 OK**: Thông tin học tập đã được cập nhật thành công.
```json
{
  "statusCode": 200,
  "message": "Study updated successfully"
}
```
- **400 Bad Request**: ID của học phần không được phép null.
```json
{
  "statusCode": 400,
  "message": "StudyId cannot be null"
}
```
- **404 Not Found**: Không tìm thấy thông tin học tập của sinh viên.
```json
{
  "statusCode": 404,
  "message": "Study not found"
}
```
- **400 Bad Request**: Chuyên ngành và khoa của sinh viên không phù hợp.
```json
{
  "statusCode": 400,
  "message": "Student major and faculty does not match"
}
```
- **400 Bad Request**: Chuyên ngành của sinh viên không phù hợp với môn học.
```json
{
  "statusCode": 400,
  "message": "Student major does not match"
}
```
- **400 Bad Request**: Khoa của sinh viên không phù hợp với môn học.
```json
{
  "statusCode": 400,
  "message": "User faculty does not match"
}
```

#### 3. Delete Study Student  
- **URL**: `DELETE /grade-portal/study/delete/{id}`
- **Description**: Endpoint này cho phép Giảng viên xóa thông tin học tập của một sinh viên dựa trên ID.
- **Authorization**: `LECTURER`

**Path Variable**:
- **id**: ID của thông tin học tập cần xóa (kiểu Long).

**Response**:
- **200 OK**: Thông tin học tập đã được xóa thành công.
```json
{
  "statusCode": 200,
  "message": "Study deleted successfully"
}
```
- **404 Not Found**: Không tìm thấy thông tin học tập với ID cung cấp.
```json
{
  "statusCode": 404,
  "message": "Study not found"
}
```

#### 4. Get Study by ID 
- **URL**: `GET /grade-portal/study/{id}`
- **Description**: Cho phép Giảng viên hoặc Admin lấy thông tin học tập dựa trên ID.
- **Authorization**: `LECTURER` hoặc `ADMIN`.

**Path Variable**:
- **id**: ID của thông tin học tập cần lấy (kiểu Long).

**Response**:
- **200 OK**: Thông tin học tập đã được tìm thấy thành công.
```json
{
  "statusCode": 200,
  "message": "Study found successfully",
  "study": {
    "studentId": 2212870,
    "subjectId": "CO2000",
    "classId": 1,
    "semester": 1,
    "gradesList": [
      {
        "gradeType": "Midterm",
        "gradeValue": 40.0
      },
      {
        "gradeType": "Final",
        "gradeValue": 45.5
      }
    ]
  }
}
```
- **404 Not Found**: Không tìm thấy thông tin học tập với ID cung cấp.
```json
{
  "statusCode": 404,
  "message": "Study not found"
}
```

#### 5. Get Studies by Semester 
- **URL**: `GET /grade-portal/study/result/{semester}?page={page}&size={size}`
- **Description**: Cho phép Sinh viên lấy danh sách thông tin học tập trong một học kỳ cụ thể.
- **Authorization**: `STUDENT`

**Path Variables**:
- **semester**: Học kỳ cần lấy thông tin (kiểu Int).
- **page**: Trang hiện tại (mặc định là 0).
- **size**: Số lượng bản ghi trên một trang (mặc định là 10).

**Response**:
- **200 OK**: Trả về danh sách thông tin học tập của sinh viên trong học kỳ.
```json
{
  "statusCode": 200,
  "message": "Studies found successfully",
  "studies": [
    {
      "studentId": 2212870,
      "subjectId": "CO2000",
      "classId": 1,
      "semester": 1,
      "grades": [
        {
          "gradeType": "Midterm",
          "gradeValue": 40.0
        },
        {
          "gradeType": "Final",
          "gradeValue": 45.5
        }
      ]
    }
  ]
}
```
- **404 Not Found**: Không tìm thấy thông tin học tập cho học kỳ yêu cầu.
```json
{
  "statusCode": 404,
  "message": "Studies not found"
}
```

#### 6. Get Semester CSV API
- **URL**: `GET /grade-portal/study/result/get-csv/{semester}`
- **Description**: Endpoint này cho phép Sinh viên tải xuống thông tin học tập của một học kỳ dưới dạng file CSV.
- **Authorization**: `STUDENT`

**Path Variable**:
- **semester**: Học kỳ cần lấy thông tin (kiểu Int).

**Response**:
- **200 OK**: Trả về file CSV chứa thông tin học tập của học kỳ.
  - File CSV sẽ được trả về dưới dạng đính kèm trong response.
- **404 Not Found**: Không tìm thấy thông tin học tập cho học kỳ yêu cầu.
```json
{
  "statusCode": 404,
  "message": "Study data not found for the semester"
}
```

#### 7. Get Study by Subject and Semester 
- **URL**: `POST /grade-portal/study/result`
- **Description**: Cho phép Sinh viên lấy thông tin điểm của một môn học trong một học kỳ cụ thể.
- **Authorization**: `STUDENT`

**Request Body**:
```json
{
  "subjectId": "number",
  "semester": "number"
}
```

**Response**:
- **200 OK**: Trả về thông tin điểm của môn học trong học kỳ yêu cầu.
```json
{
  "statusCode": 200,
  "message": "Study result found successfully",
  "studyResult": {
    "subjectId": "CO2000",
    "semester": 1,
    "grades": [
      {
        "gradeType": "Midterm",
        "gradeValue": 40.0
      },
      {
        "gradeType": "Final",
        "gradeValue": 45.5
      }
    ]
  }
}
```
- **404 Not Found**: Không tìm thấy thông tin điểm cho môn học trong học kỳ yêu cầu.
```json
{
 "statusCode": 404,
 "message": "Study result not found"
}
```
### Subject APIs

#### 1. Generate Subject Report 
- **URL**:  `POST /grade-portal/study/generate-report`
- **Description**: Cho phép admin tạo báo cáo cho môn học ở học kỳ chỉ định.
- **Authorization**: `ADMIN`
- **Request Body**:
  ```json
  {
    "subjectId": "CO3030",
    "semester": 241,
    "year": 2024
  }
  ```
- **Response**:
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "Subject report generated successfully",
      "reportLink": "path/to/generated/report.csv"
    }
    ```
  - **404 Not Found**:
    ```json
    {
      "statusCode": 404,
      "message": "Subject or semester not found"
    }
    ```

#### 2. Add Subject 
- **URL**:  `POST /grade-portal/subjects/add`
- **Description**: Cho phép giảng viên tạo môn học mới.
- **Authorization**: `LECTURER`
- **Request Body**:
  ```json
  {
    "id": "CO3034",
    "name": "MMT",
    "credits": 3,
    "faculty": "KHMT",
    "major": "CNPM"
  }
  ```
- **Response**:
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "Subject added successfully"
    }
    ```
  - **409 Conflict**:
    ```json
    {
      "statusCode": 409,
      "message": "Subject already exists"
    }
    ```

#### 3. Update Subject 
- **URL**:  `PUT /grade-portal/subjects/update`
- **Description**: Cho phép giảng viên cập nhật thông tin môn học.
- **Authorization**: `LECTURER`
- **Request Body**:
  ```json
  {
    "id": "CO3034",
    "name": "MMT",
    "credits": 3,
    "faculty": "KHMT",
    "major": "CNPM"
  }
  ```
- **Response**:
  - **404 Not Found**:
    ```json
    {
      "statusCode": 404,
      "message": "Subject not found"
    }
    ```
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "Subject updated successfully"
    }
    ```

#### 4. Delete Subject 
- **URL**:  `DELETE /grade-portal/subjects/delete`
- **Description**: Cho phép giảng viên xóa môn học dựa vào id.
- **Authorization**: `LECTURER`
- **Request Body**:
  ```json
  {
    "id": "CO3034"
  }
  ```
- **Response**:
  - **404 Not Found**:
    ```json
    {
      "statusCode": 404,
      "message": "Subject not found"
    }
    ```
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "Subject deleted successfully"
    }
    ```

---

#### 5. Get All Subjects 
- **URL**:  `GET /grade-portal/subjects/all`
- **Description**: Cho phép giảng viên và admin lấy tất cả môn học trong hệ thống, hỗ trợ phân trang.
- **Authorization**: `LECTURER` or `ADMIN`
- **Query Parameters**:
  - **page**: Số trang hiện tại (mặc định là 0)
  - **size**: Số lượng bản ghi trên 1 trang (mặc định là 3)
- **Response**:
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "Subject found successfully",
      "subjects": [
        {
          "id": "CO3034",
          "name": "MMT",
          "credits": 3,
          "faculty": "KHMT",
          "major": "CNPM"
        }
      ]
    }
    ```

  - **404 Not Found**:
      ```json
      {
        "statusCode": 404,
        "message": "Subject not found"
      }
      ```

#### 6. Get Next Semester
- **URL**:  `GET /grade-portal/subjects/next-semester`
- **Description**: Cho phép giảng viên, admin và sinh viên biết được học kì tiếp theo.
- **Authorization**: `LECTURER` or `ADMIN` or `STUDENT`

- **Response**:
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "This is the next semester",
      "nextSemester": 243
    }
    ```

#### 7. Register Subject For Next Semester
- **URL**:  `POST /grade-portal/subjects/register`
- **Description**: Cho phép sinh viên đăng kí môn học trong kì tiếp theo.
- **Authorization**: `STUDENT`
- **Request Body**:
```json
{
  "semester": 243,
  "subjectId": "RegisterSubjectId"
}
```
- **Response**:
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "Subject registered successfully",
      "subjectRegisterDTO": [
        {
          "semester": 243,
          "subjectId": "RegisterSubjectId"
        }
      ]
    }
    ```


| Status Code | Description           | Example Response                                        |
|-------------|-----------------------|---------------------------------------------------------|
| 404         | Subject không tồn tại | `{ "statusCode": 404, "message": "Subject not found" }` |
| 404         | Student không tồn tại | `{ "statusCode": 400, "message": "Student not found" }` |


#### 8. Get Number Of Registered Students For One Subject
- **URL**:  `POST /grade-portal/subjects/register`
- **Description**: Cho phép mọi người xem số lượng sinh viên đăng kí 1 môn của kì tiếp theo.
- **Authorization**: `STUDENT` or `LECTURER` or `ADMIN`
- **Request Body**:
```json
{
  "semester": 243,
  "subjectId": "RegisterSubjectId"
}
```
- **Response**:
  - **200 OK**: Trường hợp chưa có sinh viên đăng kí
    ```json
    {
      "statusCode": 200,
      "message": "No student has registered this subject in this semester yet",
      "registerNum": 0
    }
    ```

  - **200 OK**: Trường hợp đã có sinh viên đăng kí
    ```json
    {
      "statusCode": 200,
      "message": "Number of student registered this subject in this semester fetch successfully",
      "registerNum": 100
    }
    ```

  - **404 Not Found**:
      ```json
      {
        "statusCode": 404,
        "message": "Class not found"
      }
      ```

#### 9. Open New Classes For A Qualified Subject In New Semester
- **URL**:  `POST /grade-portal/subjects/open`
- **Description**: Cho phép admin mở các lớp học mới cho một môn học đủ số lượng sinh viên đăng kí nhất định.
- **Authorization**: `ADMIN`
- **Request Body**:
```json
{
  "maxStudent": 40,
  "semester": 243,
  "subjectId": "QualifiedSubjectId"
}
```
- **Response**:
  - **200 OK**: 
    ```json
    {
      "statusCode": 200,
      "message": "Classes opened successfully",
      "listClassDTO": [
      {
        "name": "Class_01",
        "subjectId": "QualifiedSubjectId",
        "semester": 243
      },
      {
        "name": "Class_02",
        "subjectId": "QualifiedSubjectId",
        "semester": 243
      }
    ]
    }
    ```

  - **404 Not Found**:
      ```json
      {
        "statusCode": 404,
        "message": "Class not found"
      }
      ```


### User APIs

#### 1. Create Admin
- **URL**:  `POST /grade-portal/users/create-admin`
- **Description**: Cho phép admin tạo admin mới trong hệ thống.
- **Authorization**: `ADMIN`
- **Request Body**:
  ```json
  {
    "username": "admin2",
    "password": "123",
    "name": "Admin 2"
  }
  ```
- **Response**:
  - **409 Conflict**:
    ```json
    {
      "statusCode": 409,
      "message": "User existed"
    }
    ```
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "Admin created successfully"
    }
    ```

#### 2. Create Lecturer 
- **URL**:  `POST /grade-portal/users/create-lecturers`
- **Description**: Cho phép admin tạo giảng viên mới trong hệ thống.
- **Authorization**: `ADMIN`
- **Request Body**:
  ```json
  {
    "username": "nguyencongthanh2408@gmail.com",
    "password": "12345678",
    "name": "Thanh"
  }
  ```
- **Response**:
  - **409 Conflict**:
    ```json
    {
      "statusCode": 409,
      "message": "User existed"
    }
    ```
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "Lecturer created successfully"
    }
    ```


#### 3. Get User by ID 
- **URL**:  `GET /grade-portal/users/{id}`
- **Description**: Cho phép admin lấy thông tin chi tiết của người dùng dựa trên id.
- **Authorization**: `ADMIN`
- **Response**:
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "User found successfully",
      "user": {
        "id": "12345",
        "username": "nguyencongthanh2408@gmail.com",
        "role": "LECTURER",
        "name": "Thanh"
      }
    }
    ```
  - **404 Not Found**:
    ```json
    {
      "statusCode": 404,
      "message": "User not found"
    }
    ```

#### 4. Update User Info 
- **URL**:  `PATCH /grade-portal/users/update`
- **Description**: Cho phép admin cập nhật thông tin người dùng.
- **Authorization**: `ADMIN`
- **Request Body**:
  ```json
  {
    "id": "12345",
    "username": "nguyencongthanh2408@gmail.com",
    "password": "newpassword123",
    "role": "LECTURER"
  }
  ```
- **Response**:
  - **404 Not Found**:
    ```json
    {
      "statusCode": 404,
      "message": "User not found"
    }
    ```
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "Update info successfully"
    }
    ```

#### 5. Delete User Account API
- **URL**:  `DELETE /grade-portal/users/delete/{id}`
- **Description**: Cho phép admin xóa nguười dùng dựa vào id.
- **Authorization**: `ADMIN`
- **Response**:
  - **404 Not Found**:
    ```json
    {
      "statusCode": 404,
      "message": "User not found"
    }
    ```
  - **200 OK**:
    ```json
    {
      "statusCode": 200,
      "message": "User deleted successfully"
    }
    ```
