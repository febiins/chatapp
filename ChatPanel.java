import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatPanel extends JPanel
        implements ActionListener, Runnable {

    private JTextArea chatArea;
    private JTextField messageField;
    private JList<String> userList, groupList;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ChatPanel(String username) {
        this.username = username;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Chat – " + username, JLabel.CENTER);
        add(title, BorderLayout.NORTH);

        // LEFT PANEL (Users + Groups)
        DefaultListModel<String> users =
                new DefaultListModel<>();
        users.addElement("rahul");
        users.addElement("anu");

        DefaultListModel<String> groups =
                new DefaultListModel<>();
        groups.addElement("java");
        groups.addElement("project");

        userList = new JList<>(users);
        groupList = new JList<>(groups);

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.add(new JScrollPane(userList));
        left.add(new JScrollPane(groupList));
        add(left, BorderLayout.WEST);

        // CHAT AREA
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // MESSAGE BOX
        messageField = new JTextField();
        JButton sendBtn = new JButton("Send");

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(messageField, BorderLayout.CENTER);
        bottom.add(sendBtn, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        sendBtn.addActionListener(this);
        messageField.addActionListener(this);

        // SOCKET
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

        String msg = messageField.getText();
        if (msg.isEmpty()) return;

        if (!userList.isSelectionEmpty()) {
            out.println(CryptoUtil.encrypt(
                "@" + userList.getSelectedValue() + " " + msg));
        }
        else if (!groupList.isSelectionEmpty()) {
            out.println(CryptoUtil.encrypt(
                "#" + groupList.getSelectedValue() + " " + msg));
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Select a user or group");
        }

        messageField.setText("");
    }

    @Override
    public void run() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                chatArea.append(
                    CryptoUtil.decrypt(msg) + "\n");
            }
        } catch (Exception ignored) {}
    }
}