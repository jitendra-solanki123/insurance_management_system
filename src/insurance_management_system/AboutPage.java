package insurance_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AboutPage extends JFrame {

    public AboutPage() {
        if (!UserSession.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "You must be logged in to access this page.", "Access Denied", JOptionPane.WARNING_MESSAGE);
            return; // Prevent access if not logged in
        }

        // Initialize JFrame settings
        setTitle("About Insurance Management System");
        setSize(1600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(156, 180, 204));
        titlePanel.setPreferredSize(new Dimension(600, 50));
        JLabel titleLabel = new JLabel("About Insurance Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(187, 156, 192));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton homeButton = new JButton("Home");
        JButton planButton = new JButton("Plan");
        JButton clientButton = new JButton("Client");
        JButton clientListButton = new JButton("Client List");
        JButton policyButton = new JButton("Policy");
        JButton policyListButton = new JButton("Policy List");
        JButton claimButton = new JButton("Claim");
        JButton claimListButton = new JButton("Claim List");
        JButton logOutButton = new JButton("Logout");

        // Add buttons to the panel with spacing
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(homeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(planButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(clientButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(clientListButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(policyButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(policyListButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(claimButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(claimListButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(logOutButton);

        add(buttonPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.LIGHT_GRAY);
//        JTextArea text = new JTextArea("Hello, I am Jitendra Solanki");
//        text.setEditable(false); // Make it non-editable
//        text.setLineWrap(true);
//        text.setWrapStyleWord(true);
//        contentPanel.add(text);

        // Image
        ImageIcon imageIcon = new ImageIcon("HomeLic.jpg"); // Path to your image
        JLabel imageLabel = new JLabel(imageIcon);
        contentPanel.add(imageLabel);
        add(contentPanel, BorderLayout.CENTER);

        // Action Listeners
        homeButton.addActionListener(e -> {
            setVisible(false); // Close the AboutPage
            new HomePage(); // Open HomePage
        });

        planButton.addActionListener(e -> {
            setVisible(false); // Close the AboutPage
            new PlanPage(); // Open PlanPage
        });

        clientButton.addActionListener(e -> {
            setVisible(false); // Close the AboutPage
            new ClientForm(); // Open Client Management Page
        });

        clientListButton.addActionListener(e -> {
            setVisible(false); // Close the AboutPage
            new ClientManagementApp(); // Open Client List Page
        });

        policyButton.addActionListener(e -> {
            setVisible(false); // Close the AboutPage
            new PolicyForm(); // Open Policy Page
        });

        policyListButton.addActionListener(e -> {
            setVisible(false); // Close the AboutPage
            new PolicyManager(); // Open Policy List Page
        });

        claimButton.addActionListener(e -> {
            setVisible(false); // Close the AboutPage
            new ClaimForm(); // Open Claim Page
        });

        claimListButton.addActionListener(e -> {
            setVisible(false); // Close the AboutPage
            new ClaimManager(); // Open Claim List Page
        });

        logOutButton.addActionListener(e -> {
            setVisible(false); // Close the AboutPage
            new LogoutPage(); // Open Logout Page
        });

        // Set frame visibility
        setVisible(true);
    }

    public static void main(String[] args) {
        new AboutPage();
    }
}
