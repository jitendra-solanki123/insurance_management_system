package insurance_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class LoginPage extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton btnLogin;

    public LoginPage() {
        // Initialize JFrame settings
        setTitle("Login Form");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(50, 10, 300, 40);
        titlePanel.setBackground(new Color(156, 180, 204));
        JLabel titleLabel = new JLabel("Login Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 23));
        titlePanel.add(titleLabel);
        add(titlePanel);

        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 60, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 60, 200, 25);
        add(emailField);

        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 100, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 25);
        add(passwordField);

        // Login Button
        btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 150, 100, 25);
        add(btnLogin);

        // ActionListener for login button
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Set frame visibility
        setVisible(true);
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/insurance_db";
        String user = "root"; // Your MySQL username
        String pass = "Jitu@123"; // Your MySQL password
        String sql = "SELECT password FROM users WHERE email = ?"; // Select the hashed password

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");

                    if (verifyPassword(password, storedHash)) {
                        UserSession.setLoggedIn(true); // Set login status
                        JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new AboutPage(); // Redirect to AboutPage
                        setVisible(false); // Close LoginPage
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private boolean verifyPassword(String password, String storedHash) {
        // Simple verification of password - Replace with your hashing method if needed
        // Here, I'm assuming passwords are stored as plain text for simplicity.
        // In a real-world scenario, you'd want to use a secure hash.
        return password.equals(storedHash);
    }
}
