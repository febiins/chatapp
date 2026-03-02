import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationPanel extends JPanel implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField, confirmField;
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
        usernameField = new JTextField(5);
        usernameField.setColumns(10);
        form.add(usernameField);

        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        form.add(passwordField);

        form.add(new JLabel("Confirm Password:"));
        confirmField = new JPasswordField();
        confirmField.setColumns(10);
        form.add(confirmField);

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
            String u = usernameField.getText();
            String p = new String(passwordField.getPassword());
            String c = new String(confirmField.getPassword());

            if (!p.equals(c)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match");
            } else if (UserDAO.register(u, p)) {
                JOptionPane.showMessageDialog(this, "Registration Successful");
                frame.showPage("Login");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Username already exists");
            }
        } else {
            frame.showPage("Login");
        }
    }
}