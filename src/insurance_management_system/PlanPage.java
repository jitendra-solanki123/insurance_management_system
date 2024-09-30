package insurance_management_system;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PlanPage extends JFrame {

    public PlanPage() {
        // Create the frame
        setTitle("Plan Page");
        setBounds(100, 100, 1600, 800);
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Get content pane
        Container c = getContentPane();
        c.setLayout(null);

        // First Panel for Title
        JPanel panel1 = new JPanel();
        panel1.setBounds(10, 7, 1580, 40);
        panel1.setBackground(new Color(156, 180, 204));
        c.add(panel1);

        JLabel label = new JLabel("Plans");
        label.setFont(new Font("Arial", Font.BOLD, 23));
        panel1.add(label);

        // Second Panel for Buttons 1,2,3,4,5
        JPanel panel2 = new JPanel();
        panel2.setBounds(10, 50, 1580, 40);
        panel2.setBackground(new Color(187, 156, 192));
        c.add(panel2);

        // Use BoxLayout to arrange buttons horizontally
        BoxLayout boxLayout = new BoxLayout(panel2, BoxLayout.X_AXIS);
        panel2.setLayout(boxLayout);

        JButton btnHome = new JButton("Home");
        JButton btnAbout = new JButton("About");
        JButton btnContact = new JButton("Contact Us");
       // JButton btnRegister = new JButton("Register");
        JButton btnLogout = new JButton("Logout");

        panel2.add(Box.createRigidArea(new Dimension(10, 0)));
        panel2.add(btnHome);
        panel2.add(Box.createRigidArea(new Dimension(10, 0)));
        panel2.add(btnAbout);
        panel2.add(Box.createRigidArea(new Dimension(10, 0)));
        panel2.add(btnContact);
        panel2.add(Box.createHorizontalGlue());
       // panel2.add(btnRegister);
        panel2.add(Box.createRigidArea(new Dimension(10, 0)));
        panel2.add(btnLogout);

        // Add action listeners for navigation buttons
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage(); // Redirect to HomePage
                dispose(); // Close current PlanPage
            }
        });

        btnAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutPage(); // Redirect to AboutPage
                dispose(); // Close current PlanPage
            }
        });
        
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
                new LogoutPage();
             
            }
        });

        // Other panels for different plans
        createPlanSections(c);

        setVisible(true);
    }

    private void createPlanSections(Container c) {
        // Add your plan sections here
        // Example: Children Plans
        JPanel panel3 = new JPanel();
        panel3.setBounds(10, 120, 1580, 40);
        panel3.setBackground(new Color(79, 117, 255));
        c.add(panel3);

        JLabel label2 = new JLabel("Children Plans");
        label2.setFont(new Font("Arial", Font.BOLD, 23));
        panel3.add(label2);

        JPanel panel4 = new JPanel();
        panel4.setBounds(10, 170, 1580, 80);
        panel4.setBackground(Color.GRAY);
        BoxLayout boxLayout1 = new BoxLayout(panel4, BoxLayout.X_AXIS);
        panel4.setLayout(boxLayout1);

        JButton btn6 = new JButton("932  \n New Children Money Back");
        panel4.add(Box.createRigidArea(new Dimension(250, 0)));
        panel4.add(btn6);

        JButton btn7 = new JButton("934  \n Jeeven Tarun");
        panel4.add(Box.createRigidArea(new Dimension(250, 0)));
        panel4.add(btn7);

        JButton btn8 = new JButton("874  \n Amrit Baal");
        panel4.add(Box.createRigidArea(new Dimension(250, 0)));
        panel4.add(btn8);

        c.add(panel4);

        // Continue to add other sections as needed...
        // Whole Life Plans
        // Single Premium Plans
        // Pension Plans

        // For example, Whole Life Plans
        JPanel panel5 = new JPanel();
        panel5.setBounds(10, 260, 1580, 40);
        panel5.setBackground(new Color(79, 117, 255));
        c.add(panel5);

        JLabel label3 = new JLabel("Whole Life Plans");
        label3.setFont(new Font("Arial", Font.BOLD, 23));
        panel5.add(label3);

        JPanel panel6 = new JPanel();
        panel6.setBounds(10, 310, 1580, 80);
        panel6.setBackground(Color.GRAY);
        BoxLayout boxLayout2 = new BoxLayout(panel6, BoxLayout.X_AXIS);
        panel6.setLayout(boxLayout2);

        JButton btn9 = new JButton("945  \n Jeevan Umang");
        panel6.add(Box.createRigidArea(new Dimension(430, 0)));
        panel6.add(btn9);

        JButton btn10 = new JButton("871  \n Jeevan Utsav");
        panel6.add(Box.createRigidArea(new Dimension(430, 0)));
        panel6.add(btn10);

        c.add(panel6);

        // Add other sections similarly
    }

    public static void main(String[] args) {
        new PlanPage();
    }
}

