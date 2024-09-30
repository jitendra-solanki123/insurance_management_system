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

public class ClaimManager extends JFrame {
    static final String DB_URL = "jdbc:mysql://localhost:3306/insurance_db";
    static final String USER = "root";
    static final String PASSWORD = "Jitu@123";

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton updateButton, deleteButton, exitButton;

    public ClaimManager() {
        setTitle("Claim Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.LIGHT_GRAY);

        tableModel = new DefaultTableModel(new String[]{"ClaimID", "ClaimNumber", "PolicyID", "ClaimAmount", "ClaimDate", "Status"}, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        loadData();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        exitButton  =  new JButton("Exit");
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
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Claim");
             ResultSet rs = pstmt.executeQuery()) {
            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                    rs.getString("ClaimID"),
                    rs.getString("ClaimNumber"),
                    rs.getString("PolicyID"),
                    rs.getString("ClaimAmount"),
                    rs.getString("ClaimDate"),
                    rs.getString("Status") // Added status field
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
                String claimID = tableModel.getValueAt(selectedRow, 0).toString();
                String claimNumber = tableModel.getValueAt(selectedRow, 1).toString();
                String policyID = tableModel.getValueAt(selectedRow, 2).toString();
                String claimAmount = tableModel.getValueAt(selectedRow, 3).toString();
                String claimDate = tableModel.getValueAt(selectedRow, 4).toString();
                String status = tableModel.getValueAt(selectedRow, 5).toString();

                new UpdateClaimForm(claimID, claimNumber, policyID, claimAmount, claimDate, status, ClaimManager.this);
            } else {
                JOptionPane.showMessageDialog(ClaimManager.this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String claimID = tableModel.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(ClaimManager.this, "Are you sure you want to delete this claim?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteClaim(claimID);
                }
            } else {
                JOptionPane.showMessageDialog(ClaimManager.this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteClaim(String claimID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Claim WHERE ClaimID = ?")) {
            pstmt.setString(1, claimID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Claim deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadData(); // Refresh table data
            } else {
                JOptionPane.showMessageDialog(this, "Claim deletion failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClaimManager::new);
    }
}

class UpdateClaimForm extends JFrame {
    private JTextField claimNumberField, policyIDField, claimAmountField, claimDateField, statusField;
    private JButton updateButton;
    private String claimID;

    public UpdateClaimForm(String claimID, String claimNumber, String policyID, String claimAmount, String claimDate, String status, ClaimManager parent) {
        this.claimID = claimID;
        setTitle("Update Claim");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(50, 0, 400, 40);
        JLabel titleLabel = new JLabel("Update Claim");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 23));
        titlePanel.add(titleLabel);
        add(titlePanel);

        JLabel claimNumberLabel = new JLabel("Claim Number:");
        claimNumberLabel.setBounds(100, 80, 150, 25);
        add(claimNumberLabel);

        claimNumberField = new JTextField(claimNumber);
        claimNumberField.setBounds(250, 80, 200, 25);
        add(claimNumberField);

        JLabel policyIDLabel = new JLabel("Policy ID:");
        policyIDLabel.setBounds(100, 120, 150, 25);
        add(policyIDLabel);

        policyIDField = new JTextField(policyID);
        policyIDField.setBounds(250, 120, 200, 25);
        add(policyIDField);

        JLabel claimAmountLabel = new JLabel("Claim Amount:");
        claimAmountLabel.setBounds(100, 160, 150, 25);
        add(claimAmountLabel);

        claimAmountField = new JTextField(claimAmount);
        claimAmountField.setBounds(250, 160, 200, 25);
        add(claimAmountField);

        JLabel claimDateLabel = new JLabel("Claim Date:");
        claimDateLabel.setBounds(100, 200, 150, 25);
        add(claimDateLabel);

        claimDateField = new JTextField(claimDate);
        claimDateField.setBounds(250, 200, 200, 25);
        add(claimDateField);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(100, 240, 150, 25);
        add(statusLabel);

        statusField = new JTextField(status);
        statusField.setBounds(250, 240, 200, 25);
        add(statusField);

        updateButton = new JButton("Update");
        updateButton.setBounds(150, 320, 180, 25);
        updateButton.addActionListener(e -> updateClaim(parent));
        add(updateButton);

        setVisible(true);
    }

    private void updateClaim(ClaimManager parent) {
        String claimNumber = claimNumberField.getText().trim();
        String policyID = policyIDField.getText().trim();
        String claimAmount = claimAmountField.getText().trim();
        String claimDate = claimDateField.getText().trim();
        String status = statusField.getText().trim();

        if (claimNumber.isEmpty() || policyID.isEmpty() || claimAmount.isEmpty() || claimDate.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(ClaimManager.DB_URL, ClaimManager.USER, ClaimManager.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE Claim SET ClaimNumber = ?, PolicyID = ?, ClaimAmount = ?, ClaimDate = ?, Status = ? WHERE ClaimID = ?")) {
            pstmt.setString(1, claimNumber);
            pstmt.setString(2, policyID);
            pstmt.setString(3, claimAmount);
            pstmt.setString(4, claimDate);
            pstmt.setString(5, status);
            pstmt.setString(6, claimID);            
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Claim updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                parent.loadData(); // Refresh data in the main table
                dispose(); // Close the update form
            } else {
                JOptionPane.showMessageDialog(this, "Claim update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}