import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatPanel extends JPanel
        implements ActionListener, Runnable {

    private JTextArea chatArea;
    private JTextField messageField;
    private PrintWriter out;
    private BufferedReader in;

    public ChatPanel(String username) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Chat - " + username, JLabel.CENTER);
        add(title, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        messageField = new JTextField();
        JButton sendBtn = new JButton("Send");

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(messageField, BorderLayout.CENTER);
        bottom.add(sendBtn, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        sendBtn.addActionListener(this);
        messageField.addActionListener(this);

        try {
            Socket socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out.println(username);
            new Thread(this).start();
        } catch (Exception e) {
            chatArea.setText("Server not running");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        out.println(CryptoUtil.encrypt(messageField.getText()));
        messageField.setText("");
    }

    @Override
    public void run() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                chatArea.append(CryptoUtil.decrypt(msg) + "\n");
            }
        } catch (Exception ignored) {}
    }
}