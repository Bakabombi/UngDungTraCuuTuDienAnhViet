package tudien;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame {
    private JTextField txtWord, txtMeaning;
    private JTextArea txtResult;
    private JButton btnSearch, btnAdd, btnEdit, btnDelete, btnExit, btnLogout;
    private JComboBox<String> cbMode;
    private JLabel lblWord, lblMeaning;
    private String role = "user";

    public Client() {
        loginOrRegisterDialog();
        initUI();
    }

    private void initUI() {
        setTitle("Từ điển Anh - Việt (" + role + ")");
        setSize(700, 500);
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtWord = new JTextField();
        txtMeaning = new JTextField();
        txtResult = new JTextArea();
        txtResult.setEditable(false);
        txtResult.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtResult.setBackground(new Color(245, 245, 245));
        txtResult.setLineWrap(true);
        txtResult.setWrapStyleWord(true);

        cbMode = new JComboBox<>(new String[]{"Anh → Việt", "Việt → Anh"});
        cbMode.addActionListener(e -> updateLabels());

        JPanel panelTop = createTopPanel();
        JScrollPane scrollPane = new JScrollPane(txtResult);
        scrollPane.setBorder(new TitledBorder("📄 Kết quả tra cứu"));
        JPanel panelManage = createManagePanel();

        setLayout(new BorderLayout(10, 10));
        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelManage, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("🔍 Tra cứu từ"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblWord = new JLabel("Từ tiếng Anh:");
        gbc.gridx=0; gbc.gridy=0;
        panel.add(lblWord, gbc);

        gbc.gridx=1; gbc.weightx=1.0;
        panel.add(txtWord, gbc);

        gbc.gridx=2; gbc.weightx=0;
        panel.add(cbMode, gbc);

        btnSearch = new JButton("Tra cứu");
        btnSearch.addActionListener(e -> sendRequest("TRA", txtWord.getText().trim(), cbMode.getSelectedIndex()==0?"EV":"VE"));
        gbc.gridx=3;
        panel.add(btnSearch, gbc);

        return panel;
    }

    private JPanel createManagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("➕ Quản lý từ điển"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblMeaning = new JLabel("Nghĩa:");
        gbc.gridx=0; gbc.gridy=0;
        panel.add(lblMeaning, gbc);

        gbc.gridx=1; gbc.weightx=1.0;
        panel.add(txtMeaning, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        btnAdd = new JButton("Thêm từ");
        btnEdit = new JButton("Chỉnh sửa");
        btnDelete = new JButton("Xoá");
        btnLogout = new JButton("Đăng xuất");
        btnExit = new JButton("Thoát");

        if("admin".equals(role)){
            buttons.add(btnAdd); buttons.add(btnEdit); buttons.add(btnDelete);
            btnAdd.addActionListener(e -> addWord());
            btnEdit.addActionListener(e -> editWord());
            btnDelete.addActionListener(e -> deleteWord());
        } else {
            btnAdd.setEnabled(false); btnEdit.setEnabled(false); btnDelete.setEnabled(false);
            buttons.add(btnAdd);
        }
        buttons.add(btnLogout); buttons.add(btnExit);

        btnLogout.addActionListener(e -> { dispose(); SwingUtilities.invokeLater(Client::new); });
        btnExit.addActionListener(e -> { sendRequest("EXIT","",""); System.exit(0); });

        gbc.gridx=0; gbc.gridy=1; gbc.gridwidth=2;
        panel.add(buttons, gbc);

        return panel;
    }

    private void updateLabels() {
        if(cbMode.getSelectedIndex()==0){
            lblWord.setText("Từ tiếng Anh:");
            lblMeaning.setText("Nghĩa tiếng Việt:");
        } else {
            lblWord.setText("Từ tiếng Việt:");
            lblMeaning.setText("Nghĩa tiếng Anh:");
        }
    }

    // --- Sửa: sendRequest luôn nhận 3 tham số ---
    private void sendRequest(String type, String word, String meaningOrMode){
        try(Socket socket=new Socket("localhost",1234);
            ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois=new ObjectInputStream(socket.getInputStream())){

            Message req = new Message(type, word, meaningOrMode);
            oos.writeObject(req);
            oos.flush();

            if(!"EXIT".equals(type)){
                Message resp=(Message)ois.readObject();
                if("TRA".equals(type)) txtResult.setText(resp.meaning+"\n");
                else txtResult.append(resp.meaning+"\n");
            }

        }catch(Exception ex){ txtResult.append("⚠ Lỗi kết nối server!\n"); }
    }

    private void addWord(){
        String english = cbMode.getSelectedIndex()==0 ? txtWord.getText().trim() : txtMeaning.getText().trim();
        String vietnamese = cbMode.getSelectedIndex()==0 ? txtMeaning.getText().trim() : txtWord.getText().trim();
        if(english.isEmpty()||vietnamese.isEmpty()){ txtResult.append("⚠ Vui lòng nhập đủ cả hai ô để thêm từ.\n"); return; }
        sendRequest("ADD", english, vietnamese);
    }

    private void editWord(){
        if(txtWord.getText().trim().isEmpty()||txtMeaning.getText().trim().isEmpty()){
            txtResult.append("⚠ Vui lòng nhập từ và nghĩa mới để chỉnh sửa.\n"); return;
        }
        sendRequest("EDIT", txtWord.getText().trim(), txtMeaning.getText().trim());
    }

    private void deleteWord(){
        if(txtWord.getText().trim().isEmpty()){ txtResult.append("⚠ Vui lòng nhập từ cần xoá.\n"); return; }
        sendRequest("DELETE", txtWord.getText().trim(), "");
    }

    private void loginOrRegisterDialog(){
        String[] options={"Đăng nhập","Đăng ký","Thoát"};
        int choice=JOptionPane.showOptionDialog(null,"Chào mừng bạn đến với Từ điển!","Đăng nhập/Đăng ký",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
        if(choice==0) doLogin();
        else if(choice==1){ doRegister(); doLogin(); }
        else System.exit(0);
    }

    private void doLogin(){
        JTextField txtUser=new JTextField(); JPasswordField txtPass=new JPasswordField();
        Object[] msg={"Tên đăng nhập:",txtUser,"Mật khẩu:",txtPass};
        int opt=JOptionPane.showConfirmDialog(null,msg,"Đăng nhập",JOptionPane.OK_CANCEL_OPTION);
        if(opt==JOptionPane.OK_OPTION){
            try(Socket socket=new Socket("localhost",1234);
                ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream())){
                Message req=new Message("LOGIN",txtUser.getText(),new String(txtPass.getPassword()));
                oos.writeObject(req); oos.flush();
                Message resp=(Message)ois.readObject();
                if("LOGIN_OK".equals(resp.type)){ role=resp.meaning; JOptionPane.showMessageDialog(null,"Đăng nhập thành công! Quyền: "+role);}
                else { JOptionPane.showMessageDialog(null,resp.meaning); System.exit(0);}
            }catch(Exception e){ JOptionPane.showMessageDialog(null,"Không kết nối được server!"); System.exit(0);}
        } else System.exit(0);
    }

    private void doRegister(){
        JTextField txtUser=new JTextField(); JPasswordField txtPass=new JPasswordField();
        Object[] msg={"Tên đăng nhập:",txtUser,"Mật khẩu:",txtPass};
        int opt=JOptionPane.showConfirmDialog(null,msg,"Đăng ký",JOptionPane.OK_CANCEL_OPTION);
        if(opt==JOptionPane.OK_OPTION){
            try(Socket socket=new Socket("localhost",1234);
                ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream())){
                Message req=new Message("REGISTER",txtUser.getText(),new String(txtPass.getPassword()));
                oos.writeObject(req); oos.flush();
                Message resp=(Message)ois.readObject();
                JOptionPane.showMessageDialog(null,resp.meaning);
            }catch(Exception e){ JOptionPane.showMessageDialog(null,"Không kết nối được server!"); System.exit(0);}
        } else System.exit(0);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new Client().setVisible(true));
    }
}
