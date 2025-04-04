# Module: Giao Diện Material Design 🎨

## Tổng Quan
Module Giao Diện Material Design triển khai giao diện người dùng hiện đại, trực quan và nhất quán cho ứng dụng AiFlashLight. Tập trung vào trải nghiệm người dùng tối ưu, dễ tiếp cận, và thích ứng với đa dạng kích thước thiết bị Android.

## tham khảo giao diện 

## Yêu Cầu Kỹ Thuật

### Thư Viện & Phụ Thuộc
```gradle
dependencies {
    // Material Design
    implementation 'com.google.android.material:material:1.8.0'
    
    // ConstraintLayout cho bố cục linh hoạt
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // Thư viện chuyển động và animation
    implementation 'androidx.dynamicanimation:dynamicanimation:1.0.0'
    
    // ViewPager2 cho màn hình giới thiệu và chọn hiệu ứng
    implementation 'androidx.viewpager2:viewpager2:1.0.0'
}
```

### Thành Phần Cần Triển Khai

#### 1. Hệ Thống Theme
- Tạo theme chính với màu sắc nhất quán
- Hỗ trợ chế độ tối (Dark Mode) và chế độ sáng (Light Mode)
- Hệ thống màu sắc linh hoạt thích ứng với cài đặt người dùng
- Kiểu chữ và kích thước phù hợp với các hướng dẫn Material Design

#### 2. Bố Cục Chính
- Sử dụng ConstraintLayout cho bố cục linh hoạt
- Tối ưu hóa cho thao tác một tay (phần tử tương tác chính gần phía dưới)
- Quy tắc tỷ lệ và khoảng cách nhất quán
- Bố cục thích ứng với kích thước màn hình và hướng thiết bị

#### 3. Thành Phần Điều Khiển
- Nút và điều khiển tuân theo hướng dẫn Material Design
- Thanh trượt (sliders) và nút điều chỉnh (toggles) tùy chỉnh
- Bộ chọn màu và hiệu ứng cho đèn màn hình
- Animation và phản hồi khi tương tác

#### 4. Điều Hướng
- Bottom Navigation cho điều hướng chính
- Đảm bảo khả năng tiếp cận và thao tác dễ dàng
- Chuyển cảnh mượt mà giữa các màn hình
- Tối ưu hóa luồng người dùng

#### 5. Giao Diện Các Màn Hình Chính
- Màn hình chính với nút đèn flash lớn ở trung tâm
- Màn hình cài đặt với các phần nhóm logic
- Màn hình hiệu ứng với giao diện chọn và tùy chỉnh
- Màn hình SOS với thiết kế nhấn mạnh tính khẩn cấp

## Quy Tắc Thiết Kế

### Bảng Màu
```xml
<!-- Màu chính -->
<color name="primary">#2196F3</color>            <!-- Blue 500 -->
<color name="primary_dark">#1976D2</color>       <!-- Blue 700 -->
<color name="primary_light">#BBDEFB</color>      <!-- Blue 100 -->
<color name="accent">#FF5722</color>             <!-- Orange -->

<!-- Màu chức năng -->
<color name="success">#4CAF50</color>            <!-- Green -->
<color name="warning">#FFC107</color>            <!-- Amber -->
<color name="error">#F44336</color>              <!-- Red -->
<color name="emergency">#D32F2F</color>          <!-- Red 700 -->

<!-- Màu nền -->
<color name="background_light">#FAFAFA</color>   <!-- Grey 50 -->
<color name="background_dark">#212121</color>    <!-- Grey 900 -->
<color name="surface_light">#FFFFFF</color>      <!-- White -->
<color name="surface_dark">#303030</color>       <!-- Grey 850 -->

<!-- Màu văn bản -->
<color name="text_primary_light">#DE000000</color>   <!-- Black 87% -->
<color name="text_secondary_light">#8A000000</color> <!-- Black 54% -->
<color name="text_primary_dark">#DEFFFFFF</color>    <!-- White 87% -->
<color name="text_secondary_dark">#8AFFFFFF</color>  <!-- White 54% -->
```

### Typography
```xml
<!-- Kiểu chữ -->
<style name="TextAppearance.AiFlashLight.Headline1" parent="TextAppearance.MaterialComponents.Headline1">
    <item name="fontFamily">@font/roboto</item>
    <item name="android:fontFamily">@font/roboto</item>
    <item name="android:textSize">96sp</item>
</style>

<style name="TextAppearance.AiFlashLight.Headline2" parent="TextAppearance.MaterialComponents.Headline2">
    <item name="fontFamily">@font/roboto</item>
    <item name="android:fontFamily">@font/roboto</item>
    <item name="android:textSize">60sp</item>
</style>

<!-- Các kiểu văn bản khác: Headline3-6, Subtitle1-2, Body1-2, Button, Caption, Overline -->
```

## Mã Nguồn Chính

### Thiết Lập Theme

```xml
<!-- themes.xml (Light Theme) -->
<style name="Theme.AiFlashLight" parent="Theme.MaterialComponents.Light.NoActionBar">
    <!-- Màu sắc chính -->
    <item name="colorPrimary">@color/primary</item>
    <item name="colorPrimaryDark">@color/primary_dark</item>
    <item name="colorPrimaryVariant">@color/primary_dark</item>
    <item name="colorOnPrimary">@color/white</item>
    <item name="colorSecondary">@color/accent</item>
    <item name="colorSecondaryVariant">@color/accent</item>
    <item name="colorOnSecondary">@color/white</item>
    
    <!-- Màu nền -->
    <item name="android:colorBackground">@color/background_light</item>
    <item name="colorSurface">@color/surface_light</item>
    <item name="colorOnBackground">@color/text_primary_light</item>
    <item name="colorOnSurface">@color/text_primary_light</item>
    
    <!-- Status bar và navigation bar -->
    <item name="android:statusBarColor">@color/primary</item>
    <item name="android:navigationBarColor">@color/background_light</item>
    <item name="android:windowLightNavigationBar">true</item>
    
    <!-- Kiểu chữ -->
    <item name="textAppearanceHeadline1">@style/TextAppearance.AiFlashLight.Headline1</item>
    <item name="textAppearanceHeadline2">@style/TextAppearance.AiFlashLight.Headline2</item>
    <!-- ... các kiểu chữ khác ... -->
    
    <!-- Shape và các thuộc tính khác -->
    <item name="shapeAppearanceSmallComponent">@style/ShapeAppearance.AiFlashLight.SmallComponent</item>
    <item name="shapeAppearanceMediumComponent">@style/ShapeAppearance.AiFlashLight.MediumComponent</item>
    <item name="shapeAppearanceLargeComponent">@style/ShapeAppearance.AiFlashLight.LargeComponent</item>
</style>

<!-- themes.xml (Dark Theme) -->
<style name="Theme.AiFlashLight.Dark" parent="Theme.MaterialComponents.DayNight.NoActionBar">
    <!-- Tương tự với Light Theme nhưng sử dụng các màu dark mode -->
    <item name="colorPrimary">@color/primary</item>
    <item name="android:colorBackground">@color/background_dark</item>
    <item name="colorSurface">@color/surface_dark</item>
    <item name="colorOnBackground">@color/text_primary_dark</item>
    <item name="colorOnSurface">@color/text_primary_dark</item>
    <!-- ... other attributes ... -->
</style>
```

### Nút Đèn Flash Chính

```xml
<!-- layout/flashlight_button.xml -->
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <com.google.android.material.card.MaterialCardView
        android:id="@+id/flashlight_button_container"
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:cardCornerRadius="100dp"
        app:cardElevation="12dp"
        app:strokeWidth="2dp"
        app:strokeColor="?attr/colorPrimary"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent">
        
        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            
            <ImageView
                android:id="@+id/flashlight_icon"
                android:layout_width="80dp"
                android:layout_height="80dp"
                android:src="@drawable/ic_flashlight"
                android:tint="?attr/colorOnSurface"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"/>
                
            <TextView
                android:id="@+id/flashlight_label"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/tap_to_toggle"
                android:textAppearance="?attr/textAppearanceCaption"
                android:layout_marginTop="8dp"
                app:layout_constraintTop_toBottomOf="@id/flashlight_icon"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"/>
                
        </androidx.constraintlayout.widget.ConstraintLayout>
    </com.google.android.material.card.MaterialCardView>
    
    <TextView
        android:id="@+id/flash_status"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/flash_off"
        android:textAppearance="?attr/textAppearanceSubtitle1"
        android:layout_marginTop="24dp"
        app:layout_constraintTop_toBottomOf="@id/flashlight_button_container"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>
        
</androidx.constraintlayout.widget.ConstraintLayout>
```

### Hiệu Ứng Nút

```java
public class FlashlightButton extends MaterialCardView {
    private static final float PRESSED_SCALE = 0.95f;
    private static final long ANIMATION_DURATION = 100; // ms
    
    private boolean isOn = false;
    private ImageView iconView;
    private TextView statusLabel;
    private FlashController flashController;
    
    // Constructor, init code...
    
    private void setupClickListener() {
        setOnClickListener(v -> {
            isOn = !isOn;
            animateButtonPress();
            updateButtonState();
            
            if (isOn) {
                flashController.turnOnFlash();
            } else {
                flashController.turnOffFlash();
            }
        });
    }
    
    private void animateButtonPress() {
        // Scale down animation
        animate()
            .scaleX(PRESSED_SCALE)
            .scaleY(PRESSED_SCALE)
            .setDuration(ANIMATION_DURATION)
            .setInterpolator(new FastOutSlowInInterpolator())
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // Scale up animation
                    animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(ANIMATION_DURATION)
                        .setInterpolator(new FastOutSlowInInterpolator())
                        .setListener(null);
                }
            })
            .start();
    }
    
    private void updateButtonState() {
        if (isOn) {
            setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary));
            iconView.setImageResource(R.drawable.ic_flashlight_on);
            iconView.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
            statusLabel.setText(R.string.flash_on);
        } else {
            setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.surface_light));
            iconView.setImageResource(R.drawable.ic_flashlight_off);
            iconView.setColorFilter(ContextCompat.getColor(getContext(), 
                    ThemeUtils.isNightMode(getContext()) ? R.color.text_primary_dark : R.color.text_primary_light));
            statusLabel.setText(R.string.flash_off);
        }
    }
}
```

### Bottom Navigation

```xml
<!-- layout/activity_main.xml -->
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:defaultNavHost="true"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        app:navGraph="@navigation/nav_graph" />
    
    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottom_navigation"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"
        app:menu="@menu/bottom_nav_menu"
        app:labelVisibilityMode="labeled"
        android:background="?attr/colorSurface"
        app:elevation="8dp" />
        
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

```xml
<!-- menu/bottom_nav_menu.xml -->
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/nav_flashlight"
        android:icon="@drawable/ic_flashlight"
        android:title="@string/nav_flashlight" />
    
    <item
        android:id="@+id/nav_sos"
        android:icon="@drawable/ic_sos"
        android:title="@string/nav_sos" />
    
    <item
        android:id="@+id/nav_effects"
        android:icon="@drawable/ic_effects"
        android:title="@string/nav_effects" />
    
    <item
        android:id="@+id/nav_settings"
        android:icon="@drawable/ic_settings"
        android:title="@string/nav_settings" />
</menu>
```

## Luồng Thiết Kế

1. **Splash Screen**:
   - Hiển thị logo và tên ứng dụng
   - Kiểm tra quyền và khởi tạo các dịch vụ
   - Chuyển tiếp mượt mà đến màn hình chính

2. **Màn Hình Chính (Flashlight)**:
   - Nút đèn flash lớn ở trung tâm
   - Thanh trượt điều chỉnh độ sáng (nếu thiết bị hỗ trợ)
   - Các tùy chọn nhanh (Quick Actions)
   - Bottom Navigation để điều hướng

3. **Màn Hình Hiệu Ứng**:
   - Danh sách các hiệu ứng đèn flash dạng grid
   - Card cho mỗi hiệu ứng với xem trước và tên
   - Tùy chọn tùy chỉnh hiệu ứng
   - Phân loại theo nhóm (Beat, Strobe, SOS, v.v.)

4. **Màn Hình SOS**:
   - Thiết kế nổi bật với màu đỏ cảnh báo
   - Nút SOS lớn với animation thu hút
   - Thông tin hướng dẫn rõ ràng
   - Tùy chọn cài đặt liên hệ khẩn cấp

5. **Màn Hình Cài Đặt**:
   - Các nhóm cài đặt rõ ràng (Chung, Đèn, SOS, Thông báo)
   - Switches, sliders và các điều khiển Material Design
   - Màn hình thông tin ứng dụng
   - Tùy chọn Premium

## Tối Ưu Hóa

1. **Hiệu Suất UI**:
   - Sử dụng ConstraintLayout để giảm độ sâu phân cấp view
   - Tối ưu hóa animations và chuyển cảnh
   - Tái sử dụng resources thông qua themes và styles
   - Lazy loading cho các thành phần phức tạp

2. **Kích Thước Màn Hình**:
   - Sử dụng đơn vị dp và sp cho các kích thước
   - Tỷ lệ linh hoạt thay vì kích thước cố định
   - Bố cục thích ứng (responsive) cho tablet và màn hình lớn
   - Hỗ trợ cả portrait và landscape mode

3. **Trải Nghiệm Người Dùng**:
   - Đảm bảo tất cả tương tác có phản hồi trực quan
   - Áp dụng haptic feedback (phản hồi rung) khi tương tác
   - Cung cấp tooltips và hướng dẫn cho người dùng mới
   - Tối ưu hóa cho thao tác một tay

## Khả Năng Tiếp Cận

1. **Màu Sắc và Độ Tương Phản**:
   - Đảm bảo đủ độ tương phản theo WCAG 2.1 AA
   - Không chỉ dựa vào màu sắc để truyền đạt thông tin
   - Hỗ trợ Dark Mode để giảm mỏi mắt
   - Tùy chọn "High Contrast" cho người khiếm thị

2. **Text và Đọc**:
   - Hỗ trợ TalkBack và các trình đọc màn hình
   - Cung cấp contentDescription cho tất cả các thành phần UI
   - Cho phép điều chỉnh kích thước chữ
   - Đảm bảo tất cả văn bản có thể đọc được trên mọi nền

3. **Điều Hướng và Tương Tác**:
   - Kích thước tối thiểu 48dp cho các thành phần tương tác
   - Hỗ trợ điều hướng bàn phím
   - Hỗ trợ công cụ trợ năng như Switch Access
   - Phản hồi rõ ràng cho mọi tương tác

## Kiểm Thử

1. **Thiết Bị và Phiên Bản**:
   - Kiểm tra trên nhiều kích thước màn hình (phone, tablet)
   - Kiểm tra trên Android 6.0+ với các API khác nhau
   - Đảm bảo tương thích với các overlay của nhà sản xuất
   - Kiểm tra trên các màn hình tỷ lệ khác nhau (16:9, 18:9, 20:9)

2. **Trải Nghiệm Người Dùng**:
   - Kiểm tra tất cả tương tác và luồng điều hướng
   - Verify animations và transitions giữa các màn hình
   - Kiểm tra hiệu suất trên thiết bị cấp thấp
   - Đảm bảo hiển thị chính xác trong cả Light và Dark Mode

3. **Khả Năng Tiếp Cận**:
   - Kiểm tra với TalkBack và các dịch vụ trợ năng
   - Kiểm tra độ tương phản và kích thước phông chữ
   - Verify điều hướng không chạm (voice, keyboard)
   - Đảm bảo tuân thủ WCAG 2.1 AA

## Hoàn Thành

Tính năng được coi là hoàn thành khi:
- Giao diện người dùng hiển thị đúng trên các thiết bị và kích thước màn hình
- Tất cả thành phần UI hoạt động như thiết kế và phản hồi khi tương tác
- Chuyển cảnh và animations mượt mà trên tất cả thiết bị được kiểm tra
- Hỗ trợ cả Light Mode và Dark Mode
- Đã tuân thủ các tiêu chuẩn khả năng tiếp cận
- Tối ưu hóa hiệu suất cho thiết bị cấp thấp
- Các tài nguyên (resources) được tổ chức tốt và tái sử dụng qua themes/styles 