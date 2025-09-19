package tudien;

import java.io.*;
import java.net.*;

public class Server {
    public static final int PORT = 1234;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server đang chạy trên port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client kết nối: " + clientSocket.getInetAddress().getHostAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            Message request;
            while ((request = (Message) in.readObject()) != null) {
                System.out.println("Nhận từ client: " + request.type + " | " + request.word);
                Message response;

                switch (request.type) {
                    case "LOGIN":
                        String role = DatabaseHelper.checkLogin(request.word, request.meaning);
                        response = new Message(
                                role != null ? "LOGIN_OK" : "LOGIN_FAIL",
                                request.word,
                                role != null ? role : "Sai tài khoản hoặc mật khẩu!"
                        );
                        break;

                    case "REGISTER":
                        boolean registered = DatabaseHelper.registerUser(request.word, request.meaning);
                        response = new Message(
                                registered ? "REGISTER_OK" : "REGISTER_FAIL",
                                request.word,
                                registered ? "Đăng ký thành công!" : "Tên người dùng đã tồn tại!"
                        );
                        break;

                    case "TRA":
                        String traMode = (request.meaning == null || request.meaning.isEmpty()) ? "EV" : request.meaning;
                        String traResult = DatabaseHelper.lookupWord(request.word, traMode);
                        response = new Message("RESULT", request.word, traResult);
                        break;

                    case "ADD":
                        // request.meaning chứa nghĩa, request.word chứa từ
                        boolean added = DatabaseHelper.addWord(
                                request.word,
                                request.meaning,
                                "" // definition hiện chưa dùng
                        );
                        response = new Message(
                                "RESULT",
                                request.word,
                                added ? "✅ Đã thêm từ thành công!\n" + request.word + " → " + request.meaning
                                      : "⚠ Không thể thêm từ (có thể đã tồn tại)."
                        );
                        break;

                    case "EDIT":
                        // request.meaning chứa nghĩa mới, request.word chứa từ
                        boolean edited = DatabaseHelper.editWord(
                                request.word,
                                request.meaning,
                                "",   // definition mới
                                "EV"  // mặc định EV, Client sẽ gửi đúng mode nếu cần mở rộng
                        );
                        response = new Message(
                                "RESULT",
                                request.word,
                                edited ? "✏️ Đã chỉnh sửa thành công!\n" + request.word + " → " + request.meaning
                                       : "⚠ Không tìm thấy từ để chỉnh sửa!"
                        );
                        break;

                    case "DELETE":
                        boolean deleted = DatabaseHelper.deleteWord(request.word, "EV"); // mặc định EV
                        response = new Message(
                                "RESULT",
                                request.word,
                                deleted ? "🗑️ Đã xoá thành công từ: " + request.word
                                        : "⚠ Không tìm thấy từ để xoá!"
                        );
                        break;

                    case "EXIT":
                        System.out.println("Client thoát kết nối.");
                        return;

                    default:
                        response = new Message("RESULT", request.word, "⚠ Yêu cầu không hợp lệ!");
                        break;
                }

                out.writeObject(response);
                out.flush();
                System.out.println("Gửi phản hồi: " + response.type + " | " + response.word + " → " + response.meaning);
            }
        } catch (Exception e) {
            System.out.println("Client ngắt kết nối.");
        }
    }
}
