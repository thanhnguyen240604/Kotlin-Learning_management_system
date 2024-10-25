# Danh Sách Các API và Chức Năng

## Của admin

### Đăng nhập và đăng xuất

- **Đăng nhập**: `admin/api/login`  
  - Mô tả: Tính năng đăng nhập.
  - yêu cầu gửi lên :
  ```bash
   {
    "idToken": idToken
   }
  ```
- **Đăng xuất**: `admin/api/logout`  
  - Mô tả: Tính năng đăng xuất, xóa cookie trên máy người dùng
### Quản lí tài khoản
- **Thêm admin mới**: `admin/api/create`  
  - Mô tả: Tạo thêm 1 admin mới.
  - Yêu cầu gửi lên:
  ```bash
   {
    "email": email,
    "name": name,
    "ms": ms,
   }
  ```
- **Tạo tài khoản**: `admin/api/account/create`  
  - Mô tả: Tạo thêm tài khoản (có thể gửi lên một danh sách tài khoản).
  - Yêu cầu gửi lên:
     ```bash
       [
         { // object 1
           "email": email,
           "name": name,
           "ms": mssv,
           "faculty": faculty,
           "role": role
         },
         { // object 2
           ....
         }
       ]
     ```
### Quản lý khóa học và lớp học 
- **Tạo khóa học**: `admin/api/course/create`  
    - Mô tả: Tạo thêm 1 khóa học.
    - Yêu cầu gửi lên: 
    ```bash
      {
        "name": name,
        "credit": credit,
        "ms": ms,
        "desc": desc
      }
    ```
- **Tạo lớp học**: `admin/api/class/create`  
    - Mô tả: Tạo thêm 1 lớp học mới.
    - Yêu cầu gửi lên:
    ```bash
      {
        "name": name,
        "semester": semester,
        "course_id": course_id,
        "teacher_id": teacher_id,
        "listStudent_id": [ // 1 mảng các string mssv
          mssv_1, mssv_2, ...
        ]
      }
    ```
