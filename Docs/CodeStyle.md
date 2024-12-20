# CODESTYLE
﻿1. **Quy tắc Chung**

- Ngôn ngữ: Sử dụng Kotlin cho tất cả các tệp mã nguồn.
- Thư viện : Sử dụng các thư viện tiêu chuẩn của Kotlin và Spring một cách nhất quán.Đảm bảo rằng các thư viện được sử dụng là phiên bản mới nhất và ổn định.
- Cấu trúc gói : Sử dụng cấu trúc gói (package) rõ ràng và có tổ chức.Tên gói nên sử dụng chữ thường và phân tách bằng dấu chấm (.) theo quy tắc tên miền ngược (reverse domain name).
- Định dạng mã: Sử dụng định dạng mã tự động của IDE (IntelliJ IDEA) để đảm bảo mã được căn chỉnh và dễ đọc.
- Ký tự trắng: Sử dụng một dòng trống giữa các phương thức và giữa các nhóm phương thức có liên quan.
- Ký tự đặc biệt: Tránh sử dụng ký tự đặc biệt trong tên biến, tên hàm, và tên lớp.

2. **Quy Tắc Đặt Tên**

- Tên biến: Sử dụng camelCase cho tên biến. Ví dụ: emailSender, userName, classDTO, response, pageable,… 
- Tên hàm: Sử dụng camelCase cho tên hàm và mô tả rõ ràng chức năng của nó. Ví dụ: sendEmail(), getUser Details(), addClass(), updateClass(), getClassById(),…
- Tên lớp: Sử dụng PascalCase cho tên lớp. Ví dụ: EmailConfig, User Service, ClassController, ClassService,….
- Hậu tố và tiền tố: Sử dụng hậu tố DTO cho các lớp dữ liệu truyền tải (Data Transfer Object). Ví dụ: User ResponseDTO, GradeDTO. Sử dụng tiền tố list cho các biến danh sách. Ví dụ: listUser DTO, listSubjectDTO.
- Tên hằng số: Sử dụng UPPER\_SNAKE\_CASE cho tên hằng số. Ví dụ: DEFAULT\_PAGE\_SIZE,...

3. **Khai báo Biến**

- Sử dụng val cho các biến không thay đổi và var cho các biến có thể thay đổi.
- Đặt giá trị mặc định cho các biến khi có thể tránh tình trạng NULL .

4. **Sử dụng Nullable Types**

- Sử dụng kiểu nullable (?) khi cần thiết, nhưng tránh lạm dụng.
- Sử dụng !! chỉ khi bạn chắc chắn rằng giá trị không null.

5. **Cách Sắp Xếp Các Thuộc Tính**

- Sắp xếp các thuộc tính trong lớp theo nhóm:
- Các thuộc tính cơ bản (statusCode, message, v.v.)
- Các DTO đơn lẻ
- Các danh sách DTO
- Các thuộc tính phân trang
- Các thuộc tính khác (như file attachment)

6. **Cấu trúc Lớp**

- Cấu trúc lớp: Các lớp nên được cấu trúc theo thứ tự sau:
- Các thuộc tính (properties)
- Các hàm khởi tạo (constructors)
- Các phương thức (methods)
- Sử dụng `lateinit`: Chỉ sử dụng`lateinit` cho các thuộc tính không thể khởi tạo ngay lập tức nhưng sẽ được khởi tạo trước khi sử dụng.

7. **Cấu hình Spring**

- Sử dụng @Configuration: Đánh dấu các lớp cấu hình với @Configuration.Sử dụng @Value để tiêm các giá trị từ tệp cấu hình (application.properties hoặc application.yml).Sử dụng @Bean: Đánh dấu các phương thức tạo bean với @Bean.

8. **Định dạng Mã**

- Khoảng trắng: Sử dụng khoảng trắng để tách biệt các khối mã và cải thiện khả năng đọc và sử dụng 4 khoảng trắng cho mỗi cấp độ thụt lề
- Dấu ngoặc nhọn: Đặt dấu ngoặc nhọn mở { ở cùng dòng với định nghĩa hàm hoặc lớp, và dấu ngoặc nhọn đóng } ở dòng mới.
- Sử dụng dòng trống để phân tách các phần khác nhau trong mã , như giữa các hàm hoặc giữa các nhóm thuộc tính.
- Sử dụng một dòng trống trước và sau các khối điều kiện (if, else, when).

9. **Annotations**

- Sử dụng annotations như @Service,@Override,@Autowired,…. một cách nhất quán và rõ ràng.
- Sử dụng các annotation như @Query và @Param để chỉ định các truy vấn JPQL và các tham số tương ứng.
- Annotations: Đặt annotations trên cùng một dòng với phương thức hoặc lớp mà nó áp dụng.

10. **Xử lý ResponseEntity**

- ResponseEntity: Sử dụng ResponseEntity để trả về phản hồi từ các phương thức. Đảm bảo rằng mã trạng thái HTTP được thiết lập chính xác dựa trên kết quả của các thao tác.

11. **Cách Viết Truy Vấn**

- Viết truy vấn JPQL rõ ràng và dễ hiểu, sử dụng định dạng nhiều dòng cho các truy vấn phức tạp.
- Đảm bảo rằng các điều kiện trong truy vấn được sắp xếp hợp lý và dễ theo dõi.

12. **Xử lý Ngoại lệ**

- Sử dụng các lớp ngoại lệ tùy chỉnh để xử lý lỗi.
- Đảm bảo rằng các thông báo lỗi rõ ràng và có thể hiểu được.

13. **Quy tắc về quyền truy cập**

Quy tắc truy cập: Sử dụng @PreAuthorize để xác định quyền truy cập cho từng phương thức. Đảm bảo rằng các vai trò được chỉ định chính xác và rõ ràng.

14. **Tham số và Giá trị Mặc định**

- Tham số: Sử dụng @RequestParam để lấy tham số từ yêu cầu HTTP. Đặt giá trị mặc định cho các tham số nếu cần thiết.

15. **Tài liệu và Chú thích**

- Chú thích: Sử dụng chú thích để giải thích các phần mã phức tạp hoặc không rõ ràng. Sử dụng tiếng Anh cho các chú thích.
- Tài liệu: Cung cấp tài liệu cho các lớp và phương thức quan trọng, mô tả chức năng và cách sử dụng.

16. **Kiểm tra mã**

- Kiểm tra mã: Sử dụng các công cụ kiểm tra mã (như SonarQube) để đảm bảo chất lượng mã nguồn.
- Unit tests: Viết các bài kiểm tra đơn vị cho các phương thức quan trọng trong dịch vụ và bộ điều khiển.

