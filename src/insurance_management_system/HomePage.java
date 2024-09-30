package insurance_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HomePage extends JFrame {
    private JLabel imageLabel;
    private int currentImageIndex = 0;
    private BufferedImage backgroundImage;
    private BufferedImage footerImage;

    public HomePage() {
        // Load main background image and footer image
        try {
            backgroundImage = ImageIO.read(new File("Pm.jpg")); // Change the path to your main image
            footerImage = ImageIO.read(new File("file:///C:/Users/JITENDRA%20SOLANKI/eclipse-workspace/insurance_management_system/jitu(2).jpg")); // Change the path to your footer image
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the frame
        JFrame frame = new JFrame("Home Page");
        frame.setBounds(100, 100, 1600, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Get content pane
        Container c = frame.getContentPane();
        c.setLayout(null);

        // First Panel for Title
        JPanel panel1 = new JPanel();
        panel1.setBounds(10, 7, 1580, 40);
        panel1.setBackground(new Color(156, 180, 204, 200)); // Semi-transparent background
        c.add(panel1);

        JLabel label = new JLabel("Insurance Management System");
        label.setFont(new Font("Arial", Font.BOLD, 23));
        panel1.add(label);

        // Second Panel for Buttons
        JPanel panel2 = new JPanel();
        panel2.setBounds(10, 50, 1580, 40);
        panel2.setBackground(new Color(187, 156, 192, 200)); // Semi-transparent background
        c.add(panel2);

        // Use BoxLayout to arrange buttons horizontally
        BoxLayout boxLayout = new BoxLayout(panel2, BoxLayout.X_AXIS);
        panel2.setLayout(boxLayout);

        JButton btn1 = new JButton("Home");
        JButton btn2 = new JButton("About");
       // JButton btn3 = new JButton("Admin");
        JButton btn4 = new JButton("Register");
        JButton btn5 = new JButton("Login");

        panel2.add(Box.createRigidArea(new Dimension(10, 0)));
        panel2.add(btn1);
        panel2.add(Box.createRigidArea(new Dimension(10, 0)));
        panel2.add(btn2);
        panel2.add(Box.createRigidArea(new Dimension(10, 0)));
       // panel2.add(btn3);
        panel2.add(Box.createHorizontalGlue()); // Adjust spacing dynamically
        panel2.add(btn4);
        panel2.add(Box.createRigidArea(new Dimension(10, 0)));
        panel2.add(btn5);

        // Third Panel for Background Image Slider
        JPanel panel3 = new JPanel();
        panel3.setBounds(8, 110, 1580, 500);
        panel3.setLayout(new BorderLayout()); // Use BorderLayout for the image label
        c.add(panel3);

        // Image label for the slider
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        panel3.add(imageLabel, BorderLayout.CENTER);

        // Array of image paths
        String[] images = {"Pm.jpg", "Jeevan.jpg", "Anmol.jpg"}; // Add paths to your images
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex = (currentImageIndex + 1) % images.length; // Loop through images
                imageLabel.setIcon(new ImageIcon(images[currentImageIndex]));
            }
        });
        timer.start(); // Start the image slider

        // Action listener for the "Register" button
        btn4.addActionListener(e -> new RegistrationPage()); // Open the registration form

        // Action listener for the "Login" button
        btn5.addActionListener(e -> new LoginPage()); // Open the login form

        // Action listener for the "About" button
        btn2.addActionListener(e -> {
            frame.setVisible(false); // Close the current frame
            new AboutPage(); // Open the About page
        });

        // Action listener for the "Admin" button
//        btn3.addActionListener(e -> {
//            frame.setVisible(false); // Close the current frame
//            new AdminLoginApp(); // Open the Admin Login page
//        });

        // Footer Panel for Full-Size Background Image
        JPanel footerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (footerImage != null) {
                    g.drawImage(footerImage, 0, 30, getWidth(), 100, this); // Draw the footer image
                }
            }
        };
        footerPanel.setBounds(10, 610, 1580, 100); // Adjust the bounds as needed
        footerPanel.setOpaque(false); // Make the panel transparent to show the image
        c.add(footerPanel); // Add footer panel to the main content

        // Set frame visibility
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the background image
        }
    }

    public static void main(String args[]) {
        new HomePage();
    }
}
