# Module: SOS Thông Minh 🆘

## Tổng Quan
Module SOS Thông Minh giám sát các cảm biến gia tốc để phát hiện tác động mạnh và tự động kích hoạt tín hiệu SOS khi cần thiết. Tính năng này rất hữu ích trong các tình huống khẩn cấp hoặc tai nạn.

## Yêu Cầu Kỹ Thuật

### Quyền Hệ Thống
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-feature android:name="android.hardware.camera.flash" />
<uses-feature android:name="android.hardware.sensor.accelerometer" />
```

### Thành Phần Cần Triển Khai

#### 1. Dịch Vụ Giám Sát Cảm Biến (SensorMonitorService)
- Dịch vụ chạy ngầm để giám sát dữ liệu cảm biến gia tốc
- Thuật toán phát hiện va chạm/tác động mạnh
- Khả năng chạy nền với mức tiêu thụ pin tối thiểu
- Foreground service với thông báo không gây phiền hà

#### 2. Bộ Xử Lý Tín Hiệu SOS (SOSManager)
- Nhận và xử lý sự kiện từ dịch vụ giám sát
- Điều khiển đèn flash để phát tín hiệu SOS chuẩn (... --- ...)
- Phát âm thanh cảnh báo kèm theo
- Giao diện để kích hoạt/hủy SOS thủ công

#### 3. Bộ Cấu Hình SOS (SOSConfigManager)
- Lưu trữ và quản lý cài đặt độ nhạy cảm biến
- Cài đặt mẫu nhấp nháy SOS (tốc độ, kiểu)
- Quản lý cài đặt xác nhận trước khi gửi cảnh báo
- Lưu trữ lịch sử kích hoạt SOS

#### 4. Giao Diện Người Dùng
- Màn hình cấu hình độ nhạy cảm biến
- Nút kích hoạt SOS thủ công
- Giao diện xác nhận/hủy khi phát hiện tác động
- Màn hình hiển thị khi đang trong chế độ SOS

## Luồng Xử Lý

1. **Khởi động dịch vụ**:
   - Dịch vụ SensorMonitorService chạy nền
   - Đăng ký lắng nghe dữ liệu từ cảm biến gia tốc
   - Áp dụng cài đặt độ nhạy từ người dùng

2. **Phát hiện tác động**:
   - Dữ liệu cảm biến vượt ngưỡng cài đặt
   - Đưa ra thông báo xác nhận với đếm ngược
   - Nếu không có phản hồi hoặc xác nhận → kích hoạt SOS

3. **Kích hoạt SOS**:
   - Bật đèn flash theo mẫu SOS
   - Phát âm thanh cảnh báo
   - Gửi thông báo (nếu được cấu hình)
   - Hiển thị màn hình SOS với nút hủy

4. **Kết thúc SOS**:
   - Người dùng nhấn nút hủy
   - Hoặc hết thời gian định trước
   - Ghi lại sự kiện vào lịch sử

## Thuật Toán Phát Hiện Va Chạm

```java
// Thuật toán mẫu để phát hiện tác động mạnh
public class ImpactDetector implements SensorEventListener {
    private static final float IMPACT_THRESHOLD = 15.0f; // Có thể điều chỉnh
    private static final int TIME_THRESHOLD = 100; // ms
    
    private float lastX, lastY, lastZ;
    private long lastUpdate;
    private SOSManager sosManager;
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastUpdate) > TIME_THRESHOLD) {
                float deltaX = Math.abs(lastX - x);
                float deltaY = Math.abs(lastY - y);
                float deltaZ = Math.abs(lastZ - z);
                
                // Tính toán vector gia tốc tổng hợp
                float deltaAccel = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ);
                
                if (deltaAccel > IMPACT_THRESHOLD) {
                    // Phát hiện tác động mạnh
                    sosManager.potentialImpactDetected();
                }
                
                lastX = x;
                lastY = y;
                lastZ = z;
                lastUpdate = currentTime;
            }
        }
    }
}
```

## Mẫu Nhấp Nháy SOS

```java
// Mẫu nhấp nháy đèn flash theo tín hiệu SOS chuẩn
private void playSosPattern() {
    // Morse code: ... --- ...
    int[] pattern = {
        // Chữ S: 3 nháy ngắn
        200, 200, 200, 200, 200, 200,
        // Khoảng trống giữa chữ
        500,
        // Chữ O: 3 nháy dài
        500, 200, 500, 200, 500, 200,
        // Khoảng trống giữa chữ
        500,
        // Chữ S: 3 nháy ngắn
        200, 200, 200, 200, 200, 200,
        // Khoảng nghỉ dài
        1000
    };
    
    // Lặp mẫu
    for (int i = 0; i < pattern.length; i += 2) {
        flashLight.setFlashOn(true);
        try {
            Thread.sleep(pattern[i]);
            flashLight.setFlashOn(false);
            Thread.sleep(pattern[i+1]);
        } catch (InterruptedException e) {
            break;
        }
    }
}
```

## Tối Ưu Hóa

1. **Tiêu Thụ Pin**:
   - Giảm tần suất lấy mẫu cảm biến khi pin yếu
   - Sử dụng batch processing cho dữ liệu cảm biến
   - Tự động điều chỉnh độ nhạy cảm biến dựa trên hoạt động

2. **Độ Chính Xác**:
   - Thu thập dữ liệu về các va chạm thực/giả để cải thiện thuật toán
   - Kết hợp nhiều cảm biến (gia tốc, gyro) nếu có thể
   - Xử lý nhiễu và bộ lọc dữ liệu

3. **Giảm Báo Động Giả**:
   - Phân tích mẫu chuyển động để phân biệt va chạm thật/giả
   - Cơ chế xác nhận đa cấp
   - Học thói quen sử dụng của người dùng

## Kiểm Thử

1. **Độ Nhạy Cảm Biến**:
   - Kiểm tra với các mức độ nhạy khác nhau
   - Kiểm tra trên nhiều thiết bị khác nhau
   - Kiểm tra trong nhiều điều kiện (đang đi bộ, đi xe)

2. **Kịch Bản Kiểm Thử**:
   - Thử nghiệm với tác động mạnh giả lập
   - Kiểm tra tác động nhẹ (không nên kích hoạt)
   - Kiểm tra xác nhận và hủy bỏ cảnh báo
   - Kiểm tra tiêu thụ pin

## Giao Diện

1. **Màn Hình Cấu Hình**:
   - Thanh trượt điều chỉnh độ nhạy
   - Tùy chọn mẫu nhấp nháy SOS
   - Tùy chọn âm thanh cảnh báo
   - Thời gian xác nhận trước khi kích hoạt

2. **Màn Hình SOS**:
   - Nền đỏ nổi bật
   - Nút hủy lớn, dễ nhấn
   - Đồng hồ đếm thời gian đã kích hoạt
   - Thông báo tình trạng

## Hoàn Thành

Tính năng được coi là hoàn thành khi:
- Phát hiện chính xác các tác động mạnh
- Kích hoạt tín hiệu SOS đúng chuẩn quốc tế
- Tỷ lệ báo động giả thấp
- Tối ưu hóa sử dụng pin
- Giao diện người dùng rõ ràng, dễ sử dụng trong trường hợp khẩn cấp 