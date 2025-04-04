# Instruction: AiFlashLight - Ứng Dụng Đèn Pin Thông Minh 📱💡

## Tổng Quan
AiFlashLight là ứng dụng đèn pin thông minh tích hợp các tính năng AI và cảm biến, mang đến trải nghiệm đèn pin vượt xa ứng dụng đèn pin thông thường. Ứng dụng tận dụng phần cứng điện thoại (đèn flash, cảm biến gia tốc, màn hình) kết hợp với các thuật toán thông minh để mang lại nhiều tính năng hữu ích trong đời sống hàng ngày và các tình huống khẩn cấp.

## Mục Tiêu
- Xây dựng ứng dụng đèn pin đa năng với UI/UX hiện đại
- Tích hợp các tính năng AI và cảm biến thông minh
- Hỗ trợ đa ngôn ngữ và tùy biến cao
- Tạo ra giải pháp an toàn trong các tình huống khẩn cấp
- Triển khai mô hình kinh doanh bền vững

## Hướng Dẫn Triển Khai

Dự án được chia thành các module riêng biệt, mỗi module có hướng dẫn chi tiết trong file riêng:

### Core Modules
1. [Basic Flashlight](instructions/1-flash-basic.md) ✅ - Đèn pin cơ bản với các chức năng điều khiển
2. [SOS Thông Minh](instructions/2-sos-smart.md) ✅ - Phát hiện va chạm và kích hoạt tín hiệu SOS
3. [Thông Báo Khẩn Cấp](instructions/3-emergency-notification.md) ✅ - Gửi tin nhắn cầu cứu tự động với vị trí GPS
4. [Đèn Màn Hình](instructions/4-screen-light.md) ✅ - Sử dụng màn hình làm nguồn sáng thay thế
5. [Hiệu Ứng Âm Nhạc](instructions/5-music-effect.md) ✅ - Đèn flash nhấp nháy theo nhạc

### Support Modules
6. [Monetization](instructions/6-monetization.md) ✅ - Hệ thống quảng cáo và gói premium
7. [Material Design UI](instructions/7-material-design-ui.md) ✅ - Giao diện người dùng theo hướng dẫn Material Design
8. [I18n (Đa Ngôn Ngữ)](instructions/8-i18n-module.md) ✅ - Hệ thống hỗ trợ đa ngôn ngữ

## Cấu Trúc Project

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/aiflashlight/
│   │   │   ├── activities/         # Các màn hình chính
│   │   │   ├── fragments/          # Các fragment UI
│   │   │   ├── services/           # Background services
│   │   │   ├── managers/           # Các lớp quản lý tính năng
│   │   │   ├── models/             # Data models
│   │   │   ├── utils/              # Tiện ích và helper
│   │   │   ├── views/              # Custom views
│   │   │   ├── i18n/               # Đa ngôn ngữ
│   │   │   └── monetization/       # Quảng cáo và premium
│   │   ├── res/                    # Resources (layouts, strings, drawables...)
│   │   └── AndroidManifest.xml     # Manifests
│   └── test/                       # Unit tests
├── build.gradle                    # Project build file
└── README.md                       # Project documentation
```

## Yêu Cầu Kỹ Thuật

### Phiên Bản Android
- Min SDK: 23 (Android 6.0)
- Target SDK: 33 (Android 13)

### Phụ Thuộc Chính
```gradle
dependencies {
    // Android core
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.core:core-ktx:1.10.1'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
    
    // UI
    implementation 'com.google.android.material:material:1.8.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // Sensors and hardware
    implementation 'androidx.camera:camera-camera2:1.2.3'
    implementation 'androidx.camera:camera-lifecycle:1.2.3'
    
    // Location
    implementation 'com.google.android.gms:play-services-location:21.0.1'
    
    // Ads & Billing
    implementation 'com.google.android.gms:play-services-ads:22.2.0'
    implementation 'com.android.billingclient:billing:6.0.1'
    
    // Audio processing
    implementation 'com.github.paramsen:noise:2.0.0'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
}
```

### Quyền Hệ Thống
```xml
<!-- Camera access for flashlight -->
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera.flash" android:required="false" />

<!-- Sensor permissions -->
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.hardware.sensor.accelerometer" android:required="true" />

<!-- Location for emergency messages -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<!-- SMS for emergency notifications -->
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.READ_CONTACTS" />

<!-- For keeping screen on -->
<uses-permission android:name="android.permission.WAKE_LOCK" />

<!-- For audio analysis -->
<uses-permission android:name="android.permission.RECORD_AUDIO" />

<!-- Internet for ads and premium features -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## Luồng Hoạt Động

### Khởi Động Ứng Dụng
1. Hiển thị splash screen với logo và tên ứng dụng
2. Kiểm tra quyền cần thiết (camera, vị trí, v.v.)
3. Kiểm tra phần cứng thiết bị (đèn flash, cảm biến gia tốc)
4. Chuyển đến màn hình chính với đèn pin

### Luồng Chính
1. **Đèn Pin Cơ Bản**: Người dùng có thể bật/tắt đèn pin, điều chỉnh độ sáng
2. **Đèn Màn Hình**: Thay thế hoặc bổ sung cho đèn flash với nhiều tùy chọn màu sắc
3. **Hiệu Ứng Âm Nhạc**: Phân tích âm thanh và tạo hiệu ứng đèn flash theo nhịp nhạc
4. **SOS Thông Minh**: Giám sát cảm biến gia tốc để phát hiện va chạm và kích hoạt SOS
5. **Thông Báo Khẩn Cấp**: Gửi tin nhắn tự động với vị trí GPS khi SOS được kích hoạt

### Tích Hợp Module

Tất cả các module được tích hợp vào ứng dụng chính, nhưng được thiết kế để hoạt động độc lập:

1. Mỗi module có managers và services riêng
2. Tương tác giữa các module thông qua interface và listener
3. Dữ liệu được chia sẻ qua repository pattern
4. UI tích hợp thông qua bottom navigation và fragment containers

## Triển Khai Chi Tiết

Mỗi module có hướng dẫn triển khai chi tiết riêng. Tham khảo các file hướng dẫn cụ thể:

- [Basic Flashlight](instructions/1-flash-basic.md): Triển khai đèn pin cơ bản
- [SOS Thông Minh](instructions/2-sos-smart.md): Phát hiện va chạm và kích hoạt SOS
- [Thông Báo Khẩn Cấp](instructions/3-emergency-notification.md): Gửi tin nhắn cầu cứu
- [Đèn Màn Hình](instructions/4-screen-light.md): Sử dụng màn hình làm nguồn sáng
- [Hiệu Ứng Âm Nhạc](instructions/5-music-effect.md): Đèn flash nhấp nháy theo nhạc
- [Monetization](instructions/6-monetization.md): Quảng cáo và gói premium
- [Material Design UI](instructions/7-material-design-ui.md): Triển khai giao diện
- [I18n Module](instructions/8-i18n-module.md): Hỗ trợ đa ngôn ngữ

## Ưu Tiên Triển Khai

Thứ tự triển khai đề xuất:

1. **Giai Đoạn 1**: Thiết lập project và cấu trúc cơ bản
   - Cấu trúc project, manifest và gradle
   - Material Design UI framework
   - Đa ngôn ngữ (i18n) framework

2. **Giai Đoạn 2**: Các tính năng cốt lõi
   - Basic Flashlight
   - Đèn Màn Hình
   - SOS Thông Minh (tính năng thủ công)

3. **Giai Đoạn 3**: Tính năng nâng cao
   - SOS Thông Minh (phát hiện va chạm)
   - Thông Báo Khẩn Cấp
   - Hiệu Ứng Âm Nhạc

4. **Giai Đoạn 4**: Hoàn thiện và monetization
   - Tối ưu hóa UI/UX
   - Triển khai monetization
   - Kiểm thử và sửa lỗi

## Kiểm Thử

Mỗi module cần được kiểm thử kỹ lưỡng, bao gồm:

1. **Unit Tests**: Kiểm thử từng thành phần riêng lẻ
2. **Integration Tests**: Kiểm thử tương tác giữa các module
3. **UI Tests**: Kiểm thử giao diện người dùng và luồng hoạt động
4. **Device Tests**: Kiểm thử trên nhiều thiết bị và phiên bản Android
5. **Performance Tests**: Kiểm thử hiệu suất và tiêu thụ pin

## Phát Hành

Kế hoạch phát hành đề xuất:

1. **Alpha**: Phát hành nội bộ với các tính năng cơ bản
2. **Beta**: Phát hành cho nhóm người dùng thử nghiệm
3. **Production v1.0**: Phát hành chính thức với tất cả tính năng cốt lõi
4. **Updates**: Bổ sung tính năng và sửa lỗi theo phản hồi người dùng

## Kết Luận

AiFlashLight là ứng dụng đèn pin thông minh với nhiều tính năng sáng tạo. Bằng cách tuân theo hướng dẫn triển khai cho từng module, ứng dụng sẽ được phát triển có cấu trúc và hiệu quả. 

Tham khảo các file hướng dẫn cụ thể cho từng module để biết chi tiết triển khai.
