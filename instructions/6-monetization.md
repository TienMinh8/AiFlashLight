# Module: Monetization 💰

## Tổng Quan
Module Monetization triển khai chiến lược kiếm tiền cho ứng dụng AiFlashLight thông qua quảng cáo và gói premium. Mục tiêu là tạo doanh thu bền vững nhưng không làm ảnh hưởng đến trải nghiệm người dùng, đặc biệt trong các tình huống khẩn cấp.

## Yêu Cầu Kỹ Thuật

### Thư Viện & Phụ Thuộc
```gradle
dependencies {
    // Google Admob - Quảng cáo
    implementation 'com.google.android.gms:play-services-ads:22.0.0'
    
    // Google Play Billing - Gói Premium
    implementation 'com.android.billingclient:billing:6.0.0'
    
    // Analytics
    implementation 'com.google.firebase:firebase-analytics:21.2.2'
}
```

### Thành Phần Cần Triển Khai

#### 1. Quản Lý Quảng Cáo (AdsManager)
- Khởi tạo và cấu hình SDK quảng cáo
- Quản lý hiển thị quảng cáo Banner tại vị trí thích hợp
- Cài đặt quảng cáo Interstitial (xen kẽ) và Rewarded (có thưởng)
- Cơ chế chặn quảng cáo trong tình huống khẩn cấp

#### 2. Quản Lý Thanh Toán (BillingManager)
- Cấu hình các gói đăng ký Premium (hàng tháng, hàng năm, mua đứt)
- Xác thực và xử lý giao dịch thanh toán
- Khôi phục mua hàng trên nhiều thiết bị
- Quản lý chu kỳ thanh toán và gia hạn

#### 3. Quản Lý Tính Năng Premium (PremiumFeatureManager)
- Xác minh quyền sử dụng tính năng premium
- Mở khóa tính năng cao cấp cho người dùng trả phí
- Quản lý trial period và dùng thử tính năng
- Khôi phục trạng thái premium khi đăng nhập lại

#### 4. Giao Diện Người Dùng Premium
- Màn hình giới thiệu gói Premium
- Trình thanh toán và chọn gói
- Giao diện quản lý đăng ký hiện tại
- Thông báo hết hạn và gia hạn

#### 5. Analytics & Báo Cáo
- Theo dõi hiệu suất quảng cáo
- Thống kê chuyển đổi và doanh thu
- Phân tích hành vi người dùng (miễn phí vs premium)
- Báo cáo về hiệu quả của chiến lược kiếm tiền

## Luồng Xử Lý

1. **Khởi động ứng dụng**:
   - Kiểm tra trạng thái premium của người dùng
   - Tải quảng cáo banner nếu không phải người dùng premium
   - Chuẩn bị quảng cáo interstitial để sử dụng sau

2. **Hiển thị quảng cáo**:
   - Banner: Hiển thị ở cuối màn hình chính
   - Interstitial: Hiển thị khi chuyển màn hình (giới hạn tần suất)
   - Rewarded: Hiển thị khi người dùng muốn mở khóa tính năng tạm thời

3. **Mua Premium**:
   - Người dùng chọn gói Premium từ màn hình giới thiệu
   - Khởi tạo giao dịch với Google Play Billing
   - Xác nhận và xử lý thanh toán thành công
   - Cập nhật trạng thái Premium và loại bỏ quảng cáo

4. **Quản lý đăng ký**:
   - Kiểm tra trạng thái đăng ký khi khởi động ứng dụng
   - Nhận thông báo khi đăng ký hết hạn
   - Xử lý gia hạn tự động và thất bại thanh toán

## Cấu Trúc Gói Premium

### Các Gói Subscription
1. **Basic Premium (Hàng Tháng)**
   - Giá: $1.99/tháng
   - Tính năng: Loại bỏ quảng cáo, mở khóa các hiệu ứng cơ bản

2. **Pro Premium (Hàng Năm)**
   - Giá: $14.99/năm
   - Tính năng: Tất cả tính năng Basic + hiệu ứng nâng cao, tiết kiệm 37%

3. **Lifetime Premium (Mua đứt)**
   - Giá: $24.99 (một lần)
   - Tính năng: Tất cả tính năng Pro mãi mãi

### Gói Gia Đình
- Giá: $19.99/năm
- Chia sẻ cho tối đa 6 thành viên gia đình
- Tính năng theo dõi vị trí thời gian thực của các thành viên gia đình
- Thông báo khi thành viên kích hoạt SOS

## Mã Nguồn Chính

### Quản Lý Quảng Cáo

```java
public class AdsManager {
    private static final String TAG = "AdsManager";
    private static final String ADMOB_APP_ID = "ca-app-pub-XXXXXXXXXXXXXXXX~YYYYYYYYYY";
    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/ZZZZZZZZZZ";
    private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/WWWWWWWWWW";
    private static final String REWARDED_AD_UNIT_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/VVVVVVVVVV";
    
    private static final int INTERSTITIAL_AD_INTERVAL = 5; // Số lần mở màn hình để hiển thị quảng cáo
    
    private final Context context;
    private final BillingManager billingManager;
    private AdView bannerAdView;
    private InterstitialAd interstitialAd;
    private RewardedAd rewardedAd;
    private int screenOpenCount = 0;
    private boolean isEmergencyMode = false;
    
    public AdsManager(Context context, BillingManager billingManager) {
        this.context = context;
        this.billingManager = billingManager;
        
        // Khởi tạo SDK quảng cáo
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d(TAG, "AdMob SDK initialized");
                
                // Chuẩn bị quảng cáo sau khi khởi tạo
                loadBannerAd();
                loadInterstitialAd();
                loadRewardedAd();
            }
        });
    }
    
    // Tải và hiển thị Banner Ad
    public void loadBannerAd() {
        // Không tải quảng cáo nếu đang trong chế độ khẩn cấp hoặc người dùng premium
        if (isEmergencyMode || billingManager.isPremiumUser()) {
            return;
        }
        
        bannerAdView = new AdView(context);
        bannerAdView.setAdSize(AdSize.BANNER);
        bannerAdView.setAdUnitId(BANNER_AD_UNIT_ID);
        
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAdView.loadAd(adRequest);
    }
    
    // Lấy view quảng cáo Banner để hiển thị
    public AdView getBannerAdView() {
        return bannerAdView;
    }
    
    // Tải Interstitial Ad
    private void loadInterstitialAd() {
        if (isEmergencyMode || billingManager.isPremiumUser()) {
            return;
        }
        
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, INTERSTITIAL_AD_UNIT_ID, adRequest, 
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd ad) {
                    interstitialAd = ad;
                    interstitialAd.setFullScreenContentCallback(
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                interstitialAd = null;
                                // Tải lại quảng cáo mới
                                loadInterstitialAd();
                            }
                        });
                }
                
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    interstitialAd = null;
                    // Thử lại sau 1 phút
                    new Handler().postDelayed(() -> loadInterstitialAd(), 60000);
                }
            });
    }
    
    // Hiển thị Interstitial Ad
    public void showInterstitialAd(Activity activity) {
        if (isEmergencyMode || billingManager.isPremiumUser()) {
            return;
        }
        
        screenOpenCount++;
        
        if (screenOpenCount % INTERSTITIAL_AD_INTERVAL == 0) {
            if (interstitialAd != null) {
                interstitialAd.show(activity);
            } else {
                // Quảng cáo chưa sẵn sàng, tải lại
                loadInterstitialAd();
            }
        }
    }
    
    // Tải Rewarded Ad
    private void loadRewardedAd() {
        if (isEmergencyMode) {
            return;
        }
        
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, REWARDED_AD_UNIT_ID, adRequest,
            new RewardedAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull RewardedAd ad) {
                    rewardedAd = ad;
                }
                
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    rewardedAd = null;
                }
            });
    }
    
    // Hiển thị Rewarded Ad và nhận phần thưởng
    public void showRewardedAd(Activity activity, OnRewardEarnedListener listener) {
        if (rewardedAd == null) {
            listener.onRewardFailed();
            loadRewardedAd();
            return;
        }
        
        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                rewardedAd = null;
                loadRewardedAd();
            }
        });
        
        rewardedAd.show(activity, rewardItem -> {
            // Người dùng đã xem hết quảng cáo và nhận phần thưởng
            listener.onRewardEarned(rewardItem.getAmount());
        });
    }
    
    // Đặt trạng thái khẩn cấp - tắt tất cả quảng cáo
    public void setEmergencyMode(boolean isEmergency) {
        this.isEmergencyMode = isEmergency;
        
        if (isEmergency && bannerAdView != null) {
            bannerAdView.setVisibility(View.GONE);
        } else if (!isEmergency && bannerAdView != null && !billingManager.isPremiumUser()) {
            bannerAdView.setVisibility(View.VISIBLE);
        }
    }
    
    // Interface để thông báo khi người dùng nhận phần thưởng
    public interface OnRewardEarnedListener {
        void onRewardEarned(int amount);
        void onRewardFailed();
    }
}
```

### Quản Lý Thanh Toán

```java
public class BillingManager implements PurchasesUpdatedListener {
    private static final String TAG = "BillingManager";
    
    // SKU cho các gói subscription
    public static final String SKU_PREMIUM_MONTHLY = "aiflashlight_premium_monthly";
    public static final String SKU_PREMIUM_YEARLY = "aiflashlight_premium_yearly";
    public static final String SKU_PREMIUM_LIFETIME = "aiflashlight_premium_lifetime";
    public static final String SKU_FAMILY_PLAN = "aiflashlight_family_plan";
    
    private final Context context;
    private BillingClient billingClient;
    private List<SkuDetails> skuDetailsList = new ArrayList<>();
    private boolean isPremium = false;
    
    private Set<OnPurchaseCompletedListener> purchaseListeners = new HashSet<>();
    
    public BillingManager(Context context) {
        this.context = context;
        
        // Khởi tạo BillingClient
        billingClient = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases()
            .build();
        
        // Kết nối đến Google Play
        startConnection();
    }
    
    private void startConnection() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Kết nối thành công
                    Log.d(TAG, "Billing client connected");
                    
                    // Truy vấn thông tin sản phẩm
                    querySkuDetails();
                    
                    // Kiểm tra trạng thái đăng ký hiện tại
                    queryPurchases();
                }
            }
            
            @Override
            public void onBillingServiceDisconnected() {
                // Thử kết nối lại sau khi mất kết nối
                Log.d(TAG, "Billing service disconnected, retrying...");
                startConnection();
            }
        });
    }
    
    private void querySkuDetails() {
        List<String> skuList = Arrays.asList(
            SKU_PREMIUM_MONTHLY,
            SKU_PREMIUM_YEARLY,
            SKU_PREMIUM_LIFETIME,
            SKU_FAMILY_PLAN
        );
        
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList)
            .setType(BillingClient.SkuType.SUBS);
        
        // Truy vấn thông tin subscription
        billingClient.querySkuDetailsAsync(params.build(),
            (billingResult, skuDetailsList) -> {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    this.skuDetailsList = skuDetailsList;
                    Log.d(TAG, "SkuDetails query successful, products: " + skuDetailsList.size());
                }
            });
        
        // Truy vấn riêng cho sản phẩm một lần (lifetime)
        SkuDetailsParams.Builder paramsInapp = SkuDetailsParams.newBuilder();
        paramsInapp.setSkusList(Collections.singletonList(SKU_PREMIUM_LIFETIME))
            .setType(BillingClient.SkuType.INAPP);
            
        billingClient.querySkuDetailsAsync(paramsInapp.build(),
            (billingResult, inappSkuDetailsList) -> {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && 
                    inappSkuDetailsList != null) {
                    this.skuDetailsList.addAll(inappSkuDetailsList);
                }
            });
    }
    
    private void queryPurchases() {
        // Kiểm tra subscription hiện có
        billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS,
            (billingResult, purchases) -> {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    handlePurchases(purchases);
                }
            });
        
        // Kiểm tra mua một lần
        billingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP,
            (billingResult, purchases) -> {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    handlePurchases(purchases);
                }
            });
    }
    
    private void handlePurchases(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            // Kiểm tra và xử lý trạng thái mua
            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                if (!purchase.isAcknowledged()) {
                    acknowledgePurchase(purchase);
                }
                
                if (isSubscriptionSku(purchase.getSkus()) || 
                    purchase.getSkus().contains(SKU_PREMIUM_LIFETIME)) {
                    // Người dùng đã mua Premium
                    isPremium = true;
                }
            }
        }
    }
    
    private boolean isSubscriptionSku(List<String> skus) {
        return skus.contains(SKU_PREMIUM_MONTHLY) || 
               skus.contains(SKU_PREMIUM_YEARLY) || 
               skus.contains(SKU_FAMILY_PLAN);
    }
    
    private void acknowledgePurchase(Purchase purchase) {
        AcknowledgePurchaseParams acknowledgePurchaseParams =
            AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();
                
        billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                Log.d(TAG, "Purchase acknowledged: " + purchase.getOrderId());
            }
        });
    }
    
    // Bắt đầu luồng mua sản phẩm
    public void purchaseItem(Activity activity, String sku) {
        SkuDetails skuDetails = null;
        for (SkuDetails details : skuDetailsList) {
            if (details.getSku().equals(sku)) {
                skuDetails = details;
                break;
            }
        }
        
        if (skuDetails == null) {
            Log.e(TAG, "SkuDetails not found for: " + sku);
            return;
        }
        
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build();
            
        billingClient.launchBillingFlow(activity, billingFlowParams);
    }
    
    // Callback khi quá trình mua cập nhật
    @Override
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            // Xử lý mua hàng thành công
            handlePurchases(purchases);
            
            // Thông báo cho tất cả listener
            for (OnPurchaseCompletedListener listener : purchaseListeners) {
                listener.onPurchaseCompleted(true, "Purchase successful!");
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Người dùng hủy mua
            for (OnPurchaseCompletedListener listener : purchaseListeners) {
                listener.onPurchaseCompleted(false, "Purchase canceled");
            }
        } else {
            // Xử lý lỗi khác
            String errorMessage = "Error: " + billingResult.getDebugMessage();
            for (OnPurchaseCompletedListener listener : purchaseListeners) {
                listener.onPurchaseCompleted(false, errorMessage);
            }
        }
    }
    
    // Kiểm tra người dùng premium
    public boolean isPremiumUser() {
        return isPremium;
    }
    
    // Đăng ký listener
    public void addPurchaseListener(OnPurchaseCompletedListener listener) {
        purchaseListeners.add(listener);
    }
    
    // Hủy đăng ký listener
    public void removePurchaseListener(OnPurchaseCompletedListener listener) {
        purchaseListeners.remove(listener);
    }
    
    // Interface thông báo kết quả mua
    public interface OnPurchaseCompletedListener {
        void onPurchaseCompleted(boolean success, String message);
    }
}
```

## Tối Ưu Hóa

1. **Trải Nghiệm Người Dùng**:
   - Quảng cáo banner chỉ hiển thị ở cuối màn hình, không gây cản trở
   - Interstitial ads được kiểm soát tần suất để không gây khó chịu
   - Tự động tắt quảng cáo trong chế độ SOS và khẩn cấp
   - Rewarded ads chỉ xuất hiện khi người dùng chủ động chọn xem

2. **Hiệu Suất Quảng Cáo**:
   - Tải trước quảng cáo để giảm thời gian chờ đợi
   - Tối ưu hóa thời điểm hiển thị để tăng CTR
   - Phân tích điểm nóng (hotspot) và điều chỉnh vị trí quảng cáo
   - A/B testing với các định dạng quảng cáo khác nhau

3. **Chiến Lược Premium**:
   - Cân bằng giữa giá trị và giá tiền
   - Chính sách hoàn tiền rõ ràng
   - Cơ chế dùng thử và chiết khấu theo mùa
   - Tối ưu hóa tỷ lệ chuyển đổi (conversion rate)

## Kiểm Thử

1. **Thanh Toán**:
   - Kiểm tra toàn bộ luồng thanh toán với tài khoản test
   - Xác minh gia hạn tự động
   - Kiểm tra hủy đăng ký và khôi phục mua hàng
   - Xử lý các trường hợp lỗi thanh toán

2. **Quảng Cáo**:
   - Kiểm tra hiển thị quảng cáo trên các kích thước màn hình
   - Đảm bảo quảng cáo không xuất hiện cho người dùng premium
   - Xác minh tắt quảng cáo trong chế độ khẩn cấp
   - Kiểm tra rewarded ads và xác nhận phần thưởng

3. **Tích Hợp**:
   - Kiểm tra tích hợp với các module khác
   - Đảm bảo tính năng premium hoạt động chính xác
   - Kiểm tra trong điều kiện mạng khác nhau
   - Theo dõi hiệu suất và sử dụng pin

## Giao Diện

1. **Màn Hình Premium**:
   - Giới thiệu rõ ràng lợi ích của Premium
   - So sánh trực quan giữa các gói
   - Nút CTA nổi bật với giá hiển thị rõ ràng
   - Đường dẫn đến chính sách hoàn tiền và điều khoản

2. **Hiển Thị Quảng Cáo**:
   - Vị trí banner ở cuối màn hình, dễ dàng tránh chạm vô tình
   - Container quảng cáo với padding đủ lớn
   - Nhãn "Quảng Cáo" rõ ràng
   - Hoạt ảnh chuyển tiếp khi tải quảng cáo

3. **Quản Lý Đăng Ký**:
   - Hiển thị trạng thái đăng ký hiện tại
   - Ngày hết hạn và cài đặt gia hạn
   - Nút hủy đăng ký với hướng dẫn rõ ràng
   - Lịch sử thanh toán

## Hoàn Thành

Tính năng được coi là hoàn thành khi:
- Quảng cáo hiển thị chính xác và không làm gián đoạn trải nghiệm chính
- Luồng thanh toán và mua premium hoạt động mượt mà
- Tự động xác minh và khôi phục mua hàng khi cài đặt lại
- Cơ chế tắt quảng cáo trong tình huống khẩn cấp hoạt động tin cậy
- Tỷ lệ hiển thị quảng cáo và tỷ lệ chuyển đổi premium đạt mục tiêu đề ra
- Analytics cho thấy doanh thu ổn định từ cả quảng cáo và người dùng trả phí 