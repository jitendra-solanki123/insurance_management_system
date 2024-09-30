package insurance_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PolicyManager extends JFrame {
    static final String DB_URL = "jdbc:mysql://localhost:3306/insurance_db";
    static final String USER = "root"; 
    static final String PASSWORD = "Jitu@123"; 

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton updateButton, deleteButton,exitButton;

    public PolicyManager() {
        setTitle("Policy Management System");
        setSize(600, 400);
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.LIGHT_GRAY);

        tableModel = new DefaultTableModel(new String[]{"PolicyID", "PolicyNumber", "ClientID", "PolicyType", "StartDate", "EndDate", "Premium","Plan"}, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        loadData();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        exitButton  = new JButton("Exit");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        updateButton.setBackground(Color.GREEN);
        deleteButton.setBackground(Color.red);
        
        buttonPanel.add(exitButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(new UpdateButtonAction());
        deleteButton.addActionListener(new DeleteButtonAction());
        exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    
				setVisible(false);
				new AboutPage();
				
			}
        	
        });
        

        setVisible(true);
    }

    void loadData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Policy");
             ResultSet rs = pstmt.executeQuery()) {
            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                    rs.getString("PolicyID"),
                    rs.getString("PolicyNumber"),
                    rs.getString("ClientID"),
                    rs.getString("PolicyType"),
                    rs.getString("StartDate"),
                    rs.getString("EndDate"),
                    rs.getString("Premium"),
                    rs.getString("Plan"),
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class UpdateButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String policyID = tableModel.getValueAt(selectedRow, 0).toString();
                String policyNumber = tableModel.getValueAt(selectedRow, 1).toString();
                String clientID = tableModel.getValueAt(selectedRow, 2).toString();
                String policyType = tableModel.getValueAt(selectedRow, 3).toString();
                String startDate = tableModel.getValueAt(selectedRow, 4).toString();
                String endDate = tableModel.getValueAt(selectedRow, 5).toString();
                String premium = tableModel.getValueAt(selectedRow, 6).toString();
                String plan = tableModel.getValueAt(selectedRow, 7).toString();

                new UpdatePolicyForm(policyID, policyNumber, clientID, policyType, startDate, endDate, premium,plan, PolicyManager.this);
            } else {
                JOptionPane.showMessageDialog(PolicyManager.this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String policyID = tableModel.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(PolicyManager.this, "Are you sure you want to delete this policy?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deletePolicy(policyID);
                }
            } else {
                JOptionPane.showMessageDialog(PolicyManager.this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deletePolicy(String policyID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Policy WHERE PolicyID = ?")) {
            pstmt.setString(1, policyID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Policy deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Policy deletion failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PolicyManager::new);
    }
}

class UpdatePolicyForm extends JFrame {
    private JTextField policyNumberField, clientIDField, policyTypeField, premiumField, startDateField, endDateField;
    private JComboBox<String> planTypeComboBox, planNameComboBox;
    private JButton updateButton;
    private String policyID;

    public UpdatePolicyForm(String policyID, String policyNumber, String clientID, String policyType, String startDate, String endDate, String premium, String plan, PolicyManager parent) {
        this.policyID = policyID;
        setTitle("Update Policy");
        setSize(550, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(50, 0, 400, 40);
        JLabel titleLabel = new JLabel("Update Policy");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 23));
        titlePanel.add(titleLabel);
        add(titlePanel);

        JLabel policyNumberLabel = new JLabel("Policy Number:");
        policyNumberLabel.setBounds(100, 80, 150, 25);
        add(policyNumberLabel);

        policyNumberField = new JTextField(policyNumber);
        policyNumberField.setBounds(250, 80, 200, 25);
        add(policyNumberField);

        JLabel clientIDLabel = new JLabel("Client ID:");
        clientIDLabel.setBounds(100, 120, 150, 25);
        add(clientIDLabel);

        clientIDField = new JTextField(clientID);
        clientIDField.setBounds(250, 120, 200, 25);
        add(clientIDField);

        JLabel policyTypeLabel = new JLabel("Policy Type:");
        policyTypeLabel.setBounds(100, 160, 150, 25);
        add(policyTypeLabel);

        policyTypeField = new JTextField(policyType);
        policyTypeField.setBounds(250, 160, 200, 25);
        add(policyTypeField);

        JLabel startDateLabel = new JLabel("Start Date:");
        startDateLabel.setBounds(100, 200, 150, 25);
        add(startDateLabel);

        startDateField = new JTextField(startDate);
        startDateField.setBounds(250, 200, 200, 25);
        add(startDateField);

        JLabel endDateLabel = new JLabel("End Date:");
        endDateLabel.setBounds(100, 240, 150, 25);
        add(endDateLabel);

        endDateField = new JTextField(endDate);
        endDateField.setBounds(250, 240, 200, 25);
        add(endDateField);

        JLabel premiumLabel = new JLabel("Premium:");
        premiumLabel.setBounds(100, 280, 150, 25);
        add(premiumLabel);

        premiumField = new JTextField(premium);
        premiumField.setBounds(250, 280, 200, 25);
        add(premiumField);

        JLabel planTypeLabel = new JLabel("Plan Type:");
        planTypeLabel.setBounds(100, 320, 150, 25);
        add(planTypeLabel);

        // JComboBox for Plan Type
        planTypeComboBox = new JComboBox<>(new String[]{
            "Select Plan Type",
            "Money Back",
            "Pension Plan",
            "Children",
            "Single Premium",
            "Share Market (Nivece Plan)"
        });
        planTypeComboBox.setBounds(250, 320, 200, 25);
        planTypeComboBox.addActionListener(e -> updatePlanNames());
        add(planTypeComboBox);

        JLabel planNameLabel = new JLabel("Plan Name:");
        planNameLabel.setBounds(100, 360, 150, 25);
        add(planNameLabel);

        // JComboBox for Plan Names
        planNameComboBox = new JComboBox<>();
        planNameComboBox.setBounds(250, 360, 200, 25);
        add(planNameComboBox);

        updateButton = new JButton("Update");
        updateButton.setBounds(150, 400, 180, 25);
        updateButton.addActionListener(e -> updatePolicy(parent));
        add(updateButton);

        // Pre-select the existing plan in the combo boxes
        if (plan != null) {
            planTypeComboBox.setSelectedItem(getPlanType(policyType));
            planNameComboBox.setSelectedItem(plan);
        }

        setVisible(true);
    }

    private String getPlanType(String policyType) {
        // Map policy types to plan types
        switch (policyType) {
            case "Money Back":
                return "Money Back";
            case "Pension Plan":
                return "Pension Plan";
            case "Children":
                return "Children";
            case "Single Premium":
                return "Single Premium";
            case "Share Market (Nivece Plan)":
                return "Share Market (Nivece Plan)";
            default:
                return "Select Plan Type";
        }
    }

    private void updatePlanNames() {
        String selectedType = (String) planTypeComboBox.getSelectedItem();
        planNameComboBox.removeAllItems();

        if (selectedType == null || selectedType.equals("Select Plan Type")) {
            return;
        }

        switch (selectedType) {
            case "Money Back":
                planNameComboBox.addItem("Dhan Rekha");
                planNameComboBox.addItem("Bima Ratna");
                planNameComboBox.addItem("New Money Back (20 Year)");
                planNameComboBox.addItem("New Money Back (25 Year)");
                planNameComboBox.addItem("Jivan Siromani");
                planNameComboBox.addItem("Bima Shree");
                break;
            case "Pension Plan":
            	   planNameComboBox.addItem("Jeevan Shanti 858");
                   planNameComboBox.addItem("857 Jeevan Akshay VII");
                   planNameComboBox.addItem("872 Jeevan Dhara II ");
                break;
            case "Children":
                // Add children plans
            	planNameComboBox.addItem("932 New Children Money Back");
                planNameComboBox.addItem("934 Jeevan Tarun");
                planNameComboBox.addItem("874 Amrit Baal");
                break;
            case "Single Premium":
                // Add single premium plans
            	planNameComboBox.addItem("869 Dhan Vriddhi");
                planNameComboBox.addItem("916 Bima Bachat");
                planNameComboBox.addItem("917 Single Premium Endowment");
                break;
            case "Share Market (Nivece Plan)":
                // Add share market plans
                // planNameComboBox.addItem("Share Market Plan 1");
                break;
            default:
                break;
        }
    }

    private void updatePolicy(PolicyManager parent) {
        String policyNumber = policyNumberField.getText().trim();
        String clientID = clientIDField.getText().trim();
        String policyType = policyTypeField.getText().trim();
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();
        String premium = premiumField.getText().trim();
        String selectedPlan = (String) planNameComboBox.getSelectedItem();

        if (policyNumber.isEmpty() || clientID.isEmpty() || policyType.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || premium.isEmpty() || selectedPlan == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(PolicyManager.DB_URL, PolicyManager.USER, PolicyManager.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE Policy SET PolicyNumber = ?, ClientID = ?, PolicyType = ?, StartDate = ?, EndDate = ?, Premium = ?, Plan = ? WHERE PolicyID = ?")) {
            pstmt.setString(1, policyNumber);
            pstmt.setString(2, clientID);
            pstmt.setString(3, policyType);
            pstmt.setString(4, startDate);
            pstmt.setString(5, endDate);
            pstmt.setString(6, premium);
            pstmt.setString(7, selectedPlan);
            pstmt.setString(8, policyID); // Ensure the PolicyID is set

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Policy updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                parent.loadData(); // Refresh data in the main table
                dispose(); // Close the update form
            } else {
                JOptionPane.showMessageDialog(this, "Policy update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


