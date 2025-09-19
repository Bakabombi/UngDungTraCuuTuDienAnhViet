# 🎓 Faculty of Information Technology (DaiNam University)

# ỨNG DỤNG TRA CỨU TỪ ĐIỂN ANH-VIỆT

<p align="center">
  <img src="aiotlab_logo.png" alt="AIoT Lab" width="150"/>
  <img src="fitdnu_logo.png" alt="FIT DNU" width="150"/>
  <img src="dnu_logo.png" alt="Dai Nam University" width="150"/>
</p>

---

<p align="center">
  <a href="#">
    <img src="https://img.shields.io/badge/AiOTLab-green?style=for-the-badge" />
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Faculty%20of%20Information%20Technology-blue?style=for-the-badge" />
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/DaiNam%20University-orange?style=for-the-badge" />
  </a>
</p>

---

## 1. 📖 Giới thiệu

**Từ Điển Anh-Việt** là ứng dụng từ điển trực tuyến được phát triển theo mô hình **Client-Server**, cho phép người dùng tra cứu từ vựng Anh-Việt và Việt-Anh một cách nhanh chóng và hiệu quả. Ứng dụng hỗ trợ hiển thị đầy đủ thông tin bao gồm **nghĩa, định nghĩa**

---

## ✨ Tính năng nổi bật

- 🔹 Tra cứu từ **Anh → Việt** và **Việt → Anh**  
- 🔹 **Giao diện đồ họa thân thiện**  
- 🔹 **Log hoạt động chi tiết**  

---

## 2. 💻 Công nghệ sử dụng

<p align="center">
  <a href="#">
    <img width="57" height="28" alt="image" src="https://github.com/user-attachments/assets/961ef281-19f1-47fd-ad75-b5f940846e46" />
      </a>
  <a href="#">
    <img width="109" height="28" alt="image" src="https://github.com/user-attachments/assets/255ad8dc-b730-4f6d-8461-608bd0395b7f" />
      </a>
  <a href="#">
    <img width="69" height="28" alt="image" src="https://github.com/user-attachments/assets/7b74b430-dd1f-4c8d-8ab6-4355cf7f28be" />
  </a>
  <a href='#'>
    <img width="146" height="28" alt="image" src="https://github.com/user-attachments/assets/cadf7012-7926-4ece-9216-b1ba9bc45d18" />
  </a> 
</p>

---

### 🖥️ Client
- Java Swing cho giao diện đồ họa  
- Xử lý sự kiện, tra cứu và quản lý từ điển  

### 🗄️ Server
- Java Socket cho mô hình Client-Server  
- Xử lý yêu cầu, phản hồi từ Client  

### 📊 Data Management
- MySQL để lưu trữ từ điển và tài khoản người dùng  
- JDBC để kết nối và thực hiện các thao tác CRUD  

## 3. Các bước cài đặt
### 3.1. Yêu cầu hệ thống

🪟 Windows / Linux / macOS

☕ Java 8 trở lên (kiểm tra bằng java -version)

🐬 MySQL Server 5.7+ (khuyến nghị 8.0+)

### 3.2. Cài đặt và chuẩn bị CSDL

### 1. 📥 Tải và cài đặt MySQL Server từ trang chính thức: https://dev.mysql.com/downloads/mysql/

### 2. 🗃️ Tạo cơ sở dữ liệu và người dùng (ví dụ):

```sql
CREATE DATABASE IF NOT EXISTS dictionarydb
CREATE TABLE users
CREATE TABLE dictionary
```

### 3. 🧩 Khởi tạo bảng và dữ liệu mẫu bằng file schema.sql (nếu có):
   mysql -u root -p dictionarydb < database/schema.sql

### 3.3. Cấu hình kết nối CSDL

Cập nhật thông tin kết nối MySQL trong file DatabaseHelper.java:
```java
private static final String URL = "jdbc:mysql://localhost:3306/dictionarydb?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";           // tên người dùng MySQL
private static final String PASS = "PASSWORD";       // mật khẩu MySQL
```
### 3.4. Build và chạy ứng dụng

⚙️ Chạy thủ công bằng javac / java

🏗️ Biên dịch:
```java
javac -cp "lib/mysql-connector-java-8.0.33.jar" -d target/classes src/tudien/*.java
```

▶️ Chạy Server:
```java
java -cp "target/classes;lib/mysql-connector-java-8.0.33.jar" tudien.Server
```

▶️ Chạy Client:
```java
java -cp "target/classes;lib/mysql-connector-java-8.0.33.jar" tudien.Client
```

💡 Mặc định server lắng nghe trên localhost:1234 (thay đổi trong mã nguồn nếu cần).

---

#### 4. 🖼️ Một số hình ảnh hệ thống
💻 Đăng nhập/Đăng ký:

<img width="263" height="170" alt="image" src="https://github.com/user-attachments/assets/fae47891-3d99-4b4f-bbef-7ba0408e2be6" /> <br> <br>

<img width="263" height="167" alt="image" src="https://github.com/user-attachments/assets/ace53a5f-ad6d-4fad-8bf9-7b9539c0f341" />

🖥️ Giao diện Server:

<img width="686" height="493" alt="image" src="https://github.com/user-attachments/assets/7ed518db-a076-454d-a0bc-c492edffc506" />

💻 Giao diện Client:

<img width="689" height="488" alt="image" src="https://github.com/user-attachments/assets/ca3c1f3f-7e49-45d6-b1c9-833f4ef0e4fa" />

Giao diện dịch từ tiếng Anh sang tiếng Việt:

<img width="685" height="493" alt="image" src="https://github.com/user-attachments/assets/e098fb84-e5ab-4eb5-a126-c2a250f4ab19" />

Giao diện dịch từ tiếng Việt sang tiếng Anh:

<img width="686" height="494" alt="image" src="https://github.com/user-attachments/assets/c1341a24-26ee-4644-9bf5-fb3680cd7132" />

---

##### 📫 5. Liên hệ
Họ tên: Nguyễn Khôi Nguyên

Lớp: CNTT 16-01

✉️ Liên hệ email: ngkhoinguyen2912@gmail.com

---
