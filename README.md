# AiFlashLight - Ứng Dụng Đèn Pin Thông Minh

AiFlashLight là ứng dụng đèn pin thông minh sử dụng AI và cảm biến để mang lại trải nghiệm đèn pin vượt trội so với các ứng dụng đèn pin thông thường.

## Tính Năng

- **Đèn Pin Thông Minh**: Bật/tắt đèn flash với điều khiển độ sáng (nếu thiết bị hỗ trợ)
- **Đèn Màn Hình**: Sử dụng màn hình điện thoại làm nguồn sáng với nhiều tùy chọn màu sắc
- **SOS Thông Minh**: Phát hiện va chạm tự động và gửi tín hiệu cứu hộ
- **Thông Báo Khẩn Cấp**: Gửi tin nhắn cầu cứu tự động với vị trí GPS
- **Hiệu Ứng Âm Nhạc**: Đèn flash nhấp nháy theo nhịp nhạc
- **Giao Diện Material Design**: UI/UX hiện đại, hỗ trợ chế độ tối/sáng
- **Đa Ngôn Ngữ**: Hỗ trợ nhiều ngôn ngữ, mặc định tiếng Anh

## Yêu Cầu Hệ Thống

- Android 6.0 (API 23) trở lên
- Thiết bị có đèn flash camera (khuyến nghị)
- Quyền truy cập camera, vị trí, SMS (tùy tính năng)

## Cài Đặt & Phát Triển

### Yêu Cầu

- Android Studio Hedgehog (2023.1.1) trở lên
- JDK 11 trở lên
- Gradle 7.2.1 trở lên

### Cài Đặt

1. Clone repository:
```
git clone https://github.com/yourusername/AiFlashLight.git
```

2. Mở project trong Android Studio

3. Sync Gradle và build project

4. Chạy trên thiết bị hoặc emulator (lưu ý: emulator có thể không hỗ trợ tính năng đèn flash)

## Cấu Trúc Dự Án

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
```

## Đóng Góp

Đóng góp cho dự án là rất được hoan nghênh. Để đóng góp:

1. Fork repository
2. Tạo branch mới (`git checkout -b feature/amazing-feature`)
3. Commit thay đổi (`git commit -m 'Add some amazing feature'`)
4. Push lên branch của bạn (`git push origin feature/amazing-feature`)
5. Mở Pull Request

## Giấy Phép

Distributed under the MIT License. See `LICENSE` for more information.

## Liên Hệ

- Email: support@aiflashlight.com
- Website: www.aiflashlight.com
