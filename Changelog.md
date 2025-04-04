# Changelog: AiFlashLight

Tài liệu này ghi lại tất cả các thay đổi đáng chú ý trong dự án AiFlashLight.

## [1.0.0] - 2023-04-04
### Added
- Thiết lập cấu trúc dự án ban đầu theo tiêu chuẩn Android
- Triển khai tính năng đèn pin cơ bản với khả năng bật/tắt
- Hỗ trợ điều chỉnh độ sáng đèn pin (trên thiết bị hỗ trợ)
- Tạo bottom navigation cho điều hướng giữa các tính năng
- Tạo các placeholder cho các tính năng sắp triển khai (Screen Light, SOS, Music Effect, Settings)
- Thiết kế UI với Material Design, bao gồm dark/light theme
- Tự động tắt đèn pin khi rời khỏi ứng dụng hoặc tạm dừng

### Technical
- Sử dụng Camera2 API để điều khiển đèn flash
- Kiểm tra tương thích thiết bị và xử lý trường hợp thiết bị không có đèn flash
- Cài đặt cơ chế yêu cầu quyền camera cho đèn flash
- Thiết lập cấu trúc Fragment để dễ dàng mở rộng sau này

## [Upcoming]
### Planned
- Tính năng Đèn Màn Hình với nhiều màu sắc và hiệu ứng
- Tính năng SOS với phát hiện va chạm tự động
- Tính năng tin nhắn khẩn cấp với vị trí GPS
- Hiệu ứng đèn nhảy theo nhạc
- Thiết lập đa ngôn ngữ
- Tối ưu hóa pin và hiệu suất

## [Chưa phát hành]

### Thiết kế & Tài liệu
- Hoàn thành thiết kế cấu trúc toàn bộ ứng dụng
- Tạo tài liệu hướng dẫn chi tiết cho tất cả các module
- Tạo tài liệu hướng dẫn sử dụng cho người dùng cuối
- Thiết lập kế hoạch phát triển và lộ trình

### Module: Basic Flashlight
- Tạo hướng dẫn chi tiết cho module đèn pin cơ bản
- Định nghĩa các thành phần chính: CameraManager, FlashlightService, và UI
- Thiết kế flow xử lý quyền camera và kiểm tra phần cứng
- Chuẩn bị mẫu code cho điều khiển đèn flash

### Module: SOS Thông Minh
- Tạo hướng dẫn chi tiết cho module SOS thông minh
- Thiết kế thuật toán phát hiện va chạm từ dữ liệu cảm biến gia tốc
- Chuẩn bị mẫu code cho mô hình nhận diện tác động
- Định nghĩa quy trình báo động và xác nhận SOS

### Module: Thông Báo Khẩn Cấp
- Tạo hướng dẫn chi tiết cho module thông báo khẩn cấp
- Thiết kế cấu trúc quản lý liên hệ khẩn cấp
- Chuẩn bị mẫu code cho dịch vụ theo dõi GPS và gửi SMS
- Định nghĩa các mẫu tin nhắn cứu hộ

### Module: Đèn Màn Hình
- Tạo hướng dẫn chi tiết cho module đèn màn hình
- Thiết kế các thành phần chính: ScreenLightManager và LightEmitterView
- Định nghĩa cơ chế điều khiển độ sáng và nhiệt độ màu
- Chuẩn bị mẫu code cho màn hình đèn toàn màn hình

### Module: Hiệu Ứng Âm Nhạc
- Tạo hướng dẫn chi tiết cho module hiệu ứng âm nhạc
- Thiết kế các thành phần chính: AudioAnalyzer và PatternGenerator
- Định nghĩa các mẫu hiệu ứng ánh sáng dựa trên phân tích âm thanh
- Chuẩn bị mẫu code cho xử lý dữ liệu âm thanh thời gian thực

### Module: Monetization
- Tạo hướng dẫn chi tiết cho module monetization
- Thiết kế cấu trúc AdsManager và BillingManager
- Định nghĩa các gói Premium và tính năng mở khóa
- Chuẩn bị mẫu code cho quản lý quảng cáo và giao dịch mua hàng

### Module: Material Design UI
- Tạo hướng dẫn chi tiết cho module giao diện Material Design
- Thiết kế hệ thống theme và bảng màu
- Định nghĩa các thành phần UI chính: nút đèn flash, navigation, và layout
- Chuẩn bị mẫu code cho các custom views chính

### Module: I18n (Đa Ngôn Ngữ)
- Tạo hướng dẫn chi tiết cho module đa ngôn ngữ
- Thiết kế cấu trúc LanguageManager
- Chuẩn bị mẫu strings.xml cho các ngôn ngữ chính
- Định nghĩa quy trình chuyển đổi ngôn ngữ
