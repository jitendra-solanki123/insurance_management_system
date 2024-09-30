package insurance_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ContactUsPage extends JFrame {
    private JTextField nameField, emailField;
    private JTextArea messageArea;
    private JButton submitButton;

    public ContactUsPage() {
        // Initialize JFrame settings
        setTitle("Contact Us");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(156, 180, 204));
        titlePanel.setPreferredSize(new Dimension(600, 50));
        JLabel titleLabel = new JLabel("Contact Us");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding for each component
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        contentPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        contentPanel.add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        contentPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        contentPanel.add(emailField, gbc);

        // Message
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel messageLabel = new JLabel("Message:");
        contentPanel.add(messageLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        messageArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        contentPanel.add(scrollPane, gbc);

        // Submit Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        submitButton = new JButton("Submit");
        contentPanel.add(submitButton, gbc);

        add(contentPanel, BorderLayout.CENTER);

        // Set frame visibility
        setVisible(true);

        // Add ActionListener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });
    }

    private void handleSubmit() {
        // Logic to handle form submission
        String name = nameField.getText();
        String email = emailField.getText();
        String message = messageArea.getText();

        // Example: Show a confirmation dialog (you can replace this with your actual submission logic)
        JOptionPane.showMessageDialog(this, "Contact Details Submitted:\nName: " + name + "\nEmail: " + email + "\nMessage: " + message);
    }
}

