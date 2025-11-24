# SkyTravel - Website Bán Vé Máy Bay

## 📋 Mô tả dự án
SkyTravel là một website bán vé máy bay hoàn chỉnh với giao diện thân thiện người dùng, được xây dựng bằng HTML, CSS và JavaScript thuần.

## 🚀 Tính năng chính

### Trang chủ (index.html)
- **Tìm kiếm vé máy bay**: Form tìm kiếm với gợi ý thành phố tự động
- **Hiển thị kết quả**: Danh sách chuyến bay với thông tin chi tiết
- **Đặt vé**: Modal đặt vé với form thông tin khách hàng
- **Điểm đến phổ biến**: Hiển thị các điểm đến với giá vé
- **Tính năng nổi bật**: Giới thiệu các ưu điểm của dịch vụ

### Trang Giới thiệu (pages/about.html)
- **Câu chuyện công ty**: Lịch sử và sứ mệnh của SkyTravel
- **Giá trị cốt lõi**: Các nguyên tắc hoạt động
- **Đội ngũ**: Thông tin về ban lãnh đạo
- **Thành tựu**: Số liệu và thành tích của công ty

### Trang Dịch vụ (pages/services.html)
- **Đặt vé máy bay**: Dịch vụ chính của website
- **Đặt khách sạn**: Dịch vụ bổ sung
- **Thuê xe**: Dịch vụ di chuyển
- **Bảo hiểm du lịch**: Bảo vệ chuyến đi
- **Tour trọn gói**: Dịch vụ tour du lịch
- **Dịch vụ visa**: Hỗ trợ làm visa
- **Quy trình dịch vụ**: Hướng dẫn sử dụng
- **Đối tác**: Các hãng hàng không hợp tác

### Trang Liên hệ (pages/contact.html)
- **Form liên hệ**: Gửi tin nhắn cho công ty
- **Thông tin liên hệ**: Địa chỉ, điện thoại, email
- **Giờ làm việc**: Thời gian hỗ trợ khách hàng
- **Mạng xã hội**: Liên kết các trang mạng xã hội
- **FAQ**: Câu hỏi thường gặp
- **Bản đồ**: Vị trí văn phòng

### Trang Quản lý vé (pages/booking-management.html)
- **Tìm kiếm đặt vé**: Tìm kiếm bằng mã đặt vé hoặc họ tên
- **Xem chi tiết**: Hiển thị thông tin đặt vé đầy đủ
- **Sửa đổi**: Thay đổi thông tin đặt vé
- **Hủy vé**: Hủy đặt vé và hoàn tiền
- **Đặt lại**: Đặt lại chuyến bay đã hủy
- **Tính năng quản lý**: Các chức năng hỗ trợ

## 📁 Cấu trúc thư mục

```
WebDoAn/
├── index.html                 # Trang chủ
├── css/
│   └── styles.css            # File CSS chính
├── js/
│   └── script.js             # File JavaScript chính
├── pages/                    # Các trang con
│   ├── about.html           # Trang giới thiệu
│   ├── contact.html         # Trang liên hệ
│   ├── services.html        # Trang dịch vụ
│   └── booking-management.html # Trang quản lý vé
├── assets/                  # Thư mục tài nguyên
├── images/                  # Thư mục hình ảnh
└── README.md               # File hướng dẫn
```

## 🛠️ Công nghệ sử dụng

- **HTML5**: Cấu trúc trang web
- **CSS3**: Styling và responsive design
- **JavaScript**: Tương tác và chức năng động
- **Font Awesome**: Icon và biểu tượng
- **Google Fonts**: Font chữ đẹp
- **Unsplash**: Hình ảnh chất lượng cao

## 🎨 Thiết kế

### Màu sắc chính
- **Primary**: #667eea (Xanh dương)
- **Secondary**: #764ba2 (Tím)
- **Accent**: #ffd700 (Vàng)
- **Success**: #27ae60 (Xanh lá)
- **Danger**: #e74c3c (Đỏ)
- **Warning**: #f39c12 (Cam)

### Typography
- **Font chính**: Segoe UI, Tahoma, Geneva, Verdana, sans-serif
- **Font size**: Responsive từ 0.9rem đến 3.5rem

### Layout
- **Grid System**: CSS Grid và Flexbox
- **Responsive**: Mobile-first approach
- **Breakpoints**: 768px, 480px

## 🚀 Cách sử dụng

### 1. Mở trang web
```bash
# Mở file index.html trong trình duyệt
open index.html
```

### 2. Tìm kiếm vé máy bay
1. Điền thông tin điểm đi/đến
2. Chọn ngày đi/về
3. Chọn số hành khách
4. Nhấn "Tìm vé"

### 3. Đặt vé
1. Chọn chuyến bay phù hợp
2. Nhấn "Chọn vé này"
3. Điền thông tin khách hàng
4. Xác nhận đặt vé

### 4. Quản lý đặt vé
1. Vào trang "Quản lý vé"
2. Nhập mã đặt vé hoặc họ tên
3. Xem, sửa đổi hoặc hủy vé

## 📱 Responsive Design

Website được thiết kế responsive hoạt động tốt trên:
- **Desktop**: 1200px+
- **Tablet**: 768px - 1199px
- **Mobile**: < 768px

## 🔧 Tùy chỉnh

### Thay đổi màu sắc
Chỉnh sửa biến CSS trong file `css/styles.css`:
```css
:root {
    --primary-color: #667eea;
    --secondary-color: #764ba2;
    --accent-color: #ffd700;
}
```

### Thêm thành phố mới

[//]: # (Chỉnh sửa mảng `cities` trong file `js/script.js`:)
```javascript
const cities = [
    { name: 'Tên thành phố', code: 'MÃ', country: 'Quốc gia' },
    // ...
];
```

### Thêm hãng hàng không
```javascript
const airlines = [
    { name: 'Tên hãng', code: 'MÃ', logo: 'LOGO' },
    // ...
];
```

## 📞 Hỗ trợ

- **Hotline**: 1900-1234
- **Email**: support@skytravel.com
- **Địa chỉ**: La Uyên, Thượng Phúc, Hà Nội

## 📄 License

© 2024 SkyTravel. Tất cả quyền được bảo lưu.

## 🔄 Cập nhật

### Version 1.0.0
- ✅ Trang chủ với tìm kiếm vé máy bay
- ✅ Trang giới thiệu
- ✅ Trang dịch vụ
- ✅ Trang liên hệ
- ✅ Trang quản lý vé
- ✅ Responsive design
- ✅ Tương tác JavaScript

### Kế hoạch phát triển
- 🔄 Tích hợp API thực tế
- 🔄 Hệ thống thanh toán
- 🔄 Quản lý tài khoản người dùng
- 🔄 Ứng dụng mobile
- 🔄 Hệ thống đánh giá
