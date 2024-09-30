package insurance_management_system;

import javax.swing.*;
import java.awt.*;

public class LogoutPage extends JFrame {
    
    public LogoutPage() {
        // Initialize JFrame settings
        setTitle("Logout");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Logout Confirmation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Message Panel
        JPanel messagePanel = new JPanel();
        messagePanel.add(new JLabel("Are you sure you want to log out?"));
        add(messagePanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton btnYes = new JButton("Yes");
        JButton btnNo = new JButton("No");

        btnYes.addActionListener(e -> {
            UserSession.setLoggedIn(false); // Update session status
            JOptionPane.showMessageDialog(this, "You have logged out successfully.", "Logout", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close the Logout page
            new LoginPage(); // Redirect to the Login page
        });

        btnNo.addActionListener(e -> dispose()); // Close the Logout page without action

        buttonPanel.add(btnYes);
        buttonPanel.add(btnNo);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame visibility
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogoutPage::new);
    }
}

