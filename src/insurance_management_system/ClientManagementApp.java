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

public class ClientManagementApp extends JFrame {
    static final String DB_URL = "jdbc:mysql://localhost:3306/insurance_db";
    static final String USER = "root"; 
    static final String PASSWORD = "Jitu@123"; 

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton updateButton, deleteButton,ExitButton ;

    public ClientManagementApp() {
        setTitle("Client Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setUndecorated(true);

        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Table model
        tableModel = new DefaultTableModel(new String[]{"Client ID", "Name", "Email", "Phone", "Address"}, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE); // Set table background
        loadData();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY); // Set button panel background
        ExitButton   = new JButton("Exit");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        
        updateButton.setBackground(Color.GREEN);
        deleteButton.setBackground(Color.red);
        
        buttonPanel.add(ExitButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        updateButton.addActionListener(new UpdateButtonAction());
        deleteButton.addActionListener(new DeleteButtonAction());
        ExitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				new AboutPage();
				
			}
        	
        	
        });
        setVisible(true);
    }

    // Load data from the database into the table
    void loadData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Client");
             ResultSet rs = pstmt.executeQuery()) {
            
            // Clear previous data
            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                    rs.getString("ClientID"),
                    rs.getString("Name"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Address")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Action for updating a client
    private class UpdateButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String clientId = tableModel.getValueAt(selectedRow, 0).toString();
                String name = tableModel.getValueAt(selectedRow, 1).toString();
                String email = tableModel.getValueAt(selectedRow, 2).toString();
                String phone = tableModel.getValueAt(selectedRow, 3).toString();
                String address = tableModel.getValueAt(selectedRow, 4).toString();

                // Open update form
                new UpdateClientForm(clientId, name, email, phone, address, ClientManagementApp.this);
            } else {
                JOptionPane.showMessageDialog(ClientManagementApp.this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Action for deleting a client
    private class DeleteButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String clientId = tableModel.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(ClientManagementApp.this, "Are you sure you want to delete this client?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteClient(clientId);
                }
            } else {
                JOptionPane.showMessageDialog(ClientManagementApp.this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Delete client from the database
    private void deleteClient(String clientId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Client WHERE ClientID = ?")) {
            pstmt.setString(1, clientId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Client deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadData(); // Refresh table data
            } else {
                JOptionPane.showMessageDialog(this, "Client deletion failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientManagementApp::new);
    }
}

class UpdateClientForm extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JTextArea addressField;
    private JButton updateButton;
    private String clientId;

    public UpdateClientForm(String clientId, String name, String email, String phone, String address, ClientManagementApp parent) {
        this.clientId = clientId;
        setTitle("Update Client");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent); // Center the form relative to parent

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
        
        // Create fields
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

        updateButton = new JButton("Update");
        updateButton.setBounds(150, 320, 180, 25);
        updateButton.addActionListener(e -> updateClient(parent));
        add(updateButton);

        setVisible(true);
    }

    private void updateClient(ClientManagementApp parent) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        // Basic validation
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(ClientManagementApp.DB_URL, ClientManagementApp.USER, ClientManagementApp.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE Client SET Name = ?, Email = ?, Phone = ?, Address = ? WHERE ClientID = ?")) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setString(5, clientId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Client updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                parent.loadData(); // Refresh data in the main table
                dispose(); // Close the update form
            } else {
                JOptionPane.showMessageDialog(this, "Client update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
