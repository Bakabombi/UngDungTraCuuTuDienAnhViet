// DatabaseHelper.java
package tudien;

import java.sql.*;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/dictionarydb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       // đổi theo tài khoản MySQL của bạn
    private static final String PASS = "Nevergiveup8-8-"; // đổi theo mật khẩu MySQL của bạn

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // nạp driver
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Đăng nhập ---
    public static String checkLogin(String username, String password) {
        String sql = "SELECT role FROM users WHERE username=? AND password=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            } else {
                System.out.println("Sai username/password: " + username + " / " + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- Đăng ký ---
    public static boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'user')";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Lỗi: Người dùng đã tồn tại.");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Tra cứu từ ---
    public static String lookupWord(String word, String mode) {
        String sql;
        if ("EV".equals(mode)) {
            sql = "SELECT vietnamese, definition FROM dictionary WHERE english=?";
        } else { // VE
            sql = "SELECT english, definition FROM dictionary WHERE vietnamese=?";
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String result = "EV".equals(mode) ? rs.getString("vietnamese") : rs.getString("english");
                String definition = rs.getString("definition");
                return String.format("Nghĩa: %s\nĐịnh nghĩa: %s", result, definition);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Không tìm thấy từ!";
    }

    // --- Thêm từ ---
    public static boolean addWord(String english, String vietnamese, String definition) {
        String sql = "INSERT INTO dictionary (english, vietnamese, definition) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, english);
            ps.setString(2, vietnamese);
            ps.setString(3, definition);
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Lỗi: Từ đã tồn tại.");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Sửa từ ---
    public static boolean editWord(String word, String newMeaning, String newDefinition, String mode) {
        String sql;
        if ("EV".equals(mode)) {
            sql = "UPDATE dictionary SET vietnamese=?, definition=? WHERE english=?";
        } else { // VE
            sql = "UPDATE dictionary SET english=?, definition=? WHERE vietnamese=?";
        }
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if ("EV".equals(mode)) {
                ps.setString(1, newMeaning);
                ps.setString(2, newDefinition);
                ps.setString(3, word);
            } else {
                ps.setString(1, newMeaning); // new English
                ps.setString(2, newDefinition);
                ps.setString(3, word); // Vietnamese word
            }
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Xoá từ ---
    public static boolean deleteWord(String word, String mode) {
        String sql = "EV".equals(mode) ? "DELETE FROM dictionary WHERE english=?" : "DELETE FROM dictionary WHERE vietnamese=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, word);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
