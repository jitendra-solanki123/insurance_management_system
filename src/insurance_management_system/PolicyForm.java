package insurance_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PolicyForm extends JFrame implements ActionListener {
    private JTextField policyNumberField, clientIdField, startDateField, endDateField, premiumField;
    private JComboBox<String> policyTypeComboBox, planComboBox;
    private JButton submitButton;

    public PolicyForm() {
        // Initialize JFrame settings
        setTitle("Policy Form");
        setSize(600, 440);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Title
        JLabel titleLabel = new JLabel("Enter Policy Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 23));
        titleLabel.setBounds(150, 20, 300, 30);
        add(titleLabel);

        // Policy Number
        JLabel policyNumberLabel = new JLabel("Policy Number:");
        policyNumberLabel.setBounds(100, 70, 150, 25);
        add(policyNumberLabel);

        policyNumberField = new JTextField();
        policyNumberField.setBounds(250, 70, 200, 25);
        add(policyNumberField);

        // Client ID
        JLabel clientIdLabel = new JLabel("Client ID:");
        clientIdLabel.setBounds(100, 110, 150, 25);
        add(clientIdLabel);

        clientIdField = new JTextField();
        clientIdField.setBounds(250, 110, 200, 25);
        add(clientIdField);

        // Policy Type
        JLabel policyTypeLabel = new JLabel("Policy Type:");
        policyTypeLabel.setBounds(100, 150, 150, 25);
        add(policyTypeLabel);

        String[] policyTypes = {
            "Money Back",
            "Pension Plan",
            "Children Plan",
            "Single Premium",
            "Short Market (Nivece Plan)"
        };
        policyTypeComboBox = new JComboBox<>(policyTypes);
        policyTypeComboBox.setBounds(250, 150, 200, 25);
        add(policyTypeComboBox);

        // Plan
        JLabel planLabel = new JLabel("Plan:");
        planLabel.setBounds(100, 190, 150, 25);
        add(planLabel);

        String[] plans = {
            "Indoman Plan - 914: New Indoment",
            "Indoman Plan - 915: New Jivan Anand",
            "Indoman Plan - 933: Jivan Lakhsya",
            "Indoman Plan - 936: Jivan Labh",
            "Indoman Plan - 943: Aadhar Stumbh",
            "Indoman Plan - 944: Aadhar Sila",
            "Indoman Plan - 860: Bima Jyoti",
            "Indoman Plan - 951: Micro Bajat",
            "Indoman Plan - 865: Dhan Shanchya",
            "Indoman Plan - 863: Jivan Ajad",
            "Money Back Plan - 863: Dhan Rekha",
            "Money Back Plan - 864: Bima Ratna",
            "Money Back Plan - 920: New Money Back (20 years)",
            "Money Back Plan - 921: New Money Back (25 years)",
            "Money Back Plan - 943: Jivan Siromani",
            "Money Back Plan - 948: Bima Shree"
        };
        planComboBox = new JComboBox<>(plans);
        planComboBox.setBounds(250, 190, 200, 25);
        add(planComboBox);

        // Start Date
        JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
        startDateLabel.setBounds(100, 230, 200, 25);
        add(startDateLabel);

        startDateField = new JTextField();
        startDateField.setBounds(250, 230, 200, 25);
        add(startDateField);

        // End Date
        JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD):");
        endDateLabel.setBounds(100, 270, 200, 25);
        add(endDateLabel);

        endDateField = new JTextField();
        endDateField.setBounds(250, 270, 200, 25);
        add(endDateField);

        // Premium
        JLabel premiumLabel = new JLabel("Premium:");
        premiumLabel.setBounds(100, 310, 150, 25);
        add(premiumLabel);

        premiumField = new JTextField();
        premiumField.setBounds(250, 310, 200, 25);
        add(premiumField);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 350, 200, 30);
        submitButton.addActionListener(this);
        add(submitButton);
        
        // Set frame visibility
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String policyNumber = policyNumberField.getText();
            String clientId = clientIdField.getText();
            String policyType = (String) policyTypeComboBox.getSelectedItem();
            String plan = (String) planComboBox.getSelectedItem();
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            String premium = premiumField.getText();

            // Validate inputs
            if (policyNumber.isEmpty() || clientId.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || premium.isEmpty()) {
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
                     "INSERT INTO Policy (PolicyNumber, ClientID, PolicyType, Plan, StartDate, EndDate, Premium) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                     
                pstmt.setString(1, policyNumber);
                pstmt.setInt(2, Integer.parseInt(clientId));
                pstmt.setString(3, policyType);
                pstmt.setString(4, plan);
                pstmt.setDate(5, java.sql.Date.valueOf(startDate));
                pstmt.setDate(6, java.sql.Date.valueOf(endDate));
                pstmt.setBigDecimal(7, new java.math.BigDecimal(premium));

                // Execute update
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Policy added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    setVisible(false);
                    new AboutPage();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add policy!", "Error", JOptionPane.ERROR_MESSAGE);
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
        policyNumberField.setText("");
        clientIdField.setText("");
        policyTypeComboBox.setSelectedIndex(0); // Reset to first item
        planComboBox.setSelectedIndex(0); // Reset to first item
        startDateField.setText("");
        endDateField.setText("");
        premiumField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PolicyForm::new);
    }
}
