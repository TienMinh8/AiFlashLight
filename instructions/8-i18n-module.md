# Module: Đa Ngôn Ngữ (I18n) 🌐

## Tổng Quan
Module Đa Ngôn Ngữ (i18n) cho ứng dụng AiFlashLight cung cấp khả năng hiển thị nội dung bằng nhiều ngôn ngữ, cải thiện trải nghiệm người dùng toàn cầu. Module này bao gồm hệ thống quản lý ngôn ngữ, tự động phát hiện ngôn ngữ hệ thống, và cho phép người dùng chọn ngôn ngữ ưa thích.

## Yêu Cầu Kỹ Thuật

### Ngôn Ngữ Hỗ Trợ
1. Tiếng Anh (mặc định)
2. Tiếng Việt
3. Tiếng Trung Quốc (giản thể và phồn thể)
4. Tiếng Tây Ban Nha
5. Tiếng Nga
6. Tiếng Nhật
7. Tiếng Hàn
8. Tiếng Pháp
9. Tiếng Đức
10. Tiếng Ả Rập

### Cấu Trúc Thư Mục
```
res/
├── values/
│   ├── strings.xml           # Tiếng Anh (mặc định)
├── values-vi/
│   ├── strings.xml           # Tiếng Việt
├── values-zh-rCN/
│   ├── strings.xml           # Tiếng Trung (giản thể)
├── values-zh-rTW/
│   ├── strings.xml           # Tiếng Trung (phồn thể)
├── values-es/
│   ├── strings.xml           # Tiếng Tây Ban Nha
├── values-ru/
│   ├── strings.xml           # Tiếng Nga
├── values-ja/
│   ├── strings.xml           # Tiếng Nhật
├── values-ko/
│   ├── strings.xml           # Tiếng Hàn
├── values-fr/
│   ├── strings.xml           # Tiếng Pháp
├── values-de/
│   ├── strings.xml           # Tiếng Đức
├── values-ar/
│   ├── strings.xml           # Tiếng Ả Rập
```

## Các Thành Phần Cần Triển Khai

### 1. Lớp Quản Lý Ngôn Ngữ (LanguageManager)

Quản lý việc thay đổi ngôn ngữ trong thời gian chạy và lưu trữ cài đặt ngôn ngữ người dùng:

```java
public class LanguageManager {
    private static final String PREF_LANGUAGE = "app_language";
    private static LanguageManager instance;
    private final Context context;
    private final SharedPreferences preferences;

    private LanguageManager(Context context) {
        this.context = context.getApplicationContext();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized LanguageManager getInstance(Context context) {
        if (instance == null) {
            instance = new LanguageManager(context);
        }
        return instance;
    }

    public void setLanguage(String languageCode) {
        preferences.edit().putString(PREF_LANGUAGE, languageCode).apply();
    }

    public String getCurrentLanguage() {
        return preferences.getString(PREF_LANGUAGE, getSystemLanguage());
    }

    public String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        
        return context.createConfigurationContext(configuration);
    }

    public void applyLanguage(Activity activity) {
        String language = getCurrentLanguage();
        activity.getResources().getConfiguration().setLocale(new Locale(language));
        Locale.setDefault(new Locale(language));
        
        Configuration config = new Configuration();
        config.locale = new Locale(language);
        activity.getResources().updateConfiguration(config, 
                activity.getResources().getDisplayMetrics());
    }

    // Danh sách ngôn ngữ được hỗ trợ
    public List<LanguageItem> getSupportedLanguages() {
        List<LanguageItem> languages = new ArrayList<>();
        languages.add(new LanguageItem("en", "English", "🇺🇸"));
        languages.add(new LanguageItem("vi", "Tiếng Việt", "🇻🇳"));
        languages.add(new LanguageItem("zh", "中文", "🇨🇳"));
        languages.add(new LanguageItem("es", "Español", "🇪🇸"));
        languages.add(new LanguageItem("ru", "Русский", "🇷🇺"));
        languages.add(new LanguageItem("ja", "日本語", "🇯🇵"));
        languages.add(new LanguageItem("ko", "한국어", "🇰🇷"));
        languages.add(new LanguageItem("fr", "Français", "🇫🇷"));
        languages.add(new LanguageItem("de", "Deutsch", "🇩🇪"));
        languages.add(new LanguageItem("ar", "العربية", "🇸🇦"));
        return languages;
    }
}
```

### 2. Lớp Đối Tượng Ngôn Ngữ (LanguageItem)

```java
public class LanguageItem {
    private String languageCode; // Mã ISO-639-1 ("en", "vi", "zh", etc.)
    private String displayName;  // Tên hiển thị ("English", "Tiếng Việt", "中文", etc.)
    private String flagEmoji;    // Emoji cờ quốc gia

    public LanguageItem(String languageCode, String displayName, String flagEmoji) {
        this.languageCode = languageCode;
        this.displayName = displayName;
        this.flagEmoji = flagEmoji;
    }

    // Getters and setters
    public String getLanguageCode() {
        return languageCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFlagEmoji() {
        return flagEmoji;
    }
}
```

### 3. Giao Diện Chọn Ngôn Ngữ (LanguageSelectionActivity)

```xml
<!-- layout/activity_language_selection.xml -->
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="?attr/colorPrimary"
        android:elevation="4dp"
        android:theme="@style/ThemeOverlay.MaterialComponents.Dark.ActionBar"
        app:layout_constraintTop_toTopOf="parent"
        app:title="@string/select_language" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/language_list"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintTop_toBottomOf="@id/toolbar"
        app:layout_constraintBottom_toBottomOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### 4. Adapter Chọn Ngôn Ngữ (LanguageAdapter)

```java
public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {
    private List<LanguageItem> languages;
    private Context context;
    private OnLanguageSelectedListener listener;
    private String currentLanguageCode;

    public LanguageAdapter(Context context, List<LanguageItem> languages, String currentLanguageCode) {
        this.context = context;
        this.languages = languages;
        this.currentLanguageCode = currentLanguageCode;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_language, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        LanguageItem language = languages.get(position);
        holder.flagText.setText(language.getFlagEmoji());
        holder.languageName.setText(language.getDisplayName());
        
        // Hiển thị tick cho ngôn ngữ đang được chọn
        if (language.getLanguageCode().equals(currentLanguageCode)) {
            holder.checkIcon.setVisibility(View.VISIBLE);
        } else {
            holder.checkIcon.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLanguageSelected(language);
            }
        });
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public void setOnLanguageSelectedListener(OnLanguageSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnLanguageSelectedListener {
        void onLanguageSelected(LanguageItem language);
    }

    static class LanguageViewHolder extends RecyclerView.ViewHolder {
        TextView flagText;
        TextView languageName;
        ImageView checkIcon;

        public LanguageViewHolder(@NonNull View itemView) {
            super(itemView);
            flagText = itemView.findViewById(R.id.flag_emoji);
            languageName = itemView.findViewById(R.id.language_name);
            checkIcon = itemView.findViewById(R.id.check_icon);
        }
    }
}
```

### 5. Tích Hợp với BaseActivity

```java
public abstract class BaseActivity extends AppCompatActivity {
    
    @Override
    protected void attachBaseContext(Context newBase) {
        LanguageManager languageManager = LanguageManager.getInstance(newBase);
        String currentLanguage = languageManager.getCurrentLanguage();
        Context context = languageManager.updateResources(newBase, currentLanguage);
        super.attachBaseContext(context);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Đảm bảo ngôn ngữ được áp dụng cho activity này
        LanguageManager.getInstance(this).applyLanguage(this);
    }
}
```

### 6. Tệp Chuỗi Mẫu (strings.xml)

```xml
<!-- values/strings.xml (English - Default) -->
<resources>
    <string name="app_name">AiFlashLight</string>
    <string name="flash_on">ON</string>
    <string name="flash_off">OFF</string>
    <string name="tap_to_toggle">TAP TO TOGGLE</string>
    <string name="settings">Settings</string>
    <string name="language">Language</string>
    <string name="select_language">Select Language</string>
    <string name="sos">SOS</string>
    <string name="effects">Effects</string>
    <string name="screen_light">Screen Light</string>
    <string name="emergency_contacts">Emergency Contacts</string>
    <string name="premium">Premium</string>
    <string name="dark_mode">Dark Mode</string>

    <!-- Module: Basic Flashlight -->
    <string name="no_flash_error">Your device doesn\'t have a flash</string>
    <string name="permission_required">Camera permission required</string>
    <string name="grant_permission">Grant Permission</string>
    
    <!-- Module: SOS -->
    <string name="sos_active">SOS ACTIVE</string>
    <string name="stop_sos">STOP</string>
    <string name="sos_sensitivity">Impact Sensitivity</string>
    <string name="impact_detected">Impact Detected!</string>
    <string name="sending_sos">Sending SOS in</string>
    <string name="cancel">Cancel</string>
    
    <!-- Module: Emergency Notification -->
    <string name="add_emergency_contact">Add Emergency Contact</string>
    <string name="message_template">Message Template</string>
    <string name="test_message">Test Message</string>
    <string name="message_sent">Message Sent</string>
    <string name="message_failed">Message Failed</string>
    
    <!-- Module: Screen Light -->
    <string name="brightness">Brightness</string>
    <string name="color_temperature">Color Temperature</string>
    <string name="warm">Warm</string>
    <string name="cool">Cool</string>
    
    <!-- Module: Music Effect -->
    <string name="select_effect">Select Effect</string>
    <string name="beat_sync">Beat Sync</string>
    <string name="frequency_mode">Frequency Mode</string>
    <string name="custom_effect">Custom Effect</string>
    <string name="save_effect">Save Effect</string>
    
    <!-- Module: Monetization -->
    <string name="upgrade_to_premium">Upgrade to Premium</string>
    <string name="remove_ads">Remove Ads</string>
    <string name="unlock_all_features">Unlock All Features</string>
    <string name="premium_features">Premium Features</string>
    <string name="subscribe">Subscribe</string>
    <string name="subscription_details">Subscription Details</string>
</resources>
```

```xml
<!-- values-vi/strings.xml (Vietnamese) -->
<resources>
    <string name="app_name">AiFlashLight</string>
    <string name="flash_on">BẬT</string>
    <string name="flash_off">TẮT</string>
    <string name="tap_to_toggle">CHẠM ĐỂ BẬT/TẮT</string>
    <string name="settings">Cài đặt</string>
    <string name="language">Ngôn ngữ</string>
    <string name="select_language">Chọn Ngôn Ngữ</string>
    <string name="sos">SOS</string>
    <string name="effects">Hiệu ứng</string>
    <string name="screen_light">Đèn Màn Hình</string>
    <string name="emergency_contacts">Liên hệ Khẩn cấp</string>
    <string name="premium">Cao cấp</string>
    <string name="dark_mode">Chế độ tối</string>

    <!-- Module: Basic Flashlight -->
    <string name="no_flash_error">Thiết bị của bạn không có đèn flash</string>
    <string name="permission_required">Cần quyền truy cập camera</string>
    <string name="grant_permission">Cấp quyền</string>
    
    <!-- Module: SOS -->
    <string name="sos_active">SOS ĐANG HOẠT ĐỘNG</string>
    <string name="stop_sos">DỪNG</string>
    <string name="sos_sensitivity">Độ nhạy va chạm</string>
    <string name="impact_detected">Phát hiện va chạm!</string>
    <string name="sending_sos">Gửi SOS trong</string>
    <string name="cancel">Hủy</string>
    
    <!-- Các phần ngôn ngữ khác tiếp tục tương tự -->
</resources>
```

## Quy Trình Xử Lý

### 1. Khởi động Ứng Dụng
- Khi ứng dụng khởi động, `LanguageManager` kiểm tra ngôn ngữ đã lưu trong SharedPreferences
- Nếu không tìm thấy, sử dụng ngôn ngữ hệ thống (Locale.getDefault())
- Áp dụng ngôn ngữ đã chọn cho toàn bộ ứng dụng

### 2. Thay Đổi Ngôn Ngữ
- Người dùng mở màn hình cài đặt và chọn "Ngôn ngữ"
- Hiển thị danh sách các ngôn ngữ được hỗ trợ với `LanguageSelectionActivity`
- Khi người dùng chọn ngôn ngữ mới, `LanguageManager` lưu lựa chọn và áp dụng ngôn ngữ
- Khởi động lại màn hình hiện tại và tất cả các activity để áp dụng thay đổi

### 3. Xử Lý Hướng RTL (Right-to-Left)
- Phát hiện tự động ngôn ngữ RTL như tiếng Ả Rập
- Thay đổi bố cục UI sang hướng RTL
- Đảm bảo tất cả các thành phần hoạt động chính xác với bố cục RTL

## Mã Nguồn Bổ Sung

### Thực hiện Thay Đổi Ngôn Ngữ

```java
public class LanguageSelectionActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private LanguageAdapter adapter;
    private LanguageManager languageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        languageManager = LanguageManager.getInstance(this);
        
        recyclerView = findViewById(R.id.language_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        List<LanguageItem> languages = languageManager.getSupportedLanguages();
        adapter = new LanguageAdapter(
            this, 
            languages, 
            languageManager.getCurrentLanguage()
        );
        
        adapter.setOnLanguageSelectedListener(language -> {
            // Lưu ngôn ngữ mới
            languageManager.setLanguage(language.getLanguageCode());
            
            // Khởi động lại activity để áp dụng thay đổi
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
```

### Xử Lý Application Để Áp Dụng Ngôn Ngữ Toàn Cầu

```java
public class AiFlashLightApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Khởi tạo và áp dụng ngôn ngữ đã lưu
        LanguageManager languageManager = LanguageManager.getInstance(this);
        String savedLanguage = languageManager.getCurrentLanguage();
        
        // Áp dụng ngôn ngữ
        Locale locale = new Locale(savedLanguage);
        Locale.setDefault(locale);
        
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void attachBaseContext(Context base) {
        // Đảm bảo ngôn ngữ được áp dụng ngay cả khi ứng dụng khởi động lại
        LanguageManager languageManager = LanguageManager.getInstance(base);
        String language = languageManager.getCurrentLanguage();
        super.attachBaseContext(languageManager.updateResources(base, language));
    }
}
```

## Tối Ưu Hóa

### 1. Hiệu Suất Chuyển Đổi Ngôn Ngữ
- Sử dụng cơ chế cached strings để giảm thời gian tải
- Áp dụng lazy loading cho tài nguyên ngôn ngữ
- Tái sử dụng bộ nhớ đệm cho chuỗi thường xuyên sử dụng

### 2. Xử Lý Tài Nguyên
- Sử dụng công cụ tự động hóa để quản lý tệp chuỗi ngôn ngữ
- Thêm chú thích cho người dịch
- Quản lý format chuỗi nhất quán giữa các ngôn ngữ
- Phát hiện và cảnh báo về chuỗi bị thiếu

### 3. Hỗ trợ Ngôn Ngữ Động
- Hỗ trợ tải gói ngôn ngữ mới từ máy chủ
- Cập nhật ngôn ngữ mà không cần phát hành phiên bản mới
- Khả năng mở rộng dễ dàng cho các ngôn ngữ mới

## Kiểm Thử

### 1. Kiểm Thử Tương Thích
- Kiểm tra mỗi ngôn ngữ trên nhiều kích thước màn hình
- Kiểm tra các phiên bản Android khác nhau
- Kiểm tra bố cục RTL cho ngôn ngữ Ả Rập
- Kiểm tra font chữ đặc biệt (ví dụ: tiếng Nhật, tiếng Hàn)

### 2. Kiểm Thử Tự Động
- Kiểm tra tất cả tài nguyên chuỗi có sẵn cho mỗi ngôn ngữ
- Xác minh các tham số định dạng nhất quán
- Kiểm tra độ dài chuỗi (quá dài trong một số ngôn ngữ)
- Kiểm tra hoạt động ứng dụng khi chuyển đổi ngôn ngữ

### 3. Kiểm Thử Người Dùng
- Người dùng thật cho mỗi ngôn ngữ đánh giá chất lượng dịch
- Thu thập phản hồi về các vấn đề ngôn ngữ cụ thể
- Kiểm tra luồng người dùng với nhiều cài đặt ngôn ngữ

## Hoàn Thành

Module Đa Ngôn Ngữ được coi là hoàn thành khi:
- Tất cả giao diện người dùng hiển thị đúng trong mỗi ngôn ngữ được hỗ trợ
- Chuyển đổi ngôn ngữ hoạt động mượt mà, thay đổi áp dụng toàn cầu
- Người dùng có thể dễ dàng chọn và đổi ngôn ngữ ưa thích
- Ngôn ngữ hệ thống được phát hiện và áp dụng tự động khi đầu tiên khởi chạy
- Tất cả tệp chuỗi đã được dịch và kiểm tra chất lượng
- Hỗ trợ đầy đủ các ngôn ngữ RTL (Ả Rập)
- Đã hoàn thành các kiểm thử ngôn ngữ trên nhiều thiết bị
- Hiệu suất không bị ảnh hưởng khi chuyển đổi giữa các ngôn ngữ 