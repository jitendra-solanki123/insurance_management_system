package insurance_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClaimForm extends JFrame implements ActionListener {
    private JTextField claimNumberField, policyIdField, claimAmountField, claimDateField, statusField;
    private JButton submitButton;

    public ClaimForm() {
        // Initialize JFrame settings
        setTitle("Claim Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        
        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Title
        JLabel titleLabel = new JLabel("Enter Claim Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 23));
        titleLabel.setBounds(150, 20, 300, 30);
        add(titleLabel);

        // Claim Number
        JLabel claimNumberLabel = new JLabel("Claim Number:");
        claimNumberLabel.setBounds(100, 70, 150, 25);
        add(claimNumberLabel);

        claimNumberField = new JTextField();
        claimNumberField.setBounds(250, 70, 200, 25);
        add(claimNumberField);

        // Policy ID
        JLabel policyIdLabel = new JLabel("Policy ID:");
        policyIdLabel.setBounds(100, 110, 150, 25);
        add(policyIdLabel);

        policyIdField = new JTextField();
        policyIdField.setBounds(250, 110, 200, 25);
        add(policyIdField);

        // Claim Amount
        JLabel claimAmountLabel = new JLabel("Claim Amount:");
        claimAmountLabel.setBounds(100, 150, 150, 25);
        add(claimAmountLabel);

        claimAmountField = new JTextField();
        claimAmountField.setBounds(250, 150, 200, 25);
        add(claimAmountField);

        // Claim Date
        JLabel claimDateLabel = new JLabel("Claim Date (YYYY-MM-DD):");
        claimDateLabel.setBounds(100, 190, 200, 25);
        add(claimDateLabel);

        claimDateField = new JTextField();
        claimDateField.setBounds(250, 190, 200, 25);
        add(claimDateField);

        // Status
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(100, 230, 150, 25);
        add(statusLabel);

        statusField = new JTextField();
        statusField.setBounds(250, 230, 200, 25);
        add(statusField);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 270, 200, 30);
        submitButton.addActionListener(this);
        add(submitButton);
        
        // Set frame visibility
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String claimNumber = claimNumberField.getText();
            String policyId = policyIdField.getText();
            String claimAmount = claimAmountField.getText();
            String claimDate = claimDateField.getText();
            String status = statusField.getText();

            // Validate inputs
            if (claimNumber.isEmpty() || policyId.isEmpty() || claimAmount.isEmpty() || claimDate.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/insurance_db"; // Your database name
            String user = "root"; // Your MySQL username
            String password = "Jitu@123"; // Your MySQL password

            // Use try-with-resources for automatic resource management
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO Claim (ClaimNumber, PolicyID, ClaimAmount, ClaimDate, Status) VALUES (?, ?, ?, ?, ?)")) {
                     
                pstmt.setString(1, claimNumber);
                pstmt.setInt(2, Integer.parseInt(policyId));
                pstmt.setBigDecimal(3, new java.math.BigDecimal(claimAmount));
                pstmt.setDate(4, java.sql.Date.valueOf(claimDate));
                pstmt.setString(5, status);

                // Execute update
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Claim added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                   // clearFields();
                    setVisible(false);
                    new AboutPage();
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add claim!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + sqlEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to clear input fields after successful submission
    private void clearFields() {
        claimNumberField.setText("");
        policyIdField.setText("");
        claimAmountField.setText("");
        claimDateField.setText("");
        statusField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClaimForm::new);
    }
}
