# Module: Đèn Màn Hình 📱💡

## Tổng Quan
Module Đèn Màn Hình cung cấp nguồn sáng thay thế hoặc bổ sung cho đèn flash bằng cách sử dụng màn hình điện thoại. Tính năng này đặc biệt hữu ích khi đèn flash không hoạt động, không đủ sáng, hoặc khi cần ánh sáng với nhiều tùy chọn màu sắc và cường độ.

## Yêu Cầu Kỹ Thuật

### Quyền Hệ Thống
```xml
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" /> <!-- Cho phép hiển thị trên màn hình khóa -->
```

### Thành Phần Cần Triển Khai

#### 1. Quản Lý Màn Hình (ScreenLightManager)
- Điều khiển độ sáng màn hình (tối đa, tùy chỉnh)
- Giữ màn hình luôn bật khi đang sử dụng
- Xử lý hiển thị trên màn hình khóa
- Thay đổi độ sáng một cách mượt mà

#### 2. Máy Phát Ánh Sáng (LightEmitterView)
- View tùy chỉnh để hiển thị màu sắc đầy đủ màn hình
- Điều khiển nhiệt độ màu (từ trắng ấm đến trắng lạnh)
- Hỗ trợ các hiệu ứng chuyển màu
- Tối ưu hóa hiệu suất render

#### 3. Cảm Biến Ánh Sáng (LightSensorManager)
- Phát hiện ánh sáng môi trường xung quanh
- Tự động điều chỉnh độ sáng màn hình nếu cần
- Bảo vệ mắt trong môi trường tối
- Tối ưu hóa tiêu thụ pin

#### 4. Quản Lý Nhiệt Độ (ThermalMonitor)
- Theo dõi nhiệt độ thiết bị khi màn hình sáng tối đa
- Điều chỉnh độ sáng để tránh quá nhiệt
- Cảnh báo người dùng khi nhiệt độ cao
- Đảm bảo an toàn cho thiết bị

#### 5. Giao Diện Người Dùng
- Màn hình chính với nút bật/tắt và điều khiển độ sáng
- Bảng chọn màu và nhiệt độ màu
- Các cài đặt và tùy chọn tiết kiệm pin
- Hiệu ứng chuyển tiếp mượt mà giữa các trạng thái

## Luồng Xử Lý

1. **Khởi động tính năng**:
   - Kiểm tra khả năng điều khiển độ sáng
   - Lưu trữ độ sáng hiện tại để khôi phục sau này
   - Khởi tạo WakeLock để giữ màn hình bật
   - Hiển thị giao diện đèn màn hình

2. **Vận hành**:
   - Người dùng có thể điều chỉnh độ sáng bằng thanh trượt
   - Chọn nhiệt độ màu từ lạnh đến ấm
   - Bật/tắt chế độ tự động dựa trên cảm biến ánh sáng
   - Thiết lập hẹn giờ tắt tự động

3. **Kết hợp với đèn flash**:
   - Tùy chọn sử dụng cả đèn flash và màn hình
   - Đồng bộ hóa trạng thái giữa cả hai nguồn sáng
   - Tối ưu hóa hiệu suất và tiêu thụ pin

4. **Kết thúc**:
   - Người dùng tắt đèn màn hình
   - Khôi phục độ sáng màn hình về trạng thái trước
   - Giải phóng WakeLock
   - Lưu trữ cài đặt cho lần sử dụng tiếp theo

## Mã Nguồn Chính

### Quản Lý Độ Sáng

```java
public class BrightnessController {
    private static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00001000;
    private static final int BRIGHTNESS_MAX = 255;
    
    private final Context context;
    private final ContentResolver contentResolver;
    private int originalBrightness;
    private boolean isAutoBrightness;
    private PowerManager.WakeLock wakeLock;
    
    public BrightnessController(Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
        
        // Lưu trữ độ sáng ban đầu
        try {
            originalBrightness = Settings.System.getInt(
                contentResolver, Settings.System.SCREEN_BRIGHTNESS);
            isAutoBrightness = Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE) == 
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            originalBrightness = 125; // Giá trị mặc định nếu không lấy được
            isAutoBrightness = false;
        }
        
        // Khởi tạo WakeLock
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "AiFlashLight:ScreenLight");
    }
    
    // Bật độ sáng tối đa
    public void setMaxBrightness(Activity activity) {
        if (wakeLock != null && !wakeLock.isHeld()) {
            wakeLock.acquire();
        }
        
        // Tắt chế độ tự động điều chỉnh độ sáng
        if (isAutoBrightness) {
            Settings.System.putInt(contentResolver, 
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }
        
        // Thiết lập độ sáng tối đa
        Settings.System.putInt(contentResolver, 
            Settings.System.SCREEN_BRIGHTNESS, BRIGHTNESS_MAX);
            
        // Áp dụng cài đặt cho cửa sổ hiện tại
        WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
        layoutParams.screenBrightness = 1F; // 1.0 là tối đa
        activity.getWindow().setAttributes(layoutParams);
        
        // Toàn màn hình và immersive mode để tối đa hóa ánh sáng
        activity.getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    
    // Khôi phục độ sáng ban đầu
    public void restoreOriginalBrightness(Activity activity) {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
        
        // Khôi phục chế độ tự động nếu trước đó đang bật
        if (isAutoBrightness) {
            Settings.System.putInt(contentResolver, 
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        } else {
            // Khôi phục độ sáng cũ
            Settings.System.putInt(contentResolver, 
                Settings.System.SCREEN_BRIGHTNESS, originalBrightness);
        }
        
        // Khôi phục cài đặt cửa sổ
        WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
        layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        activity.getWindow().setAttributes(layoutParams);
        
        // Khôi phục giao diện hệ thống
        activity.getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_VISIBLE);
    }
}
```

### Quản Lý Nhiệt Độ Màu

```java
public class ColorTemperatureView extends View {
    private static final int DEFAULT_COLOR_TEMP = 5000; // Kelvin
    private static final int MIN_COLOR_TEMP = 2500; // Warm white
    private static final int MAX_COLOR_TEMP = 9000; // Cool white
    
    private Paint paint;
    private int currentColorTemp;
    
    public ColorTemperatureView(Context context) {
        super(context);
        init();
    }
    
    // Constructor với attrs và defStyle
    
    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        currentColorTemp = DEFAULT_COLOR_TEMP;
        updateBackgroundColor();
    }
    
    public void setColorTemperature(int temperature) {
        currentColorTemp = Math.max(MIN_COLOR_TEMP, 
                          Math.min(MAX_COLOR_TEMP, temperature));
        updateBackgroundColor();
        invalidate();
    }
    
    private void updateBackgroundColor() {
        int rgb = colorTemperatureToRGB(currentColorTemp);
        paint.setColor(rgb);
    }
    
    // Thuật toán chuyển đổi nhiệt độ màu (Kelvin) sang RGB
    private int colorTemperatureToRGB(int colorTemp) {
        // Các phương trình gần đúng dựa trên thuật toán của Tanner Helland
        double temperature = colorTemp / 100.0;
        double red, green, blue;
        
        // Tính toán thành phần đỏ
        if (temperature <= 66) {
            red = 255;
        } else {
            red = temperature - 60;
            red = 329.698727446 * Math.pow(red, -0.1332047592);
            red = Math.max(0, Math.min(255, red));
        }
        
        // Tính toán thành phần xanh lá
        if (temperature <= 66) {
            green = temperature;
            green = 99.4708025861 * Math.log(green) - 161.1195681661;
        } else {
            green = temperature - 60;
            green = 288.1221695283 * Math.pow(green, -0.0755148492);
        }
        green = Math.max(0, Math.min(255, green));
        
        // Tính toán thành phần xanh dương
        if (temperature >= 66) {
            blue = 255;
        } else if (temperature <= 19) {
            blue = 0;
        } else {
            blue = temperature - 10;
            blue = 138.5177312231 * Math.log(blue) - 305.0447927307;
            blue = Math.max(0, Math.min(255, blue));
        }
        
        return Color.rgb((int)red, (int)green, (int)blue);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
}
```

## Tối Ưu Hóa

1. **Tiêu Thụ Pin**:
   - Hẹn giờ tắt tự động sau thời gian không sử dụng
   - Điều chỉnh độ sáng dựa vào mức pin còn lại
   - Cảnh báo người dùng khi pin thấp
   - Chế độ tiết kiệm pin (chỉ sáng một phần màn hình)

2. **Nhiệt Độ Thiết Bị**:
   - Giám sát nhiệt độ thiết bị trong thời gian thực
   - Tự động giảm độ sáng khi nhiệt độ quá cao
   - Thông báo khi thiết bị quá nóng
   - Tự động tắt khi đạt ngưỡng nhiệt độ nguy hiểm

3. **Trải Nghiệm Người Dùng**:
   - Giao diện điều khiển dễ sử dụng trong bóng tối
   - Chế độ ban đêm cho màn hình điều khiển
   - Cử chỉ đơn giản để điều chỉnh độ sáng
   - Khả năng truy cập nhanh từ màn hình khóa

## Kiểm Thử

1. **Độ Sáng và Hiển Thị**:
   - Kiểm tra trong môi trường tối hoàn toàn
   - Kiểm tra với nhiều mức độ sáng khác nhau
   - So sánh với đèn flash về cường độ và phân bố ánh sáng
   - Kiểm tra nhiệt độ màu và độ chính xác màu sắc

2. **Hiệu Suất Hệ Thống**:
   - Theo dõi nhiệt độ thiết bị trong thời gian dài
   - Đo lường mức tiêu thụ pin
   - Kiểm tra ảnh hưởng đến các ứng dụng khác
   - Kiểm tra trên nhiều thiết bị với kích thước màn hình khác nhau

3. **Trải Nghiệm Người Dùng**:
   - Kiểm tra khả năng sử dụng trong điều kiện khẩn cấp
   - Đánh giá độ trễ khi bật/tắt
   - Kiểm tra khả năng hoạt động trên màn hình khóa
   - Kiểm tra kết hợp với đèn flash

## Giao Diện

1. **Màn Hình Đèn**:
   - Nền trắng đơn giản chiếm toàn màn hình
   - Điều khiển hiển thị/ẩn khi chạm vào màn hình
   - Chỉ báo độ sáng và nhiệt độ màu hiện tại
   - Chế độ toàn màn hình tối đa

2. **Bảng Điều Khiển**:
   - Thanh trượt điều chỉnh độ sáng
   - Thanh chọn nhiệt độ màu
   - Nút bật/tắt đèn flash kết hợp
   - Nút cài đặt hẹn giờ tắt

3. **Chế Độ Tiết Kiệm Pin**:
   - Tùy chọn chỉ sáng một phần màn hình
   - Mẫu ánh sáng tối ưu (ví dụ: mẫu xen kẽ)
   - Chế độ nhấp nháy tiết kiệm

## Hoàn Thành

Tính năng được coi là hoàn thành khi:
- Hiển thị ánh sáng trắng đồng đều trên màn hình với độ sáng tối đa
- Điều chỉnh nhiệt độ màu mượt mà từ trắng ấm đến trắng lạnh
- Hoạt động ổn định mà không gây quá nhiệt thiết bị
- Tối ưu hóa tiêu thụ pin
- Kết hợp hiệu quả với đèn flash
- Giao diện người dùng trực quan, dễ sử dụng ngay cả trong bóng tối 