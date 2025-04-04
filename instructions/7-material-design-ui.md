# Module: Giao Diện Material Design 🎨

## Tổng Quan
Module Giao Diện Material Design triển khai giao diện người dùng hiện đại, trực quan và nhất quán cho ứng dụng AiFlashLight. Tập trung vào trải nghiệm người dùng tối ưu, dễ tiếp cận, và thích ứng với đa dạng kích thước thiết bị Android.

## Tham Khảo Giao Diện 
Giao diện Flash Alert được thiết kế theo mẫu như hình ảnh tham khảo, với các đặc điểm chính:

### Flash Alert UI
- Nền tối với màu đen (#000000) làm nền chính
- Nút nguồn tròn lớn ở trung tâm màn hình với hiệu ứng phát sáng
  - Màu đỏ neon (#FF0000) khi đèn flash tắt
  - Màu xanh lơ neon (#00FFFF) khi đèn flash bật
- Vùng xung quanh nút nguồn có hiệu ứng gradient màu xám
- Các phần tử giao diện được nhóm trong các card tối màu với góc bo tròn
- Thanh tiêu đề với tên "Flash light Alert" ở trên cùng
- Menu hamburger bên trái và nút trợ giúp (?) bên phải
- Navigation bar với nút Home ở dưới cùng

### Bố Cục Chính
- 2x2 grid cho bốn chức năng chính: Incoming Calls, SMS, Flashing Type, App Selection
- Mỗi ô chức năng có icon trực quan và switch bật/tắt
- Thanh trượt điều chỉnh tốc độ nhấp nháy với giá trị từ 0ms đến 30ms
- Bottom bar với các biểu tượng chức năng (hiệu ứng đèn flash, home, cài đặt)

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
- **Màu Chính**: 
  - Dark: #121212 (nền đen)
  - Primary: #3700B3
  - Secondary: #03DAC6
- **Màu Nhấn**: 
  - Red Neon: #FF0000 (nút tắt)
  - Cyan Neon: #00FFFF (nút bật)
  - Orange Accent: #FF9800 (thanh trượt & thành phần tương tác)
- **Màu Giao Diện Card**:
  - Card Background: #1E1E1E
  - Card Border: #2A2A2A
- **Màu Văn Bản**:
  - Primary Text: #FFFFFF
  - Secondary Text: #B3FFFFFF (70% opacity)
  - Disabled Text: #61FFFFFF (38% opacity)

### Typography
- **Headings**: Roboto Medium, 20sp
- **Subheadings**: Roboto Regular, 16sp
- **Body**: Roboto Regular, 14sp
- **Buttons**: Roboto Medium, 14sp
- **Caption**: Roboto Regular, 12sp

### Elevation & Shadows
- **Card Elevation**: 2dp
- **Button Elevation**: 4dp
- **Dialog Elevation**: 24dp
- **Shadow Style**: Custom neon glow effects for interactive elements

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

## Mã Code Mẫu cho Flash Alert

### Layout XML
```xml
<!-- flash_alert_layout.xml -->
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/background_dark">

    <!-- Top App Bar -->
    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="@android:color/transparent"
        app:layout_constraintTop_toTopOf="parent"
        app:title="Flash light Alert"
        app:titleTextColor="@color/white">
        
        <ImageView
            android:id="@+id/ivHelp"
            android:layout_width="24dp"
            android:layout_height="24dp"
            android:layout_gravity="end"
            android:layout_marginEnd="16dp"
            android:src="@drawable/ic_help"
            android:tint="@color/white" />
    </androidx.appcompat.widget.Toolbar>

    <!-- Main Power Button -->
    <com.aiflashlight.views.PowerButtonView
        android:id="@+id/btnPower"
        android:layout_width="180dp"
        android:layout_height="180dp"
        app:layout_constraintTop_toBottomOf="@id/toolbar"
        app:layout_constraintBottom_toTopOf="@id/gridLayout"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:activeColor="@color/cyan_neon"
        app:inactiveColor="@color/red_neon" />

    <!-- 2x2 Grid Layout -->
    <GridLayout
        android:id="@+id/gridLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="16dp"
        android:columnCount="2"
        android:rowCount="2"
        app:layout_constraintTop_toBottomOf="@id/btnPower"
        app:layout_constraintBottom_toTopOf="@id/sliderCard">

        <!-- Incoming Calls Card -->
        <com.google.android.material.card.MaterialCardView
            android:id="@+id/cardIncomingCalls"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_columnWeight="1"
            android:layout_rowWeight="1"
            android:layout_margin="4dp"
            app:cardBackgroundColor="@color/card_background"
            app:cardCornerRadius="12dp">
            
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:padding="12dp">
                
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal">
                    
                    <ImageView
                        android:layout_width="24dp"
                        android:layout_height="24dp"
                        android:src="@drawable/ic_call"
                        android:tint="@color/green" />
                    
                    <TextView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="8dp"
                        android:text="Incoming\nCalls"
                        android:textColor="@color/white" />
                </LinearLayout>
                
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal"
                    android:layout_marginTop="8dp">
                    
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Enable"
                        android:textColor="@color/white" />
                    
                    <View
                        android:layout_width="0dp"
                        android:layout_height="1dp"
                        android:layout_weight="1" />
                    
                    <com.google.android.material.switchmaterial.SwitchMaterial
                        android:id="@+id/switchCalls"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:checked="false" />
                </LinearLayout>
            </LinearLayout>
        </com.google.android.material.card.MaterialCardView>

        <!-- SMS Card - Similar structure -->
        <com.google.android.material.card.MaterialCardView
            android:id="@+id/cardSms"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_columnWeight="1"
            android:layout_rowWeight="1"
            android:layout_margin="4dp"
            app:cardBackgroundColor="@color/card_background"
            app:cardCornerRadius="12dp">
            
            <!-- Similar content structure as Incoming Calls -->
        </com.google.android.material.card.MaterialCardView>

        <!-- Flashing Type Card -->
        <com.google.android.material.card.MaterialCardView
            android:id="@+id/cardFlashingType"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_columnWeight="1"
            android:layout_rowWeight="1"
            android:layout_margin="4dp"
            app:cardBackgroundColor="@color/card_background"
            app:cardCornerRadius="12dp">
            
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:padding="12dp">
                
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal">
                    
                    <ImageView
                        android:layout_width="24dp"
                        android:layout_height="24dp"
                        android:src="@drawable/ic_lightning"
                        android:tint="@color/yellow" />
                    
                    <TextView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="8dp"
                        android:text="Flashing\nType"
                        android:textColor="@color/white" />
                </LinearLayout>
                
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal"
                    android:layout_marginTop="8dp">
                    
                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Select"
                        android:textColor="@color/white" />
                    
                    <View
                        android:layout_width="0dp"
                        android:layout_height="1dp"
                        android:layout_weight="1" />
                    
                    <ImageView
                        android:id="@+id/ivFlashingTypeNext"
                        android:layout_width="24dp"
                        android:layout_height="24dp"
                        android:src="@drawable/ic_chevron_right"
                        android:tint="@color/white" />
                </LinearLayout>
            </LinearLayout>
        </com.google.android.material.card.MaterialCardView>

        <!-- App Selection Card - Similar to Flashing Type -->
        <com.google.android.material.card.MaterialCardView
            android:id="@+id/cardAppSelection"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_columnWeight="1"
            android:layout_rowWeight="1"
            android:layout_margin="4dp"
            app:cardBackgroundColor="@color/card_background"
            app:cardCornerRadius="12dp">
            
            <!-- Similar content structure as Flashing Type -->
        </com.google.android.material.card.MaterialCardView>
    </GridLayout>

    <!-- Flashing Speed Slider Card -->
    <com.google.android.material.card.MaterialCardView
        android:id="@+id/sliderCard"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        app:cardBackgroundColor="@color/card_background"
        app:cardCornerRadius="12dp"
        app:layout_constraintTop_toBottomOf="@id/gridLayout"
        app:layout_constraintBottom_toTopOf="@id/bottomNav">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="16dp">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal">

                <ImageView
                    android:layout_width="24dp"
                    android:layout_height="24dp"
                    android:src="@drawable/ic_speed"
                    android:tint="@color/orange_accent" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginStart="8dp"
                    android:text="Flashing Speed"
                    android:textColor="@color/white" />
            </LinearLayout>

            <com.google.android.material.slider.Slider
                android:id="@+id/sliderSpeed"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:valueFrom="0"
                android:valueTo="30"
                app:thumbColor="@color/orange_accent"
                app:trackColorActive="@color/orange_accent"
                app:trackColorInactive="@color/gray" />

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="0 ms"
                    android:textColor="@color/white"
                    android:textSize="12sp" />

                <View
                    android:layout_width="0dp"
                    android:layout_height="1dp"
                    android:layout_weight="1" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="30 ms"
                    android:textColor="@color/white"
                    android:textSize="12sp" />
            </LinearLayout>
        </LinearLayout>
    </com.google.android.material.card.MaterialCardView>

    <!-- Bottom Navigation -->
    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNav"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/background_dark"
        app:itemIconTint="@color/white"
        app:itemTextColor="@color/white"
        app:layout_constraintBottom_toBottomOf="parent"
        app:menu="@menu/bottom_nav_menu" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### Mã Custom Power Button
```java
public class PowerButtonView extends View {
    private Paint circlePaint;
    private Paint glowPaint;
    private Paint iconPaint;
    private Path iconPath;
    private boolean isActive = false;
    private int activeColor;
    private int inactiveColor;
    private float glowRadius = 20f;
    private ValueAnimator glowAnimator;

    public PowerButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PowerButtonView);
        activeColor = a.getColor(R.styleable.PowerButtonView_activeColor, 
                                Color.parseColor("#00FFFF"));
        inactiveColor = a.getColor(R.styleable.PowerButtonView_inactiveColor, 
                                 Color.parseColor("#FF0000"));
        a.recycle();

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.DKGRAY);

        glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setStrokeWidth(5f);
        glowPaint.setColor(inactiveColor);

        iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        iconPaint.setStyle(Paint.Style.STROKE);
        iconPaint.setStrokeWidth(8f);
        iconPaint.setStrokeCap(Paint.Cap.ROUND);
        iconPaint.setColor(inactiveColor);

        iconPath = new Path();

        // Setup glow animation
        setupGlowAnimation();

        // Enable touch feedback
        setClickable(true);
    }

    private void setupGlowAnimation() {
        glowAnimator = ValueAnimator.ofFloat(5f, 20f);
        glowAnimator.setDuration(1500);
        glowAnimator.setRepeatMode(ValueAnimator.REVERSE);
        glowAnimator.setRepeatCount(ValueAnimator.INFINITE);
        glowAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        glowAnimator.addUpdateListener(animation -> {
            glowRadius = (float) animation.getAnimatedValue();
            invalidate();
        });
        glowAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 2 - 20;
        
        // Draw background circle
        canvas.drawCircle(centerX, centerY, radius, circlePaint);
        
        // Draw glow
        glowPaint.setColor(isActive ? activeColor : inactiveColor);
        glowPaint.setMaskFilter(new BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.NORMAL));
        canvas.drawCircle(centerX, centerY, radius, glowPaint);
        
        // Draw power icon
        iconPaint.setColor(isActive ? activeColor : inactiveColor);
        
        // Create power icon path
        iconPath.reset();
        int iconSize = radius / 2;
        // Vertical line
        iconPath.moveTo(centerX, centerY - iconSize / 2);
        iconPath.lineTo(centerX, centerY + iconSize / 2);
        // Circle
        RectF oval = new RectF(centerX - iconSize, centerY - iconSize, 
                             centerX + iconSize, centerY + iconSize);
        iconPath.addArc(oval, -60, 300);
        
        canvas.drawPath(iconPath, iconPaint);
    }

    public void toggle() {
        isActive = !isActive;
        invalidate();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        if (this.isActive != active) {
            this.isActive = active;
            invalidate();
        }
    }
}
```

### Activity Java
```java
public class FlashAlertActivity extends AppCompatActivity {
    
    private PowerButtonView btnPower;
    private SwitchMaterial switchCalls;
    private SwitchMaterial switchSms;
    private Slider sliderSpeed;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_alert_layout);
        
        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        
        // Initialize views
        btnPower = findViewById(R.id.btnPower);
        switchCalls = findViewById(R.id.switchCalls);
        switchSms = findViewById(R.id.switchSms);
        sliderSpeed = findViewById(R.id.sliderSpeed);
        
        // Setup listeners
        btnPower.setOnClickListener(v -> {
            btnPower.toggle();
            updateFlashAlertState();
        });
        
        switchCalls.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save call alert preference
            PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean("flash_alert_calls", isChecked)
                .apply();
        });
        
        switchSms.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save SMS alert preference
            PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean("flash_alert_sms", isChecked)
                .apply();
        });
        
        sliderSpeed.addOnChangeListener((slider, value, fromUser) -> {
            if (fromUser) {
                // Save flash speed preference (0-30ms)
                PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putInt("flash_alert_speed", (int) value)
                    .apply();
            }
        });
        
        // Load saved preferences
        loadPreferences();
    }
    
    private void loadPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isEnabled = prefs.getBoolean("flash_alert_enabled", false);
        boolean callsEnabled = prefs.getBoolean("flash_alert_calls", false);
        boolean smsEnabled = prefs.getBoolean("flash_alert_sms", false);
        int speed = prefs.getInt("flash_alert_speed", 15);
        
        btnPower.setActive(isEnabled);
        switchCalls.setChecked(callsEnabled);
        switchSms.setChecked(smsEnabled);
        sliderSpeed.setValue(speed);
    }
    
    private void updateFlashAlertState() {
        boolean isEnabled = btnPower.isActive();
        
        // Save flash alert state
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putBoolean("flash_alert_enabled", isEnabled)
            .apply();
        
        // Enable/disable the service
        if (isEnabled) {
            // Start the flash alert service
            Intent serviceIntent = new Intent(this, FlashAlertService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        } else {
            // Stop the flash alert service
            stopService(new Intent(this, FlashAlertService.class));
        }
    }
} 
```

## Chi Tiết Màn Hình Flash Alert

Màn hình Flash Alert là một trong những giao diện quan trọng của ứng dụng AiFlashLight, cho phép đèn flash nhấp nháy khi có thông báo, cuộc gọi hoặc tin nhắn. Dưới đây là mô tả chi tiết các thành phần của màn hình:

### 1. Thanh Tiêu Đề
- Màu nền đen (#000000) với tiêu đề "Flash light Alert" màu trắng ở giữa
- Biểu tượng menu hamburger (≡) ở góc trái
- Biểu tượng trợ giúp (?) ở góc phải
- Thiết kế tối giản, không có phần shadow hay divider

### 2. Nút Nguồn Trung Tâm
- Nút tròn lớn (đường kính khoảng 180dp) ở trung tâm màn hình
- Nền xám đậm với hiệu ứng neon phát sáng xung quanh
- Biểu tượng nguồn (power) ở trung tâm nút
- Hai trạng thái với màu khác nhau:
  * Đỏ neon (#FF0000) khi tính năng đang tắt
  * Xanh lơ neon (#00FFFF) khi tính năng đang bật

### 3. Grid Tùy Chọn (2×2)
Phần grid được chia thành 4 ô đều nhau, mỗi ô là một card riêng biệt:

#### a. Card "Incoming Calls"
- Nền card màu xám đậm (#1E1E1E) với góc bo tròn
- Icon điện thoại màu xanh lá (#4CAF50) ở bên trái
- Văn bản "Incoming Calls" màu trắng
- Phần dưới có nhãn "Enable" và switch bật/tắt
- Switch có màu xanh cyan khi bật, xám khi tắt

#### b. Card "SMS"
- Cùng cấu trúc với card "Incoming Calls"
- Icon tin nhắn màu xanh dương (#2196F3)
- Văn bản "SMS" màu trắng
- Switch điều khiển tính năng nhấp nháy khi có SMS

#### c. Card "Flashing Type"
- Icon sấm sét màu vàng (#FFC107)
- Văn bản "Flashing Type" màu trắng
- Thay vì switch, có nhãn "Select" và biểu tượng mũi tên (>)
- Khi nhấn sẽ mở ra menu chọn kiểu nhấp nháy

#### d. Card "App Selection"
- Icon ứng dụng nhiều màu
- Văn bản "App Selection" màu trắng
- Nhãn "Select" và biểu tượng mũi tên (>)
- Cho phép chọn ứng dụng nào sẽ kích hoạt đèn flash

### 4. Slider Tốc Độ Nhấp Nháy
- Card lớn chiếm toàn bộ chiều rộng màn hình
- Nền card màu xám đậm (#1E1E1E) với góc bo tròn
- Tiêu đề "Flashing Speed" với icon đồng hồ/tốc độ màu cam (#FF9800)
- Thanh trượt (slider) với:
  * Thumb màu cam (#FF9800)
  * Track active màu cam, track inactive màu xám
  * Giá trị từ 0ms đến 30ms
  * Nhãn "0 ms" ở bên trái và "30 ms" ở bên phải
  
### 5. Thanh Điều Hướng Dưới Cùng
- Nền màu đen (#000000)
- 3 biểu tượng chính:
  * Flash effect (tia sét) - biểu tượng hiện tại
  * Home (ngôi nhà) - ở giữa
  * Settings (bánh răng) - bên phải
- Biểu tượng được active hiển thị màu trắng đậm hơn

### Hiệu Ứng Tương Tác
- Hiệu ứng ripple khi chạm vào các thành phần tương tác
- Hiệu ứng nhấn xuống (press state) cho các nút và card
- Chuyển động mượt mà khi toggle switch
- Hiệu ứng feedback khi di chuyển thanh trượt
- Phản hồi haptic (rung nhẹ) khi nhấn nút nguồn chính

### Bố Cục Thích Ứng
- Khoảng cách giữa các thành phần tỷ lệ với kích thước màn hình
- Grid tự động điều chỉnh trong chế độ ngang
- Kích thước nút nguồn trung tâm được điều chỉnh theo kích thước màn hình
- Tối ưu cả cho điện thoại nhỏ và màn hình lớn

Màn hình Flash Alert cần đảm bảo tính thống nhất với phần còn lại của ứng dụng, đồng thời nổi bật đặc trưng riêng với các hiệu ứng neon và tương tác trực quan.

## Triển Khai Nút Nguồn Phát Sáng

Để tạo nút nguồn phát sáng với hiệu ứng neon như trong giao diện tham khảo, chúng ta sẽ kết hợp phương pháp vẽ tùy chỉnh sử dụng Canvas API và hiệu ứng animation.

### Hiệu Ứng Neon Glow
Hiệu ứng neon glow được tạo ra bằng cách sử dụng BlurMaskFilter với Paint và một ValueAnimator để làm cho hiệu ứng phát sáng nhấp nháy nhẹ tạo cảm giác sống động.

```java
// Trong PowerButtonView.java

// Tạo hiệu ứng phát sáng
private void createGlowEffect(Canvas canvas, int centerX, int centerY, int radius, int color) {
    Paint glowPaint = new Paint();
    glowPaint.setStyle(Paint.Style.STROKE);
    glowPaint.setStrokeWidth(15f);
    glowPaint.setColor(color);
    glowPaint.setMaskFilter(new BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.NORMAL));
    
    // Vẽ vòng tròn phát sáng
    canvas.drawCircle(centerX, centerY, radius, glowPaint);
    
    // Vẽ thêm vòng tròn nhỏ hơn để tăng cường độ sáng ở trung tâm
    glowPaint.setStrokeWidth(8f);
    glowPaint.setMaskFilter(new BlurMaskFilter(glowRadius / 2, BlurMaskFilter.Blur.NORMAL));
    canvas.drawCircle(centerX, centerY, radius - 10, glowPaint);
}
```

### Màu Sắc Phụ Thuộc Trạng Thái
Màu sắc nút nguồn sẽ tự động chuyển đổi giữa màu đỏ (khi tắt) và màu xanh lơ (khi bật), sử dụng hiệu ứng chuyển đổi màu mượt mà.

```java
// Trong PowerButtonView.java

// Chuyển đổi màu với hiệu ứng mượt mà
private void changeColorWithAnimation(int fromColor, int toColor) {
    ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
    colorAnimator.setDuration(300);
    colorAnimator.addUpdateListener(animation -> {
        currentColor = (int) animation.getAnimatedValue();
        invalidate();
    });
    colorAnimator.start();
}
```

### Phản Hồi Chạm
Khi người dùng nhấn vào nút nguồn, sẽ có hiệu ứng gợn sóng (ripple effect) và phản hồi rung nhẹ để tăng UX.

```java
// Trong PowerButtonView.java

// Xử lý sự kiện nhấn với hiệu ứng ripple và phản hồi haptic
@Override
public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
        } else {
            performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        }
        
        // Hiệu ứng ripple
        createRippleEffect();
    }
    if (event.getAction() == MotionEvent.ACTION_UP) {
        toggle();
        performClick();
        return true;
    }
    return super.onTouchEvent(event);
}

private void createRippleEffect() {
    ValueAnimator rippleAnimator = ValueAnimator.ofFloat(0f, 1f);
    rippleAnimator.setDuration(300);
    rippleAnimator.addUpdateListener(animation -> {
        rippleProgress = (float) animation.getAnimatedValue();
        invalidate();
    });
    rippleAnimator.start();
}
```

### Hiệu Ứng Khi Chuyển Đổi Trạng Thái
Khi chuyển từ trạng thái tắt sang bật và ngược lại, sẽ có hiệu ứng mở rộng và thu nhỏ nhẹ để mô phỏng cảm giác nhấn vật lý.

```java
// Trong PowerButtonView.java

// Hiệu ứng mở rộng/thu nhỏ khi chuyển trạng thái
private void toggleWithScaleAnimation() {
    ValueAnimator scaleDown = ValueAnimator.ofFloat(1f, 0.85f);
    scaleDown.setDuration(150);
    scaleDown.addUpdateListener(animation -> {
        buttonScale = (float) animation.getAnimatedValue();
        invalidate();
    });
    
    ValueAnimator scaleUp = ValueAnimator.ofFloat(0.85f, 1f);
    scaleUp.setDuration(150);
    scaleUp.addUpdateListener(animation -> {
        buttonScale = (float) animation.getAnimatedValue();
        invalidate();
    });
    
    scaleDown.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            isActive = !isActive;
            changeColorWithAnimation(isActive ? inactiveColor : activeColor, 
                                    isActive ? activeColor : inactiveColor);
            scaleUp.start();
        }
    });
    
    scaleDown.start();
}
```

Với sự kết hợp của các hiệu ứng trên, nút nguồn sẽ có diện mạo và trải nghiệm tương tự như giao diện tham khảo, với hiệu ứng neon phát sáng đặc trưng và khả năng tương tác cao.
