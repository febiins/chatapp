import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    CardLayout cardLayout;
    JPanel container;

    public MainFrame() {
        setTitle("Chat App");
        setSize(420, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        LoginPanel loginPanel = new LoginPanel(this);
        RegistrationPanel registrationPanel = new RegistrationPanel(this);

        container.add(loginPanel, "Login");
        container.add(registrationPanel, "Register");

        add(container);
        cardLayout.show(container, "Login");
        setVisible(true);
    }

    public void showPage(String page) {
        cardLayout.show(container, page);
    }

    public void openChat(String username) {
        ChatPanel chatPanel = new ChatPanel(username);
        container.add(chatPanel, "Chat");
        showPage("Chat");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}