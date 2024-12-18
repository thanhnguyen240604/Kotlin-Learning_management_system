# TỔ CHỨC VÀ QUẢN LÝ CODE
## Tổ chức code

### Tổng quan dự án
- **Ngôn ngữ**: Kotlin  
- **Framework**: Spring  
- **Database**: MySQL  
- **Kiến trúc**: 3 lớp  

Mã nguồn của dự án được lưu trong thư mục `src` trong Repository của nhóm. Mã nguồn được chia ra làm các thư mục sau:

- `repository`: Chứa các class đóng vai trò làm repository tương tác với cơ sở dữ liệu thông qua JPA.
- `service`: Chứa các class đóng vai trò làm service xử lý logic các yêu cầu người dùng.
- `controller`: Chứa các class đóng vai trò controller tiếp nhận và phản hồi các yêu cầu của người dùng.
- `model`: Chứa các class thể hiện các thực thể trong cơ sở dữ liệu.
- `dto`: Chứa các class quy định cấu trúc JSON cho các request của người dùng cũng như response cho hệ thống.
- `mapper`: Chứa các class thực hiện chức năng ánh xạ các model sang DTO và ngược lại.
- `configuration`: Chứa các class cấu hình cho ứng dụng bao gồm: cấu hình ứng dụng, cấu hình bảo mật và cấu hình email dùng cho xác thực OTP.
- `exception`: Chứa các class quy định các loại exception chung cho toàn bộ ứng dụng.

Ngoài ra, ứng dụng còn được cấu hình thông qua file `application.yml` được lưu trong thư mục `resources`. Các key quan trọng được lưu trong tệp `.env` và được mã hóa để đảm bảo an toàn dữ liệu. Các dependency được cấu hình trong file `pom.xml`.

### Docker
Bao gồm `Dockerfile` và `docker-compose.yml` được lưu ở root trong thư mục dự án.  
- `Dockerfile`: Được sử dụng để build ứng dụng thành docker image phục vụ cho mục đích deploy.  
- `docker-compose.yml`: Được sử dụng để cấu hình khi chạy multiple containers đối với image của ứng dụng và image của MySQL.

  ---

## Quản lý code

### Nền tảng sử dụng
Nhóm sử dụng GitHub để lưu trữ, chia sẻ và quản lý mã nguồn.

### Cấu trúc repository
Các nhánh trong repository được chia ra thành 3 loại, cụ thể như sau:

- **Nhánh chính (main branch)**:  
  Đây là nhánh ổn định dùng để lưu trữ mã nguồn release sau khi đã tiến hành cập nhật và sửa lỗi. Trước khi merge mã nguồn vào nhánh này, các developer trong nhóm cần tạo pull request để nhóm trưởng tiến hành kiểm tra và phê duyệt. Mỗi developer cần giải quyết xung đột mã nguồn trước khi tạo pull request.

- **Nhánh chức năng (feature branch)**:  
  Đây là nhánh được các developer tạo ra dựa trên nhánh chính, nhằm hiện thực và hoàn thiện các chức năng nhất định mà mình được phân công.

- **Nhánh sửa lỗi (hotfix branch)**:  
  Đây là nhánh được tạo ra bởi các developer nhằm sửa lỗi nhanh các chức năng của mình. Ngoài ra còn được sử dụng để cập nhật các cấu hình cần thiết cho dự án.

### Quy trình Review Code
Quy trình diễn ra khi developer tạo pull request lên cho nhóm trưởng. Trước khi tiến hành Merge, nhóm trưởng phải kiểm tra branch theo các bước sau:

1. **Kiểm tra xung đột**:  
   Nhóm trưởng cần kiểm tra xung đột mã nguồn giữa nhánh chức năng muốn merge với nhánh chính. Nếu có xung đột xảy ra, nhóm trưởng đóng pull request và yêu cầu developer thực hiện chức năng đó xử lý xung đột.

2. **Kiểm tra tính mới**:  
   Nhóm trưởng cần kiểm tra xem mã nguồn trong nhánh đó có phải là phiên bản mới nhất đối với nhánh chính không. Nếu không, nhóm trưởng đóng request và yêu cầu developer cập nhật mã nguồn mới nhất từ nhánh chính.

3. **Kiểm tra lỗi**:  
   Nhóm trưởng cần kiểm tra xem chức năng được hiện thực có hoạt động đúng với yêu cầu đặt ra không, có bị xung đột với các chức năng khác đã có trong nhánh chính không. Nếu có lỗi xảy ra, nhóm trưởng cần liên hệ và trao đổi với developer về các vấn đề cần khắc phục.

4. **Tiến hành merge**:  
   Sau khi đã thực hiện cả 3 bước trên, nếu không có vấn đề phát sinh, nhóm trưởng có thể merge nhánh chức năng vào nhánh chính, sau đó ghi chú các chức năng đã hoàn thành vào báo cáo.

### Các quy tắc của Developers
Để quá trình làm việc diễn ra thuận lợi và thống nhất giữa các developers trong nhóm, mỗi developer cần tuân thủ một số quy tắc sau:

- Cần tuân thủ các quy ước mã nguồn bao gồm: cách đặt tên biến; cách đặt tên thư mục, tệp tin; cách trình bày mã nguồn.
- Cần duy trì kiến trúc của dự án, các developer không có quyền thay đổi kiến trúc dự án.
- Các developer chỉ được quyền chỉnh sửa mã nguồn trong nhánh chức năng mà mình được phân công, không được can thiệp vào nhánh của thành viên khác. Trong trường hợp các chức năng có liên quan với nhau, các developer được phân công hiện thực các chức năng đó cần liên hệ và trao đổi với nhau để hoàn thành công việc.
- Khi phát hiện các điểm bất hợp lý trong tài liệu được thống nhất trước đó, các developer có quyền đóng góp ý kiến của mình với nhóm trưởng nhằm cải tiến và hoàn thiện dự án.
- Trong quá trình hiện thực, các developer cần cập nhật mã nguồn trong branch của mình từ nhánh chính để đảm bảo tính mới của mã nguồn.
- Sau khi hoàn thiện phần hiện thực, các developer cần tiến hành kiểm tra và sửa lỗi chức năng bằng cách chạy thử trên local.
- Cần khắc phục các xung đột mã nguồn trước khi tạo pull request.
- Khi commit, các developer cần mô tả rõ những gì mình đã cập nhật trên các tệp tin được chọn.
- Khi tạo pull request, các developer cần ghi vào phần mô tả những gì mình đã hiện thực trong nhánh đó. Nếu không ghi mô tả, cần báo cáo lại với nhóm trưởng khi tiến hành review mã nguồn.


