import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ChatPanel extends JPanel {

    JList<String> users, groups;
    JTextArea area;
    JTextField input;

    PrintWriter out;
    BufferedReader in;

    String target;
    boolean isGroup;

    public ChatPanel(String username) {

        setLayout(new BorderLayout());

        DefaultListModel<String> um = new DefaultListModel<>();
        um.addElement("sunny");
        um.addElement("rahul");

        DefaultListModel<String> gm = new DefaultListModel<>();
        gm.addElement("java");

        users = new JList<>(um);
        groups = new JList<>(gm);

        JPanel left = new JPanel(new GridLayout(2,1));
        left.add(new JScrollPane(users));
        left.add(new JScrollPane(groups));
        add(left, BorderLayout.WEST);

        area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        input = new JTextField();
        add(input, BorderLayout.SOUTH);

        users.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                target = users.getSelectedValue();
                isGroup = false;
                area.setText("Private chat with " + target + "\n");
            }
        });

        groups.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                target = groups.getSelectedValue();
                isGroup = true;
                area.setText("Group chat: " + target + "\n");
            }
        });

        input.addActionListener(e -> {
            if (target == null) return;

            String msg = input.getText();
            if (isGroup)
                out.println(CryptoUtil.encrypt("#" + target + " " + msg));
            else
                out.println(CryptoUtil.encrypt("@" + target + " " + msg));

            input.setText("");
        });

        connect(username);
    }

    void connect(String username) {
        try {
            Socket s = new Socket("localhost", 5000);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out.println(username);

            new Thread(() -> listen()).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void listen() {
        try {
            String enc;
            while ((enc = in.readLine()) != null) {
                String msg = CryptoUtil.decrypt(enc);
                SwingUtilities.invokeLater(() ->
                        area.append(msg + "\n"));
            }
        } catch (Exception e) {
            System.out.println("Disconnected");
        }
    }
}