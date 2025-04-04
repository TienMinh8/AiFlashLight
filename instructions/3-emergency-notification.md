# Module: Thông Báo Khẩn Cấp 📲

## Tổng Quan
Module Thông Báo Khẩn Cấp cho phép ứng dụng gửi tin nhắn cứu hộ tự động kèm thông tin vị trí GPS khi chế độ SOS được kích hoạt. Tính năng này đặc biệt quan trọng trong tình huống khẩn cấp khi người dùng không thể tự liên lạc.

## Yêu Cầu Kỹ Thuật

### Quyền Hệ Thống
```xml
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### Thành Phần Cần Triển Khai

#### 1. Quản Lý Liên Hệ Khẩn Cấp (EmergencyContactManager)
- Lưu trữ và quản lý danh sách liên hệ khẩn cấp
- Tích hợp với danh bạ điện thoại
- Xác thực số điện thoại hợp lệ
- Khả năng nhóm và phân loại liên hệ

#### 2. Dịch Vụ Định Vị (LocationService)
- Cung cấp vị trí GPS chính xác và nhanh nhất có thể
- Phương án dự phòng khi không có GPS (mạng di động, WiFi)
- Định dạng vị trí thành địa chỉ đường phố khi có thể
- Tối ưu hóa pin khi theo dõi vị trí

#### 3. Hệ Thống Tin Nhắn Khẩn Cấp (EmergencyMessenger)
- Gửi SMS tự động đến danh sách liên hệ khẩn cấp
- Hỗ trợ các phương thức liên lạc thay thế (email, ứng dụng nhắn tin)
- Cơ chế thử lại khi gửi thất bại
- Xác nhận khi tin nhắn đã được gửi thành công

#### 4. Quản Lý Mẫu Tin Nhắn (MessageTemplateManager)
- Lưu trữ các mẫu tin nhắn dựng sẵn
- Hỗ trợ đa ngôn ngữ (tiếng Việt và tiếng Anh)
- Thay thế biến động (vị trí, thời gian, loại khẩn cấp)
- Cho phép người dùng tùy chỉnh mẫu tin nhắn

#### 5. Giao Diện Người Dùng
- Màn hình quản lý liên hệ khẩn cấp
- Giao diện cài đặt mẫu tin nhắn
- Màn hình xem lịch sử thông báo đã gửi
- Tùy chọn kiểm tra/gửi thử tin nhắn

## Luồng Xử Lý

1. **Thiết lập ban đầu**:
   - Người dùng cấu hình danh sách liên hệ khẩn cấp
   - Người dùng tùy chỉnh mẫu tin nhắn
   - Cấp quyền cần thiết (SMS, vị trí, danh bạ)

2. **Kích hoạt thông báo khẩn cấp**:
   - Nhận tín hiệu kích hoạt từ module SOS
   - Bắt đầu xác định vị trí chính xác
   - Chuẩn bị tin nhắn với dữ liệu mới nhất

3. **Gửi thông báo**:
   - Gửi tin nhắn đến tất cả liên hệ khẩn cấp
   - Theo dõi trạng thái gửi
   - Thử lại các tin nhắn thất bại

4. **Theo dõi và cập nhật**:
   - Tiếp tục cập nhật vị trí theo định kỳ
   - Gửi cập nhật mới nếu được cấu hình
   - Lưu trữ lịch sử thông báo đã gửi

## Mẫu Tin Nhắn SOS

```
KHẨN CẤP: [Tên người dùng] cần giúp đỡ! 
Vị trí hiện tại: [Tọa độ GPS] 
Địa chỉ gần nhất: [Địa chỉ] 
Thời gian: [Ngày giờ]
Trạng thái pin: [Phần trăm]
Truy cập bản đồ: [Link Google Maps]
```

## Xử Lý Định Vị

```java
public class LocationHandler {
    private static final long UPDATE_INTERVAL = 10000; // 10 seconds
    private static final long FASTEST_INTERVAL = 5000; // 5 seconds
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private Location lastLocation;
    
    // Khởi tạo và yêu cầu cập nhật vị trí
    public void startLocationUpdates(Context context, final LocationListener listener) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        
        LocationRequest locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL);
            
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                
                for (Location location : locationResult.getLocations()) {
                    if (isBetterLocation(location, lastLocation)) {
                        lastLocation = location;
                        listener.onLocationChanged(location);
                    }
                }
            }
        };
        
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper());
        }
    }
    
    // Thuật toán xác định vị trí tốt hơn
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            return true;
        }
        
        // Kiểm tra độ cũ của vị trí
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;
        
        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
            return false;
        }
        
        // Kiểm tra độ chính xác
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;
        
        // Kiểm tra cùng nhà cung cấp vị trí
        boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());
        
        // Xác định vị trí tốt hơn dựa trên độ chính xác và thời gian
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        
        return false;
    }
    
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
```

## Tối Ưu Hóa

1. **Tiêu Thụ Pin và Dữ Liệu**:
   - Điều chỉnh tần suất cập nhật vị trí dựa trên mức pin
   - Kiểm tra kết nối mạng trước khi gửi tin nhắn
   - Giảm độ chính xác vị trí khi pin thấp

2. **Độ Tin Cậy**:
   - Sử dụng nhiều phương thức gửi tin nhắn dự phòng
   - Lưu trữ cục bộ tin nhắn đang chờ gửi
   - Xử lý các tình huống không có sóng/mạng

3. **Bảo Mật**:
   - Mã hóa dữ liệu liên hệ khẩn cấp
   - Chỉ lưu thông tin cần thiết
   - Cho phép xác thực trước khi gửi tin nhắn

## Kiểm Thử

1. **Độ Chính Xác Vị Trí**:
   - Kiểm tra trong các môi trường khác nhau (trong nhà, ngoài trời)
   - Kiểm tra với GPS tắt, chỉ dùng mạng
   - Kiểm tra độ trễ xác định vị trí

2. **Gửi Tin Nhắn**:
   - Kiểm tra gửi đến nhiều liên hệ
   - Kiểm tra với thiết bị không có SIM
   - Kiểm tra với tín hiệu yếu
   - Kiểm tra cơ chế thử lại

3. **Tích Hợp Hệ Thống**:
   - Kiểm tra kết nối với module SOS
   - Kiểm tra hoạt động ở nền
   - Kiểm tra sau khi khởi động lại thiết bị

## Giao Diện

1. **Màn Hình Quản Lý Liên Hệ**:
   - Danh sách liên hệ khẩn cấp với avatar và thông tin
   - Nút thêm/xóa/sửa liên hệ
   - Tùy chọn nhập từ danh bạ hoặc thủ công
   - Tùy chọn nhóm liên hệ theo mức độ ưu tiên

2. **Màn Hình Cài Đặt Tin Nhắn**:
   - Trình soạn thảo mẫu tin nhắn
   - Danh sách biến động có thể thêm vào tin nhắn
   - Tùy chọn ngôn ngữ
   - Nút gửi tin nhắn thử nghiệm

## Hoàn Thành

Tính năng được coi là hoàn thành khi:
- Gửi tin nhắn thành công đến tất cả liên hệ khẩn cấp
- Vị trí được xác định chính xác và kịp thời
- Hoạt động đáng tin cậy trong các điều kiện khác nhau
- Giao diện người dùng dễ sử dụng và rõ ràng
- Tối ưu hóa sử dụng pin và dữ liệu 