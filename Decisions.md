# Quyết Định: AiFlashLight

Tài liệu này ghi lại các quyết định thiết kế và kỹ thuật quan trọng trong dự án AiFlashLight, bao gồm lý do và các phương án thay thế đã được xem xét.

## 1. Kiến Trúc Ứng Dụng

### Quyết định: Sử dụng kiến trúc MVVM (Model-View-ViewModel)

**Ngày**: Trước khi bắt đầu phát triển

**Bối cảnh**: Cần chọn kiến trúc phù hợp cho ứng dụng Android hiện đại

**Lựa chọn đã xem xét**:
- MVP (Model-View-Presenter)
- MVC (Model-View-Controller)
- MVVM (Model-View-ViewModel)
- Clean Architecture

**Quyết định**: Chọn MVVM kết hợp với Repository Pattern

**Lý do**:
- MVVM được Android chính thức hỗ trợ thông qua Android Architecture Components
- Tách biệt rõ ràng giữa UI (View) và logic nghiệp vụ (ViewModel)
- Hỗ trợ tốt cho việc xử lý vòng đời Activity/Fragment thông qua ViewModel
- LiveData giúp quản lý dữ liệu theo hướng reactive và lifecycle-aware
- Dễ dàng kiểm thử (ViewModel không phụ thuộc Android Framework)
- Repository Pattern giúp tách biệt nguồn dữ liệu và logic truy cập

## 2. Module Hóa

### Quyết định: Thiết kế ứng dụng theo cấu trúc module độc lập

**Ngày**: Trước khi bắt đầu phát triển

**Bối cảnh**: Cần quyết định cách tổ chức code cho ứng dụng có nhiều tính năng khác nhau

**Lựa chọn đã xem xét**:
- Cấu trúc monolithic (tất cả trong một project)
- Multi-module theo chức năng (mỗi tính năng một module)
- Multi-module theo lớp (UI, Domain, Data)

**Quyết định**: Sử dụng module logic (các manager và service riêng biệt) trong cùng một project

**Lý do**:
- Đơn giản hóa quá trình build và phát triển so với multi-module thực tế
- Các module logic (manager, service) vẫn giúp phân tách rõ trách nhiệm
- Dễ dàng tái sử dụng và bảo trì từng thành phần
- Giảm sự phụ thuộc giữa các tính năng khác nhau
- Dễ dàng thêm/xóa tính năng trong tương lai

## 3. Quản Lý Đèn Flash

### Quyết định: Sử dụng Camera API thay vì Camera2 API hoặc CameraX

**Ngày**: Khi thiết kế module Basic Flashlight

**Bối cảnh**: Cần chọn API phù hợp để điều khiển đèn flash

**Lựa chọn đã xem xét**:
- Camera API (cũ)
- Camera2 API
- CameraX

**Quyết định**: Sử dụng Camera API kết hợp với CameraManager trên Android M trở lên

**Lý do**:
- Đơn giản hơn cho mục đích chỉ điều khiển đèn flash
- Tương thích với nhiều thiết bị cũ (từ API 23)
- Tiêu thụ ít tài nguyên hơn Camera2 hoặc CameraX
- Trên Android M trở lên, có thể dùng CameraManager.turnOnTorchWithStrengthLevel() để điều chỉnh độ sáng (nếu thiết bị hỗ trợ)

## 4. Background Service

### Quyết định: Sử dụng Foreground Service với JobScheduler

**Ngày**: Khi thiết kế module SOS và Background Processing

**Bối cảnh**: Cần duy trì dịch vụ nền để giám sát cảm biến và xử lý tín hiệu SOS

**Lựa chọn đã xem xét**:
- Service thông thường
- IntentService
- Foreground Service
- JobScheduler / WorkManager

**Quyết định**: Foreground Service kết hợp với JobScheduler để khởi động lại khi cần

**Lý do**:
- Foreground Service ít bị hệ thống kill hơn so với service thông thường
- Người dùng nhận biết được khi dịch vụ đang chạy (qua notification)
- Hoạt động tin cậy hơn trên các phiên bản Android mới (8.0+) với các hạn chế chạy nền
- JobScheduler giúp khôi phục dịch vụ khi bị kill do hệ thống tiết kiệm pin
- Cung cấp kiểm soát tốt hơn về điều kiện chạy (kết nối mạng, sạc pin, v.v.)

## 5. Phát Hiện Va Chạm

### Quyết định: Sử dụng thuật toán tự phát triển thay vì sử dụng thư viện ML

**Ngày**: Khi thiết kế module SOS Thông Minh

**Bối cảnh**: Cần chọn phương pháp để phát hiện va chạm qua cảm biến gia tốc

**Lựa chọn đã xem xét**:
- Thuật toán ngưỡng đơn giản
- Thuật toán SVM (Support Vector Machine)
- TensorFlow Lite với mô hình đã được train
- Thư viện Google Activity Recognition

**Quyết định**: Thuật toán ngưỡng tùy chỉnh với bộ lọc nhiễu và bộ nhớ đệm

**Lý do**:
- Đơn giản và nhẹ, không cần thư viện bên ngoài lớn
- Tiêu thụ ít pin hơn so với giải pháp ML
- Đủ chính xác cho mục đích phát hiện va chạm mạnh
- Cho phép người dùng tùy chỉnh độ nhạy
- Dễ dàng duy trì và cải thiện mà không phụ thuộc vào thư viện bên ngoài

## 6. Định Vị Địa Lý

### Quyết định: Sử dụng Google Play Services Location API

**Ngày**: Khi thiết kế module Thông Báo Khẩn Cấp

**Bối cảnh**: Cần chọn API để xác định vị trí người dùng chính xác trong trường hợp khẩn cấp

**Lựa chọn đã xem xét**:
- Location Manager API (Android native)
- Google Play Services Location API
- Thư viện bên thứ ba (như LocationIQ)

**Quyết định**: Google Play Services Location API (FusedLocationProviderClient)

**Lý do**:
- Cung cấp vị trí chính xác hơn thông qua sự kết hợp của GPS, Wi-Fi, và mạng di động
- Tiết kiệm pin hơn so với LocationManager thuần túy
- Xử lý tự động các trường hợp GPS không hoạt động
- Đã được cài đặt trên hầu hết các thiết bị Android
- Cung cấp cơ chế geofencing và activity recognition nếu cần mở rộng

## 7. Phân Tích Âm Thanh

### Quyết định: Sử dụng Fast Fourier Transform (FFT) tùy chỉnh

**Ngày**: Khi thiết kế module Hiệu Ứng Âm Nhạc

**Bối cảnh**: Cần chọn phương pháp để phân tích âm thanh trong thời gian thực

**Lựa chọn đã xem xét**:
- Thư viện TarsosDSP
- FFT từ Apache Commons Math
- Triển khai FFT tùy chỉnh
- OpenSL ES

**Quyết định**: Sử dụng thư viện com.github.paramsen:noise kết hợp với thuật toán phát hiện nhịp tùy chỉnh

**Lý do**:
- Thư viện noise cung cấp FFT hiệu quả được tối ưu hóa bằng C++ thông qua JNI
- Ít phụ thuộc bên ngoài hơn TarsosDSP
- Hiệu suất cao hơn Apache Commons Math
- Độ trễ thấp phù hợp với yêu cầu nhấp nháy theo nhạc
- Cho phép tùy chỉnh thuật toán phát hiện nhịp dễ dàng hơn

## 8. Quản Lý Màn Hình

### Quyết định: Sử dụng Window Manager kết hợp với WakeLock

**Ngày**: Khi thiết kế module Đèn Màn Hình

**Bối cảnh**: Cần chọn phương pháp để duy trì màn hình sáng ở độ sáng tối đa

**Lựa chọn đã xem xét**:
- Activity với cờ KEEP_SCREEN_ON
- Window Manager với service
- WakeLock

**Quyết định**: Window Manager trong Foreground Service kết hợp với WakeLock

**Lý do**:
- Cho phép đèn màn hình hoạt động ngay cả khi màn hình bị khóa
- Giải phóng tài nguyên của Activity
- Hiệu suất tốt hơn và kiểm soát nhiều hơn đối với độ sáng màn hình
- Dễ dàng kết hợp với các tính năng đèn flash khác
- Xử lý tốt chế độ ngắt quãng và tiết kiệm pin

## 9. Monetization

### Quyết định: Sử dụng mô hình Freemium với quảng cáo và gói Premium

**Ngày**: Khi thiết kế mô hình kinh doanh

**Bối cảnh**: Cần chọn chiến lược monetization phù hợp

**Lựa chọn đã xem xét**:
- Ứng dụng trả phí
- Freemium (miễn phí + gói nâng cấp)
- Chỉ hiển thị quảng cáo
- Trả phí để xóa quảng cáo
- Mua hàng trong ứng dụng

**Quyết định**: Mô hình Freemium kết hợp quảng cáo và subscription

**Lý do**:
- Người dùng có thể trải nghiệm các tính năng cơ bản miễn phí
- Nhiều lựa chọn thu nhập: quảng cáo, gói đăng ký, mua trọn đời
- Có thể tự động tắt quảng cáo trong trường hợp khẩn cấp (tăng trải nghiệm người dùng)
- Người dùng có nhiều lựa chọn (gói hàng tháng, hàng năm hoặc trọn đời)
- Cho phép mở khóa từng tính năng riêng lẻ hoặc toàn bộ

## 10. Giao Diện Người Dùng

### Quyết định: Sử dụng Material Design 3 với các custom components

**Ngày**: Khi thiết kế UI

**Bối cảnh**: Cần chọn design system phù hợp

**Lựa chọn đã xem xét**:
- Material Design cơ bản
- Material Design 3 (Material You)
- Thiết kế tùy chỉnh hoàn toàn
- Kết hợp MaterialDesign với các elements của nhà sản xuất (Samsung, Xiaomi)

**Quyết định**: Material Design 3 với Dynamic Colors và một số thành phần tùy chỉnh

**Lý do**:
- Material Design 3 cung cấp giao diện hiện đại và quen thuộc với người dùng Android
- Hỗ trợ Dynamic Colors trên Android 12+ tạo trải nghiệm cá nhân hóa
- Đã tích hợp sẵn các tính năng accessibility
- Dễ dàng triển khai Dark Mode và Light Mode
- Cung cấp các thành phần UI đã được tối ưu hóa (buttons, cards, navigation)
- Custom components cho phép tạo ra sự khác biệt khi cần thiết

## 11. Đa Ngôn Ngữ

### Quyết định: Hỗ trợ 10 ngôn ngữ phổ biến với cấu trúc resources

**Ngày**: Khi thiết kế module I18n

**Bối cảnh**: Cần quyết định cách triển khai đa ngôn ngữ cho ứng dụng toàn cầu

**Lựa chọn đã xem xét**:
- Sử dụng chỉ ngôn ngữ Anh và Việt
- Sử dụng Android resources chuẩn (values-xx)
- Sử dụng thư viện bên ngoài (như i18next)
- Tải ngôn ngữ động từ server

**Quyết định**: Sử dụng Android Resources chuẩn với kết hợp custom LanguageManager

**Lý do**:
- Cơ chế Android Resources đã tối ưu hóa và được hỗ trợ tốt
- Không cần thêm thư viện bên ngoài, giảm kích thước APK
- Cho phép thay đổi ngôn ngữ trong thời gian chạy mà không cần khởi động lại ứng dụng
- Dễ dàng thêm ngôn ngữ mới trong tương lai
- LanguageManager cho phép ghi đè ngôn ngữ hệ thống khi cần thiết

## 12. Quản Lý Quyền

### Quyết định: Sử dụng thư viện Android Permissions

**Ngày**: Khi thiết kế xử lý quyền người dùng

**Bối cảnh**: Cần quản lý nhiều quyền khác nhau (camera, location, contacts, SMS) một cách hiệu quả

**Lựa chọn đã xem xét**:
- Xử lý quyền thủ công bằng ActivityCompat
- Google's EasyPermissions
- Permission Dispatcher
- ActivityResultContracts (API mới từ AndroidX)

**Quyết định**: Sử dụng ActivityResultContracts từ AndroidX

**Lý do**:
- Là giải pháp chính thức từ Google, được hỗ trợ lâu dài
- Tích hợp tốt với Fragments và Activities
- API mới hơn và sạch hơn so với các giải pháp khác
- Không yêu cầu thư viện bên ngoài
- Dễ dàng xử lý các trường hợp từ chối quyền và hiển thị giải thích
