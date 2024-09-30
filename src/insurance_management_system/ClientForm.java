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

class ClientForm extends JFrame implements ActionListener {
    private JTextField clientIdField, nameField, emailField, phoneField;
    private JTextArea addressField;
    private JButton submitButton;

    public ClientForm() {
        // Initialize JFrame settings
        setTitle("Client  Form");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        
        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(50, 0, 400, 40);
        JLabel titleLabel = new JLabel("Client Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 23));
        titlePanel.add(titleLabel);
        add(titlePanel);

        // Client ID
        JLabel clientIdLabel = new JLabel("Client ID:");
        clientIdLabel.setBounds(100, 35, 100, 50);
        add(clientIdLabel);

        clientIdField = new JTextField();
        clientIdField.setBounds(250, 50, 200, 25);
        add(clientIdField);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(100, 80, 100, 50);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(250, 100, 200, 25);
        add(nameField);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(100, 135, 100, 50);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(250, 150, 200, 25);
        add(emailField);

        // Phone Number
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(100, 185, 100, 50);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(250, 200, 200, 25);
        add(phoneField);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(100, 230, 100, 50);
        add(addressLabel);

        addressField = new JTextArea();
        addressField.setBounds(250, 240, 200, 60);
        addressField.setLineWrap(true);
        addressField.setWrapStyleWord(true);
        add(addressField);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.setBounds(150, 320, 180, 25);
        add(submitButton);
        
        submitButton.addActionListener(this);

        // Set frame visibility
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String clientId = clientIdField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            // Validate inputs
            if (clientId.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/insurance_db"; // Your database name
            String user = "root"; // Your MySQL username
            String password = "Jitu@123"; // Your MySQL password

            // Use try-with-resources for automatic resource management
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Client (ClientID, Name, Email, Phone, Address) VALUES (?, ?, ?, ?, ?)")) {
                 
                pstmt.setString(1, clientId);
                pstmt.setString(2, name);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);
                pstmt.setString(5, address);

                // Execute update
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    //clearFields();
                    setVisible(false);
                    new AboutPage();
                } else {
                    JOptionPane.showMessageDialog(this, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + sqlEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    // Method to clear input fields after successful submission
    private void clearFields() {
        clientIdField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientForm::new);
    }
}
