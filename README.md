<a id="readme-top"></a>

<br />
<div align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="https://hcmut.edu.vn/img/nhanDienThuongHieu/01_logobachkhoasang.png" alt="Logo" width="30%" height="30%">
  </a>
  <h2 align="center">ĐỒ ÁN TỔNG HỢP HƯỚNG CÔNG NGHỆ PHẦN MỀM HK241 - GRADE PORTAL</h2>
  <p align="center">
    Đề tài: thiết kế hệ thống tra cứu và quản lí điểm dành cho sinh viên, giảng viên đại học
    <br />
  </p>
</div>

## Thông tin nhóm
Tên nhóm: Nhóm con gà
| STT | Họ và tên | MSSV | Vai trò |
|-------|-------|-------|-------|
| 1 | Nguyễn Hữu Khánh | 2211521 | PO |
| 2 | Nguyễn Hà Thùy Linh | 2211856 | Dev |
| 3 | Nguyễn Ngọc Quý | 2212870 | Dev |
| 4 | Nguyễn Minh Tân | 2213057 | Dev |
| 5 | Lê Trường Thịnh | 2213282 | Dev |
| 6 | Trần Huy Đức | 2210812 | Dev |
| 7 | Nguyễn Công Thành | 2213132 | Dev |

## Giới thiệu chung về dự án

### Chủ đề dự án

Dự án phát triển hệ thống tra cứu và quản lí điểm môn học dành cho sinh viên và giảng viên.
- Đề tài: Grade Portal
- Nhiệm vụ: Backend Kotlin

### Bối cảnh

Trong những năm gần đây, khi số lượng sinh viên theo học tại Đại học Bách Khoa - Đại học Quốc gia TP.HCM đang không ngừng gia tăng. Điều đó khiến việc quản lý thông tin học tập và điểm số của sinh viên trở thành một thách thức ngày càng lớn. Cùng với đó, chương trình đào tạo tại trường liên tục đổi mới, cập nhật để bắt kịp với sự phát triển nhanh chóng của công nghệ và khoa học toàn cầu, đặt ra yêu cầu cấp bách về việc xây dựng các hệ thống hỗ trợ quản lý và tra cứu thông tin học tập một cách hiệu quả. Trong số đó, một hệ thống cho phép sinh viên có thể tra cứu điểm số trong quá trình học tập là vô cùng quan trọng, không chỉ giúp sinh viên có thể theo dõi kết quả học tập của mình mà còn tạo điều kiện thuận lợi cho giảng viên trong việc quản lý lớp học và các dữ liệu liên quan đến điểm số.

Hiện nay, tại nhiều trường đại học trên thế giới, việc áp dụng các hệ thống quản lý điểm số thông minh đã trở thành xu hướng phổ biến, nhằm giúp cả sinh viên lẫn giảng viên dễ dàng truy cập, quản lý và cập nhật thông tin về kết quả học tập. Đại học Bách Khoa cũng không phải là ngoại lệ, với nhu cầu cấp thiết cho một hệ thống quản lí điểm số khi số lượng sinh viên ngày càng tăng, đặc biệt là trong những môn học có đông sinh viên tham gia. Việc áp dụng một hệ thống giúp sinh viên có thể tra cứu điểm số, theo dõi quá trình học tập và cập nhật kết quả của mình sẽ giúp giảm bớt áp lực cho cả giảng viên và sinh viên trong việc xử lý các thông tin về điểm số một cách thủ công, đồng thời giúp nâng cao tính minh bạch và chính xác trong việc công bố kết quả học tập.

Chính vì lẽ đó, dự án thiết kế hệ thống quản lí điểm trực tuyến này đã được thực hiện nhằm hướng đến mục tiêu hỗ trợ sinh viên và giảng viên tại Đại học Bách Khoa theo dõi, tra cứu điểm các môn học một cách thuận tiện hơn. Hệ thống này sẽ là một giải pháp hoàn hảo giúp cải thiện chất lượng học tập và giảng dạy cho sinh viên và giảng viên trong trường.

Sử dụng hệ thống này, sinh viên có thể kiểm tra, tra cứu điểm dễ dàng, tiết kiệm thời gian hơn trước. Điều này giúp cho các sinh viên có thể theo dõi và tự điều chỉnh quá trình học tập của mình nhằm phù hợp hơn với môi trường Bách Khoa. Về phần giảng viên, các thầy cô sẽ được hỗ trợ tính năng đính kèm bảng điểm dưới dạng tập tin CSV hoặc Excel, khiến cho việc nhập và theo dõi bảng điểm các lớp không còn phức tạp và mất thời gian như trước.

Một tính năng nổi bật khác của hệ thống, tuy không quá quan trọng nhưng lại vô cùng ý nghĩa, chính là tính năng bảng vinh danh (Hall of fame). Tính năng này sẽ động viên tinh thần sinh viên, giúp cho những sinh viên đã nỗ lực xứng đáng cảm thấy được trân trọng và khích lệ những sinh viên khác hãy cố gắng để đạt được thành tích tốt hơn. Từ đó tạo nên một môi trường học tập lành mạnh, nâng cao chất lượng học tập và giảng dạy.

Chung quy lại, nếu thành công, hệ thống này được dự báo sẽ trở thành công cụ hỗ trợ đắc lực cho sinh viên và giảng viên tại trường đại học Bách Khoa. Góp phần hiện đại hóa hệ thống giáo dục, nâng cao chất lượng và danh tiếng của trường.

### Tính năng

- `Admin` : Quản lý hệ thống, tạo và phân quyền cho giáo viên, tạo báo cáo.
- `Teacher` : Tạo môn học, tạo lớp học, thêm sinh viên vào lớp, tải lên file CSV chứa bảng điểm. 
- `Student` : Tra cứu điểm số theo môn học và học kì.
- `Hall of Fame` : Hiển thị danh sách các sinh viên có điểm số cao nhất trong lớp.

### Yêu cầu chức năng, yêu cầu phi chức năng và các sơ đồ
Xem trong báo cáo tổng hợp: [tại đây](Report/Report.pdf)

### Về API

## Báo cáo
- Báo cáo tiến độ làm việc theo tuần: [tại đây](Weekly_report).
- Báo cáo tổng hợp: [tại đây](Report/Report.pdf).

## Tự đánh giá tiến độ thành viên

|MSSV    | Tên thành viên      | Tuần 1 | Tuần 2 | Tuần 3 | Tuần 4 |  Tuần 5 | Tuần 6 | Tuần 7 | Tuần 8 |  
|--------|---------------------|--------|--------|--------|--------|---------|--------|--------|--------|
|2211521 | Nguyễn Hữu Khánh    | 🟢    |🟢      |🟢     |🟢      |🟢       |🟢     |🟢      |🟢     |
|2211856 | Nguyễn Hà Thùy Linh | 🟢    |🟢      |🟢     |🟢      |🟢       |🟢     |🟢      |🟢     |
|2212870 | Nguyễn Ngọc Quý     | 🟢    |🟢      |🟢     |🟢      |🟢       |🟢     |🟢      |🟢     |
|2213057 | Nguyễn Minh Tân     | 🟢    |🟢      |🟢     |🟢      |🟢       |🟢     |🟢      |🟢     |
|2213282 | Lê Trường Thịnh     | 🟢    |🟢      |🟢     |🟢      |🟢       |🟢     |🟢      |🟢     |
|2210812 | Trần Huy Đức        | 🟢    |🟢      |🟢     |🟢      |🟢       |🟢     |🟢      |🟢     |
|2213132 | Nguyễn Công Thành   | 🟢    |🟢      |🟢     |🟢      |🟢       |🟢     |🟢      |🟢     |
