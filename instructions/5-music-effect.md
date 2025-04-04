# Module: Hiệu Ứng Âm Nhạc 🎵

## Tổng Quan
Module Hiệu Ứng Âm Nhạc cho phép đèn flash nhấp nháy theo nhịp điệu của âm nhạc đang phát từ thiết bị. Tính năng này tạo ra trải nghiệm ánh sáng trực quan, biến điện thoại thành thiết bị trình diễn ánh sáng thu nhỏ phù hợp cho tiệc tùng, hòa nhạc hoặc giải trí cá nhân.

## Yêu Cầu Kỹ Thuật

### Quyền Hệ Thống
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-feature android:name="android.hardware.camera.flash" />
```

### Thành Phần Cần Triển Khai

#### 1. Phân Tích Âm Thanh (AudioAnalyzer)
- Thu thập và phân tích dữ liệu âm thanh thời gian thực
- Trích xuất thông tin nhịp, tần số và cường độ
- Phát hiện beat và các thành phần quan trọng trong âm nhạc
- Tối ưu hóa hiệu suất với mức tiêu thụ tài nguyên thấp

#### 2. Bộ Tạo Mẫu Nhấp Nháy (PatternGenerator)
- Chuyển đổi dữ liệu âm thanh thành mẫu nhấp nháy
- Hỗ trợ đa dạng kiểu hiệu ứng (theo beat, theo tần số, theo giai điệu)
- Quản lý thời gian đáp ứng và độ nhạy
- Cơ chế làm mịn để tránh nhấp nháy quá nhanh hoặc không ổn định

#### 3. Điều Khiển Đèn Flash (FlashController)
- Điều khiển đèn flash với độ trễ tối thiểu
- Đồng bộ hóa chính xác với âm thanh
- Hỗ trợ cường độ nhấp nháy khác nhau (nếu thiết bị cho phép)
- Xử lý ngắt quãng và kiểm soát nhiệt độ

#### 4. Quản Lý Hiệu Ứng (EffectManager)
- Thư viện các hiệu ứng được định nghĩa trước
- Cho phép tùy chỉnh và lưu hiệu ứng mới
- Phân loại hiệu ứng theo thể loại nhạc
- Chuyển đổi mượt mà giữa các hiệu ứng

#### 5. Giao Diện Người Dùng
- Màn hình chính với trình phân tích trực quan
- Bộ chọn hiệu ứng và điều chỉnh tham số
- Điều khiển độ nhạy và cường độ
- Hiển thị thông tin về bài hát hiện tại

## Luồng Xử Lý

1. **Thu thập âm thanh**:
   - Khởi tạo AudioRecord hoặc MediaProjection (tùy vào nguồn)
   - Lấy mẫu âm thanh với tần suất phù hợp
   - Chia nhỏ dữ liệu thành các khung để phân tích

2. **Phân tích dữ liệu**:
   - Áp dụng Fast Fourier Transform (FFT) để chuyển đổi sang miền tần số
   - Phát hiện beat bằng thuật toán phát hiện đỉnh
   - Xác định cường độ tổng thể và phân bố năng lượng
   - Trích xuất thông tin nhịp điệu

3. **Tạo mẫu nhấp nháy**:
   - Áp dụng thuật toán tạo mẫu dựa trên dữ liệu âm thanh
   - Áp dụng bộ lọc làm mịn để tránh nhấp nháy hỗn loạn
   - Điều chỉnh theo độ nhạy người dùng đã cài đặt
   - Tính toán thời gian bật/tắt đèn flash

4. **Điều khiển đèn flash**:
   - Điều khiển camera API để bật/tắt đèn flash
   - Đồng bộ hóa thời gian để giảm độ trễ
   - Theo dõi nhiệt độ và hiệu suất
   - Thực hiện mẫu nhấp nháy đã tính toán

## Mã Nguồn Chính

### Phân Tích Âm Thanh

```java
public class AudioAnalyzer {
    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    
    private static final int FFT_SIZE = 1024;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(
            SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
    
    private AudioRecord audioRecord;
    private boolean isRecording = false;
    private Thread recordingThread;
    private FFT fft;
    private double[] amplitudes;
    private float[] fftBuffer;
    private BeatDetector beatDetector;
    
    public AudioAnalyzer() {
        fft = new FFT(FFT_SIZE);
        amplitudes = new double[FFT_SIZE / 2];
        fftBuffer = new float[FFT_SIZE];
        beatDetector = new BeatDetector();
    }
    
    public void start() {
        if (isRecording) return;
        
        // Khởi tạo AudioRecord
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE);
        
        if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
            // Xử lý lỗi khởi tạo
            return;
        }
        
        audioRecord.startRecording();
        isRecording = true;
        
        recordingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                processAudio();
            }
        }, "AudioProcessing Thread");
        
        recordingThread.start();
    }
    
    private void processAudio() {
        byte[] buffer = new byte[BUFFER_SIZE];
        
        while (isRecording) {
            int bytesRead = audioRecord.read(buffer, 0, BUFFER_SIZE);
            
            if (bytesRead > 0) {
                // Chuyển đổi bytes thành mẫu âm thanh
                for (int i = 0; i < FFT_SIZE && i < bytesRead / 2; i++) {
                    fftBuffer[i] = (float) (buffer[i * 2] | (buffer[i * 2 + 1] << 8)) / 32768.0f;
                }
                
                // Áp dụng cửa sổ Hamming
                for (int i = 0; i < FFT_SIZE; i++) {
                    fftBuffer[i] *= (0.54 - 0.46 * Math.cos(2 * Math.PI * i / (FFT_SIZE - 1)));
                }
                
                // Thực hiện FFT
                fft.forward(fftBuffer);
                
                // Tính biên độ
                for (int i = 0; i < FFT_SIZE / 2; i++) {
                    double re = fft.getReal(i);
                    double im = fft.getImaginary(i);
                    amplitudes[i] = Math.sqrt(re * re + im * im);
                }
                
                // Phát hiện beat
                boolean isBeat = beatDetector.detectBeat(amplitudes);
                
                // Thông báo kết quả phân tích
                notifyListeners(amplitudes, isBeat);
            }
        }
    }
    
    public void stop() {
        isRecording = false;
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
        if (recordingThread != null) {
            recordingThread.interrupt();
            recordingThread = null;
        }
    }
    
    // Các phương thức thông báo và đăng ký listener
}
```

### Phát Hiện Beat

```java
public class BeatDetector {
    private static final int MIN_INTERVAL = 250; // Khoảng cách tối thiểu giữa các beat (ms)
    private static final float BEAT_THRESHOLD = 1.5f; // Ngưỡng phát hiện beat
    
    private double[] energyHistory;
    private int historySize = 43; // Khoảng 1 giây @ 44.1kHz sample rate
    private int currentIndex = 0;
    private long lastBeatTime = 0;
    
    public BeatDetector() {
        energyHistory = new double[historySize];
        Arrays.fill(energyHistory, 0.0);
    }
    
    public boolean detectBeat(double[] amplitudes) {
        // Tính năng lượng trong dải tần số thấp (bass)
        double energy = calculateBassEnergy(amplitudes);
        
        // Cập nhật lịch sử năng lượng
        energyHistory[currentIndex] = energy;
        currentIndex = (currentIndex + 1) % historySize;
        
        // Tính năng lượng trung bình
        double averageEnergy = 0;
        for (int i = 0; i < historySize; i++) {
            averageEnergy += energyHistory[i];
        }
        averageEnergy /= historySize;
        
        // Tính độ lệch chuẩn
        double variance = 0;
        for (int i = 0; i < historySize; i++) {
            variance += Math.pow(energyHistory[i] - averageEnergy, 2);
        }
        variance /= historySize;
        double standardDeviation = Math.sqrt(variance);
        
        // Phát hiện beat
        boolean isBeat = false;
        long currentTime = System.currentTimeMillis();
        
        // Phát hiện khi năng lượng vượt quá ngưỡng và khoảng thời gian đủ lớn
        if (energy > averageEnergy + standardDeviation * BEAT_THRESHOLD) {
            if (currentTime - lastBeatTime > MIN_INTERVAL) {
                isBeat = true;
                lastBeatTime = currentTime;
            }
        }
        
        return isBeat;
    }
    
    private double calculateBassEnergy(double[] amplitudes) {
        // Tập trung vào dải tần số thấp (bass) - thường là dưới 150Hz
        // Giả sử FFT_SIZE = 1024 và SAMPLE_RATE = 44100
        // Chỉ số FFT cho 150Hz: 150 * 1024 / 44100 ≈ 3.5, làm tròn lên 4
        double energy = 0;
        for (int i = 1; i < 4; i++) {
            energy += amplitudes[i];
        }
        return energy;
    }
}
```

### Quản Lý Hiệu Ứng

```java
public class LightEffectManager {
    public enum EffectType {
        BEAT_SYNC,        // Đèn flash theo beat
        FREQUENCY_SYNC,   // Đèn flash theo dải tần số
        ENERGY_SYNC,      // Đèn flash theo cường độ âm thanh
        STROBE,           // Nhấp nháy đều
        PULSE,            // Đèn lên xuống theo nhịp
        RANDOM            // Nhấp nháy ngẫu nhiên
    }
    
    private FlashController flashController;
    private AudioAnalyzer audioAnalyzer;
    private EffectType currentEffect = EffectType.BEAT_SYNC;
    private boolean isActive = false;
    private float sensitivity = 0.7f; // 0.0-1.0
    
    // Cài đặt cho các hiệu ứng
    private int strobeSpeed = 300; // ms
    private int pulseSpeed = 500; // ms
    private float energyThreshold = 0.3f;
    
    public LightEffectManager(FlashController flashController) {
        this.flashController = flashController;
        this.audioAnalyzer = new AudioAnalyzer();
        
        // Đăng ký lắng nghe kết quả phân tích âm thanh
        audioAnalyzer.setOnAudioAnalyzedListener(new AudioAnalyzer.OnAudioAnalyzedListener() {
            @Override
            public void onAudioAnalyzed(double[] amplitudes, boolean isBeat) {
                processAudioData(amplitudes, isBeat);
            }
        });
    }
    
    public void start() {
        if (isActive) return;
        
        audioAnalyzer.start();
        isActive = true;
    }
    
    public void stop() {
        if (!isActive) return;
        
        audioAnalyzer.stop();
        flashController.turnOffFlash();
        isActive = false;
    }
    
    public void setEffectType(EffectType type) {
        this.currentEffect = type;
    }
    
    public void setSensitivity(float sensitivity) {
        this.sensitivity = Math.max(0.0f, Math.min(1.0f, sensitivity));
    }
    
    private void processAudioData(double[] amplitudes, boolean isBeat) {
        switch (currentEffect) {
            case BEAT_SYNC:
                if (isBeat) {
                    flashController.flashBurst(100); // 100ms flash
                }
                break;
                
            case FREQUENCY_SYNC:
                // Phản ứng với dải tần số trung bình
                double midFreqEnergy = calculateMidFrequencyEnergy(amplitudes);
                if (midFreqEnergy > sensitivity * 0.5) {
                    flashController.setFlashIntensity((float) Math.min(1.0, midFreqEnergy));
                } else {
                    flashController.turnOffFlash();
                }
                break;
                
            case ENERGY_SYNC:
                // Phản ứng với tổng năng lượng âm thanh
                double energy = calculateTotalEnergy(amplitudes);
                if (energy > energyThreshold * sensitivity) {
                    flashController.setFlashIntensity((float) Math.min(1.0, energy));
                } else {
                    flashController.turnOffFlash();
                }
                break;
                
            case STROBE:
                // Xử lý riêng trong một thread điều khiển stroboscope
                break;
                
            case PULSE:
                // Xử lý riêng trong một thread điều khiển pulse
                break;
                
            case RANDOM:
                // Xử lý ngẫu nhiên
                if (Math.random() < 0.05 * sensitivity) {
                    flashController.flashBurst((int)(Math.random() * 200));
                }
                break;
        }
    }
    
    private double calculateMidFrequencyEnergy(double[] amplitudes) {
        // Tập trung vào dải tần số trung bình (300Hz-2kHz)
        double energy = 0;
        // Chỉ số FFT cho 300Hz-2kHz: 300Hz ~ 7, 2kHz ~ 46 (với FFT_SIZE=1024, SAMPLE_RATE=44100)
        for (int i = 7; i < 46 && i < amplitudes.length; i++) {
            energy += amplitudes[i];
        }
        return energy / 39.0; // Chia cho số lượng băng tần để chuẩn hóa
    }
    
    private double calculateTotalEnergy(double[] amplitudes) {
        double energy = 0;
        for (int i = 1; i < amplitudes.length; i++) {
            energy += amplitudes[i];
        }
        return energy / amplitudes.length;
    }
}
```

## Tối Ưu Hóa

1. **Hiệu Suất Phân Tích**:
   - Sử dụng thuật toán FFT hiệu quả (JTransforms hoặc RenderScript)
   - Tối ưu hóa kích thước buffer và tần suất xử lý
   - Giảm độ phức tạp của phát hiện beat trong môi trường có tài nguyên hạn chế
   - Lazy initialization các thành phần nặng

2. **Tiêu Thụ Pin**:
   - Giám sát và điều chỉnh tốc độ xử lý dựa trên mức pin
   - Tắt cảm biến và xử lý khi ứng dụng không hiển thị
   - Tối ưu hóa thời gian bật đèn flash để giảm tiêu thụ pin
   - Chế độ tiết kiệm pin tự động

3. **Độ Trễ**:
   - Tối ưu hóa pipeline xử lý để giảm độ trễ
   - Sử dụng buffer nhỏ hơn cho độ trễ thấp hơn
   - Dự đoán beat để bù đắp độ trễ hệ thống
   - Đồng bộ hóa chính xác với âm thanh

## Kiểm Thử

1. **Độ Chính Xác**:
   - Kiểm tra với nhiều thể loại nhạc khác nhau
   - So sánh với phần mềm ánh sáng chuyên nghiệp
   - Đánh giá độ trễ giữa âm thanh và ánh sáng
   - Kiểm tra hiệu suất trên nhiều thiết bị khác nhau

2. **Hiệu Suất**:
   - Đo lường sử dụng CPU và RAM
   - Theo dõi nhiệt độ thiết bị trong thời gian dài
   - Kiểm tra tiêu thụ pin
   - Kiểm tra trong môi trường đa tác vụ

3. **Trải Nghiệm Người Dùng**:
   - Đánh giá cảm quan về sự đồng bộ
   - Kiểm tra độ nhạy và độ chính xác điều khiển
   - Thu thập phản hồi về các hiệu ứng khác nhau
   - Kiểm tra hoạt động với cả nhạc nội bộ và từ ứng dụng bên ngoài

## Giao Diện

1. **Màn Hình Chính**:
   - Hiển thị trực quan dữ liệu âm thanh (dạng sóng, phổ)
   - Nút chính để bắt đầu/dừng hiệu ứng
   - Chọn nhanh giữa các hiệu ứng phổ biến
   - Thanh trượt điều chỉnh độ nhạy

2. **Tùy Chọn Hiệu Ứng**:
   - Danh sách hiệu ứng được xếp theo nhóm
   - Xem trước hiệu ứng
   - Tùy chỉnh và lưu hiệu ứng mới
   - Các thông số điều chỉnh cho từng hiệu ứng

3. **Phân Tích Trực Quan**:
   - Hiển thị phổ tần số theo thời gian thực
   - Đánh dấu beat được phát hiện
   - Biểu diễn trực quan năng lượng âm thanh
   - Biểu đồ thống kê về đặc điểm âm nhạc

## Hoàn Thành

Tính năng được coi là hoàn thành khi:
- Đèn flash nhấp nháy đồng bộ với nhịp điệu âm nhạc với độ trễ không đáng kể
- Hỗ trợ nhiều kiểu hiệu ứng khác nhau
- Hoạt động tốt với cả nhạc được phát trên thiết bị và âm thanh xung quanh
- Tối ưu hóa sử dụng pin và nhiệt độ
- Giao diện người dùng trực quan và dễ sử dụng
- Đã kiểm tra trên nhiều thiết bị và thể loại nhạc khác nhau 