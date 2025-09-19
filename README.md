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

**Từ Điển Anh-Việt** là ứng dụng từ điển trực tuyến được phát triển theo mô hình **Client-Server**, cho phép người dùng tra cứu từ vựng Anh-Việt và Việt-Anh một cách nhanh chóng và hiệu quả. Ứng dụng hỗ trợ tính năng **gợi ý từ khóa thông minh**, hiển thị đầy đủ thông tin bao gồm **nghĩa, từ loại và ví dụ minh họa**.

---

## ✨ Tính năng nổi bật

- 🔹 Tra cứu từ **Anh → Việt** và **Việt → Anh**  
- 🔹 **Gợi ý từ khóa** khi nhập  
- 🔹 **Giao diện đồ họa thân thiện**  
- 🔹 **Log hoạt động chi tiết**  
- 🔹 **Hỗ trợ nhập dữ liệu từ file CSV**  

---

## 2. 💻 CÔNG NGHỆ SỬ DỤNG

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
CREATE DATABASE dictionarydb;
CREATE USER 'root'@'localhost' IDENTIFIED BY 'PASSWORD';
GRANT ALL PRIVILEGES ON dictionarydb.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
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

Bạn có thể chạy theo nhiều cách:

🚀 Cách A: Dùng script (Windows)

🧱 Build: chạy build.bat

🖧 Chạy Server: chạy run-server.bat

💻 Chạy Client: chạy run-client.bat

⚙️ Cách B: Chạy thủ công bằng javac / java

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
