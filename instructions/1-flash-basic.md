# Module: Đèn Flash Cơ Bản 💡

## Tổng Quan
Module này xử lý tính năng cốt lõi của ứng dụng - bật/tắt đèn flash. Cần đảm bảo tính tương thích với nhiều thiết bị Android và thiết kế giao diện trực quan.

## Yêu Cầu Kỹ Thuật

### Quyền Hệ Thống
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.flash" />
```

### Kiểm Tra Khả Năng Đèn Flash
```java
// Code nên kiểm tra xem thiết bị có hỗ trợ đèn flash không
PackageManager packageManager = context.getPackageManager();
boolean hasFlash = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
```

### Thành Phần Cần Triển Khai

#### 1. Camera Manager
- Khởi tạo và quản lý kết nối với camera
- Điều khiển đèn flash (bật/tắt)
- Xử lý lỗi khi không thể truy cập camera

#### 2. Service Nền (FlashService)
- Duy trì quyền truy cập đèn flash ngay cả khi ứng dụng ở nền
- Quản lý trạng thái đèn flash giữa các phiên
- Tối ưu hóa sử dụng pin

#### 3. Giao Diện Người Dùng
- Màn hình chính với nút bật/tắt lớn ở trung tâm
- Phản hồi trực quan khi kích hoạt (đổi màu, hiệu ứng)
- Thông báo trạng thái (Đang bật, Đang tắt)
- Hiển thị thông báo nếu không có đèn flash

#### 4. Widget & Quick Tile
- Widget màn hình chính để bật/tắt nhanh
- Quick Tile trong thanh thông báo
- Đồng bộ trạng thái giữa widget, quick tile và ứng dụng

## Luồng Xử Lý

1. **Khởi động ứng dụng**:
   - Kiểm tra quyền camera
   - Kiểm tra khả năng đèn flash
   - Khởi tạo trạng thái dựa trên lần sử dụng cuối

2. **Kích hoạt đèn**:
   - Khi người dùng nhấn nút → Khởi tạo camera → Bật đèn flash
   - Ghi nhớ trạng thái → Cập nhật UI
   - Tương tự với tắt đèn

3. **Xử lý lỗi**:
   - Kiểm tra lỗi khi khởi tạo camera
   - Xử lý trường hợp camera bị ứng dụng khác sử dụng
   - Thông báo người dùng khi có lỗi

## Tối Ưu Hóa

1. **Hiệu Suất**:
   - Sử dụng lazy initialization cho camera
   - Tránh khởi tạo lại camera khi không cần thiết
   - Giải phóng tài nguyên camera khi không sử dụng

2. **Pin**:
   - Đóng kết nối camera khi không sử dụng
   - Theo dõi thời gian sử dụng và tự động tắt sau thời gian dài
   - Tùy chọn hẹn giờ tắt tự động

## Kiểm Thử

1. **Thiết Bị Cần Kiểm Tra**:
   - Các thiết bị Android phiên bản 6.0+
   - Thiết bị có và không có đèn flash
   - Thiết bị có nhiều camera

2. **Kịch Bản Kiểm Thử**:
   - Kiểm tra bật/tắt đèn flash
   - Kiểm tra widget và quick tile
   - Kiểm tra khi có ứng dụng khác sử dụng camera
   - Kiểm tra xử lý pin và hiệu suất

## Giao Diện

1. **Màn Hình Chính**:
   - Nút tròn lớn ở trung tâm với icon đèn pin
   - Màu nền thay đổi khi bật (sáng) và tắt (tối)
   - Hiệu ứng chuyển tiếp mượt mà

2. **Widget**:
   - Thiết kế tối giản, kích thước linh hoạt
   - Hiển thị trạng thái bằng màu sắc
   - Cập nhật trạng thái real-time

## Hoàn Thành

Tính năng được coi là hoàn thành khi:
- Đèn flash hoạt động chính xác trên các thiết bị khác nhau
- Widget và Quick Tile hoạt động đồng bộ
- Giao diện người dùng phản hồi nhanh và trực quan
- Đã xử lý tất cả trường hợp lỗi phổ biến 