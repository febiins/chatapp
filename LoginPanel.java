import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn, registerBtn;
    private MainFrame frame;

    public LoginPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        form.add(new JLabel("Username:"));
        usernameField = new JTextField();
        usernameField.setColumns(10);
        form.add(usernameField);

        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        form.add(passwordField);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        loginBtn = new JButton("Login");
        registerBtn = new JButton("Register");

        buttons.add(loginBtn);
        buttons.add(registerBtn);
        add(buttons, BorderLayout.SOUTH);

        loginBtn.addActionListener(this);
        registerBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginBtn) {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (UserDAO.login(user, pass)) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                frame.openChat(user);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password");
            }
        } else {
            frame.showPage("Register");
        }
    }
}