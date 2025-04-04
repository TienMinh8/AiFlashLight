# Codebase: AiFlashLight

## Tổng Quan

AiFlashLight là ứng dụng Android viết bằng Java, triển khai dựa trên kiến trúc MVVM (Model-View-ViewModel) kết hợp với Repository Pattern. Ứng dụng được thiết kế theo cấu trúc module, mỗi tính năng được triển khai như một module riêng biệt nhưng vẫn tích hợp chặt chẽ vào ứng dụng tổng thể.

## Cấu Trúc Thư Mục

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/aiflashlight/
│   │   │   ├── activities/       # Các màn hình chính
│   │   │   ├── fragments/        # Các fragment UI
│   │   │   ├── services/         # Background services
│   │   │   ├── managers/         # Các lớp quản lý tính năng
│   │   │   ├── models/           # Data models
│   │   │   ├── utils/            # Tiện ích và helper
│   │   │   ├── views/            # Custom views
│   │   │   ├── i18n/             # Đa ngôn ngữ
│   │   │   └── monetization/     # Quảng cáo và premium
│   │   ├── res/                  # Resources
│   │   └── AndroidManifest.xml   # Manifests
│   └── test/                     # Unit tests
```

## Kiến Trúc Ứng Dụng

AiFlashLight sử dụng kiến trúc MVVM (Model-View-ViewModel) để tách biệt logic xử lý, dữ liệu và giao diện người dùng:

- **Model (models/repositories)**: Chứa dữ liệu và logic nghiệp vụ.
- **View (activities/fragments/views)**: Hiển thị dữ liệu và gửi sự kiện người dùng đến ViewModel.
- **ViewModel (viewmodels)**: Xử lý logic UI, lấy dữ liệu từ Model và chuẩn bị dữ liệu cho View.

## Thành Phần Chính

### 1. Module: Basic Flashlight

#### CameraManager.java
```java
// Quản lý truy cập Camera API để điều khiển đèn flash
public class CameraManager {
    private static CameraManager instance;
    private Camera camera;
    private boolean isFlashOn = false;
    private int currentBrightness = 100; // 0-100%
    
    // Singleton pattern
    public static synchronized CameraManager getInstance() {
        if (instance == null) {
            instance = new CameraManager();
        }
        return instance;
    }
    
    // Phương thức chính
    public void init();
    public boolean turnOnFlash();
    public boolean turnOffFlash();
    public void setBrightness(int brightness);
    public boolean toggleFlash();
    public boolean hasFlash();
    public void release();
}
```

#### FlashlightService.java
```java
// Dịch vụ nền để duy trì đèn flash khi ứng dụng bị tắt
public class FlashlightService extends Service {
    private CameraManager cameraManager;
    private NotificationManager notificationManager;
    
    // Lifecycle methods
    @Override
    public void onCreate();
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId);
    
    @Override
    public void onDestroy();
    
    // Helper methods
    private void startForeground();
    private void stopService();
}
```

#### FlashlightViewModel.java
```java
// ViewModel quản lý trạng thái đèn flash
public class FlashlightViewModel extends ViewModel {
    private MutableLiveData<Boolean> isFlashOn = new MutableLiveData<>(false);
    private MutableLiveData<Integer> brightness = new MutableLiveData<>(100);
    private CameraManager cameraManager;
    
    // Khởi tạo
    public FlashlightViewModel() {
        cameraManager = CameraManager.getInstance();
    }
    
    // Phương thức chính
    public void toggleFlash();
    public void setBrightness(int value);
    public LiveData<Boolean> getFlashStatus();
    public LiveData<Integer> getBrightness();
    
    @Override
    protected void onCleared();
}
```

### 2. Module: SOS Smart

#### SensorMonitorService.java
```java
// Dịch vụ nền để giám sát cảm biến gia tốc
public class SensorMonitorService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SOSManager sosManager;
    private SOSConfigManager configManager;
    
    // Service lifecycle
    @Override
    public void onCreate();
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId);
    
    // SensorEventListener methods
    @Override
    public void onSensorChanged(SensorEvent event);
    
    // Helper methods
    private boolean detectImpact(float[] values);
    private void startMonitoring();
    private void stopMonitoring();
}
```

#### SOSManager.java
```java
// Quản lý tín hiệu SOS
public class SOSManager {
    private static SOSManager instance;
    private CameraManager cameraManager;
    private boolean isSosActive = false;
    private Handler handler;
    
    // Singleton pattern
    public static synchronized SOSManager getInstance() {
        if (instance == null) {
            instance = new SOSManager();
        }
        return instance;
    }
    
    // Phương thức chính
    public void startSOS();
    public void stopSOS();
    public boolean isSOSActive();
    
    // SOS patterns
    private void flashSOS();
    private void flashDot();
    private void flashDash();
}
```

#### SOSConfigManager.java
```java
// Quản lý cấu hình SOS
public class SOSConfigManager {
    private static final String PREF_NAME = "sos_prefs";
    private static final String KEY_SENSITIVITY = "impact_sensitivity";
    private static final String KEY_AUTO_DETECT = "auto_detect";
    
    private SharedPreferences preferences;
    private Context context;
    
    // Khởi tạo
    public SOSConfigManager(Context context) {
        this.context = context.getApplicationContext();
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    // Phương thức chính
    public void setSensitivity(int sensitivity);
    public int getSensitivity();
    public void setAutoDetectEnabled(boolean enabled);
    public boolean isAutoDetectEnabled();
}
```

### 3. Module: Emergency Notification

#### EmergencyContactManager.java
```java
// Quản lý danh sách liên hệ khẩn cấp
public class EmergencyContactManager {
    private static final String PREF_NAME = "emergency_contacts";
    private SharedPreferences preferences;
    private Context context;
    private List<Contact> emergencyContacts;
    
    // Khởi tạo
    public EmergencyContactManager(Context context) {
        this.context = context.getApplicationContext();
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadContacts();
    }
    
    // Phương thức chính
    public List<Contact> getContacts();
    public void addContact(String name, String phone);
    public void removeContact(String phone);
    public void saveContacts();
    private void loadContacts();
}
```

#### LocationService.java
```java
// Dịch vụ định vị
public class LocationService {
    private static LocationService instance;
    private FusedLocationProviderClient fusedLocationClient;
    private Context context;
    private LocationCallback locationCallback;
    private MutableLiveData<Location> lastLocation = new MutableLiveData<>();
    
    // Singleton pattern
    public static synchronized LocationService getInstance(Context context) {
        if (instance == null) {
            instance = new LocationService(context);
        }
        return instance;
    }
    
    // Phương thức chính
    public void requestLocationUpdates();
    public void removeLocationUpdates();
    public LiveData<Location> getLastLocation();
    public String getFormattedAddress(Location location);
    public String getGoogleMapsLink(Location location);
}
```

#### EmergencyMessenger.java
```java
// Gửi tin nhắn khẩn cấp
public class EmergencyMessenger {
    private static EmergencyMessenger instance;
    private Context context;
    private EmergencyContactManager contactManager;
    private LocationService locationService;
    private MessageTemplateManager templateManager;
    
    // Singleton pattern
    public static synchronized EmergencyMessenger getInstance(Context context) {
        if (instance == null) {
            instance = new EmergencyMessenger(context);
        }
        return instance;
    }
    
    // Phương thức chính
    public void sendEmergencyMessages();
    public void sendTestMessage(String phoneNumber);
    private void sendSMS(String phoneNumber, String message);
    private String prepareMessageContent(Location location);
}
```

### 4. Module: Screen Light

#### ScreenLightManager.java
```java
// Quản lý đèn màn hình
public class ScreenLightManager {
    private static ScreenLightManager instance;
    private Context context;
    private PowerManager.WakeLock wakeLock;
    private WindowManager windowManager;
    private LightEmitterView lightView;
    private boolean isScreenLightOn = false;
    
    // Singleton pattern
    public static synchronized ScreenLightManager getInstance(Context context) {
        if (instance == null) {
            instance = new ScreenLightManager(context);
        }
        return instance;
    }
    
    // Phương thức chính
    public void turnOnScreenLight();
    public void turnOffScreenLight();
    public boolean isScreenLightOn();
    public void setColor(int color);
    public void setBrightness(int brightness);
    public void setColorTemperature(int temperature);
}
```

#### LightEmitterView.java
```java
// View hiển thị ánh sáng
public class LightEmitterView extends View {
    private Paint paint;
    private int color = Color.WHITE;
    private int brightness = 100;
    private int colorTemperature = 5000; // Kelvin
    
    // Khởi tạo
    public LightEmitterView(Context context) {
        super(context);
        init();
    }
    
    // Phương thức chính
    public void setColor(int color);
    public void setBrightness(int brightness);
    public void setColorTemperature(int temperature);
    
    // Phương thức vẽ
    @Override
    protected void onDraw(Canvas canvas);
    
    private void init();
    private int adjustColorWithTemperature(int color, int temperature);
}
```

### 5. Module: Music Effect

#### AudioAnalyzer.java
```java
// Phân tích âm thanh
public class AudioAnalyzer {
    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = 1024;
    
    private AudioRecord audioRecord;
    private boolean isRecording = false;
    private Thread analysisThread;
    private FFT fft;
    private BeatDetector beatDetector;
    private List<AudioAnalysisListener> listeners = new ArrayList<>();
    
    // Khởi tạo
    public AudioAnalyzer() {
        fft = new FFT(BUFFER_SIZE);
        beatDetector = new BeatDetector();
    }
    
    // Phương thức chính
    public void startAnalysis();
    public void stopAnalysis();
    public void addListener(AudioAnalysisListener listener);
    public void removeListener(AudioAnalysisListener listener);
    
    // Phân tích âm thanh
    private void analyze(short[] audioData);
    private float[] calculateFFT(short[] audioData);
    private void detectBeats(short[] audioData);
    
    // Interface for listeners
    public interface AudioAnalysisListener {
        void onBeatDetected();
        void onFftDataComputed(float[] fftData);
    }
}
```

#### BeatDetector.java
```java
// Phát hiện nhịp điệu
public class BeatDetector {
    private static final int HISTORY_SIZE = 43;
    private static final int MIN_INTERVAL = 250; // ms
    
    private RunningStats energyHistory = new RunningStats(HISTORY_SIZE);
    private long lastBeatTime = 0;
    private float sensitivity = 1.3f;
    
    // Phương thức chính
    public void setSensitivity(float sensitivity);
    public boolean detectBeat(short[] audioData);
    public float getEnergy(short[] audioData);
    
    // Lớp helper
    private static class RunningStats {
        // ...
    }
}
```

#### LightEffectManager.java
```java
// Quản lý hiệu ứng ánh sáng
public class LightEffectManager implements AudioAnalyzer.AudioAnalysisListener {
    private static LightEffectManager instance;
    private Context context;
    private CameraManager cameraManager;
    private AudioAnalyzer audioAnalyzer;
    private EffectType currentEffect = EffectType.BEAT;
    private boolean isActive = false;
    
    // Enum của các loại hiệu ứng
    public enum EffectType {
        BEAT, FREQUENCY, MELODY, CUSTOM
    }
    
    // Singleton pattern
    public static synchronized LightEffectManager getInstance(Context context) {
        if (instance == null) {
            instance = new LightEffectManager(context);
        }
        return instance;
    }
    
    // Phương thức chính
    public void startEffect(EffectType effectType);
    public void stopEffect();
    public boolean isActive();
    public void setEffectType(EffectType effectType);
    
    // AudioAnalysisListener methods
    @Override
    public void onBeatDetected();
    
    @Override
    public void onFftDataComputed(float[] fftData);
    
    // Helper methods
    private void handleBeatEffect();
    private void handleFrequencyEffect(float[] fftData);
    private void handleMelodyEffect(float[] fftData);
}
```

### 6. Module: Monetization

#### AdsManager.java
```java
// Quản lý quảng cáo
public class AdsManager {
    private static AdsManager instance;
    private Context context;
    private AdView bannerAdView;
    private InterstitialAd interstitialAd;
    private RewardedAd rewardedAd;
    private boolean emergencyMode = false;
    
    // Singleton pattern
    public static synchronized AdsManager getInstance(Context context) {
        if (instance == null) {
            instance = new AdsManager(context);
        }
        return instance;
    }
    
    // Phương thức chính
    public void initialize();
    public void loadBannerAd(ViewGroup adContainer);
    public void loadInterstitialAd();
    public void showInterstitialAd(Activity activity);
    public void loadRewardedAd();
    public void showRewardedAd(Activity activity, OnRewardListener listener);
    public void setEmergencyMode(boolean enabled);
    
    // Interface for reward callback
    public interface OnRewardListener {
        void onRewarded();
    }
}
```

#### BillingManager.java
```java
// Quản lý thanh toán và gói premium
public class BillingManager {
    private static BillingManager instance;
    private Context context;
    private BillingClient billingClient;
    private List<SkuDetails> skuDetailsList = new ArrayList<>();
    private Set<String> purchasedProducts = new HashSet<>();
    
    // Singleton pattern
    public static synchronized BillingManager getInstance(Context context) {
        if (instance == null) {
            instance = new BillingManager(context);
        }
        return instance;
    }
    
    // Phương thức chính
    public void initialize();
    public void querySkuDetails();
    public void launchBillingFlow(Activity activity, String skuId);
    public boolean isPremium();
    public List<SkuDetails> getAvailableProducts();
    public void acknowledgePurchase(Purchase purchase);
    public void restorePurchases();
}
```

### 7. Module: Material Design UI

#### ThemeManager.java
```java
// Quản lý theme và giao diện
public class ThemeManager {
    private static ThemeManager instance;
    private Context context;
    private boolean isDarkMode = false;
    private SharedPreferences preferences;
    
    // Theme modes
    public enum ThemeMode {
        LIGHT, DARK, SYSTEM
    }
    
    // Singleton pattern
    public static synchronized ThemeManager getInstance(Context context) {
        if (instance == null) {
            instance = new ThemeManager(context);
        }
        return instance;
    }
    
    // Phương thức chính
    public void setThemeMode(ThemeMode mode);
    public ThemeMode getThemeMode();
    public boolean isDarkMode();
    public void applyTheme(Activity activity);
}
```

#### FlashlightButton.java
```java
// Custom view cho nút đèn pin
public class FlashlightButton extends MaterialCardView {
    private static final float PRESSED_SCALE = 0.95f;
    private static final long ANIMATION_DURATION = 100; // ms
    
    private boolean isOn = false;
    private ImageView iconView;
    private TextView statusLabel;
    private FlashController flashController;
    
    // Khởi tạo
    public FlashlightButton(Context context) {
        super(context);
        init(context, null);
    }
    
    // Phương thức chính
    public void setOn(boolean on);
    public boolean isOn();
    public void toggle();
    
    // Helper methods
    private void init(Context context, AttributeSet attrs);
    private void setupClickListener();
    private void animateButtonPress();
    private void updateButtonState();
}
```

### 8. Module: I18n (Đa Ngôn Ngữ)

#### LanguageManager.java
```java
// Quản lý ngôn ngữ
public class LanguageManager {
    private static final String PREF_LANGUAGE = "app_language";
    private static LanguageManager instance;
    private final Context context;
    private final SharedPreferences preferences;
    
    // Singleton pattern
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
    
    // Phương thức chính
    public void setLanguage(String languageCode);
    public String getCurrentLanguage();
    public String getSystemLanguage();
    public Context updateResources(Context context, String language);
    public void applyLanguage(Activity activity);
    public List<LanguageItem> getSupportedLanguages();
}
```

## Base Classes

### BaseActivity.java
```java
// Lớp cơ sở cho tất cả các Activity
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
        
        // Áp dụng theme
        ThemeManager.getInstance(this).applyTheme(this);
        
        // Áp dụng ngôn ngữ
        LanguageManager.getInstance(this).applyLanguage(this);
    }
    
    // Helper methods cho tất cả activities
    protected void showToast(String message);
    protected void showSnackbar(View view, String message);
    protected void checkRequiredPermissions(String[] permissions);
}
```

### BaseFragment.java
```java
// Lớp cơ sở cho tất cả Fragment
public abstract class BaseFragment extends Fragment {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    
    // Helper methods cho tất cả fragments
    protected void showToast(String message);
    protected void showSnackbar(View view, String message);
    protected BaseActivity getBaseActivity();
}
```

### AiFlashLightApplication.java
```java
// Lớp Application
public class AiFlashLightApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Khởi tạo và áp dụng ngôn ngữ
        LanguageManager languageManager = LanguageManager.getInstance(this);
        String savedLanguage = languageManager.getCurrentLanguage();
        
        // Áp dụng ngôn ngữ
        Locale locale = new Locale(savedLanguage);
        Locale.setDefault(locale);
        
        // Khởi tạo các manager khác
        ThemeManager.getInstance(this);
        AdsManager.getInstance(this).initialize();
        BillingManager.getInstance(this).initialize();
    }

    @Override
    protected void attachBaseContext(Context base) {
        LanguageManager languageManager = LanguageManager.getInstance(base);
        String language = languageManager.getCurrentLanguage();
        super.attachBaseContext(languageManager.updateResources(base, language));
    }
}
```

## Kết Luận

Codebase của AiFlashLight được thiết kế theo mô hình module hóa với các manager và service riêng biệt cho từng tính năng. Điều này giúp dễ dàng mở rộng, bảo trì và kiểm thử từng thành phần riêng lẻ.

Sử dụng kiến trúc MVVM giúp tách biệt UI và logic, giúp code sạch và dễ bảo trì hơn. Các thành phần được thiết kế để giao tiếp với nhau thông qua interface và listeners, tạo ra sự linh hoạt trong việc tích hợp hoặc thay thế các module.
