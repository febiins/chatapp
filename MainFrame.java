import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel container;

    public MainFrame() {
        setTitle("Chat App");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        container.add(new LoginPanel(this), "Login");
        container.add(new RegistrationPanel(this), "Register");

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