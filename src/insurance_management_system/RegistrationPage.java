package insurance_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

class RegistrationPage extends JFrame implements ActionListener {
    private JTextField t1, t2, t3;
    private JPasswordField pass;
    private JButton btn;

    public RegistrationPage() {
        // Initialize JFrame settings
        setTitle("Registration Form");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Title Panel
        JPanel panel = new JPanel();
        panel.setBounds(50, 0, 400, 40);
        JLabel label1 = new JLabel("Registration Form");
        label1.setFont(new Font("Arial", Font.BOLD, 23));
        panel.add(label1);
        add(panel);

        // User Id
        JLabel label2 = new JLabel("User Id");
        label2.setBounds(100, 35, 100, 50);
        add(label2);

        t1 = new JTextField();
        t1.setBounds(250, 50, 200, 25);
        add(t1);

        // Username
        JLabel label3 = new JLabel("Username");
        label3.setBounds(100, 80, 100, 50);
        add(label3);

        t2 = new JTextField();
        t2.setBounds(250, 100, 200, 25);
        add(t2);

        // Email
        JLabel label4 = new JLabel("Email");
        label4.setBounds(100, 135, 100, 50);
        add(label4);

        t3 = new JTextField();
        t3.setBounds(250, 150, 200, 25);
        add(t3);

        // Password
        JLabel label5 = new JLabel("Password");
        label5.setBounds(100, 185, 100, 50);
        add(label5);

        pass = new JPasswordField();
        pass.setBounds(250, 200, 200, 25);
        add(pass);

        // Submit Button
        btn = new JButton("Submit");
        btn.setBounds(150, 280, 180, 25);
        add(btn);
        
        btn.addActionListener(this);

        // Set frame visibility
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            String userId = t1.getText();
            String username = t2.getText();
            String email = t3.getText();
            String password = new String(pass.getPassword());

            // Validate inputs
            if (userId.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long and contain a mix of letters and numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/insurance_db"; // replace with your database name
            String user = "root"; // replace with your MySQL username
            String pass = "Jitu@123"; // replace with your MySQL password

            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                // Establish connection
                conn = DriverManager.getConnection(url, user, pass);

                // Prepare SQL statement
                String sql = "INSERT INTO users (user_id, username, email, password) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userId);
                pstmt.setString(2, username);
                pstmt.setString(3, email);
                pstmt.setString(4, password);

                // Execute update
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + sqlEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                // Close resources
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    // Method to validate password strength
    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches("(?=.*[0-9])(?=.*[a-zA-Z]).*");
    }
}
