import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationPanel extends JPanel implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerBtn, loginBtn;
    private MainFrame frame;

    public RegistrationPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Register", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        form.add(new JLabel("Username:"));
        usernameField = new JTextField();
        form.add(usernameField);

        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        form.add(passwordField);

        form.add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        form.add(confirmPasswordField);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        registerBtn = new JButton("Register");
        loginBtn = new JButton("Back to Login");

        buttons.add(registerBtn);
        buttons.add(loginBtn);
        add(buttons, BorderLayout.SOUTH);

        registerBtn.addActionListener(this);
        loginBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == registerBtn) {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            String confirm = new String(confirmPasswordField.getPassword());

            if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required");
            }
            else if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match");
            }
            else {
                if (UserDAO.register(user, pass)) {
                    JOptionPane.showMessageDialog(this,
                            "Registration Successful");
                    frame.showPage("Login");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Username already exists");
                }
            }
        }

        else if (e.getSource() == loginBtn) {
            frame.showPage("Login");
        }
    }
}