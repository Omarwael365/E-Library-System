package GUI;

import javax.swing.*;

import GUI.stylesAndComponents.*;

import java.awt.*;
import java.awt.event.*;

public class FrontPage extends JPanel implements ActionListener {
    JButton loginButton = new JButton("Login");
    JButton signupButton = new JButton("Signup");
    MainFrame mainFrame;

    public FrontPage(MainFrame frame) {
        this.mainFrame = frame;

        // Set the background color of this panel to a darker shade
        setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout()); // Using BorderLayout for the main layout

        // Left panel with BorderLayout
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(550, getHeight())); // Set width of the left panel to be smaller
        leftPanel.setBackground(new Color(30, 30, 30)); // Match background color to main panel

        // Load image for the left side
        ImageIcon image = new ImageIcon(getClass().getResource("/resources/images/jaredd-craig-HH4WBGNyltc-unsplash.jpg")); // create an image icon
        frame.setIconImage(image.getImage()); // change icon of the frame 

        Image resizedImage = image.getImage().getScaledInstance(550, 675, Image.SCALE_SMOOTH); // Resize image to smaller size
        image = new ImageIcon(resizedImage); // create a new image icon with resized image
        
        // Create a label to display the resized image
        JLabel leftLabel = new JLabel(image);

        // Add image label to the left panel
        leftPanel.add(leftLabel, BorderLayout.CENTER);

        // Right panel with GridBagLayout
        StyledPanel  rightPanel= new StyledPanel();
        rightPanel.setLayout(new GridBagLayout());

        // Title label
        JLabel titleLabel = new JLabel("Welcome to the E-Library");
        Labels.styleELibraryLabel(titleLabel,"title");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));

        // Style buttons using the custom Buttons class
        Buttons.styleELibraryButton(loginButton);
        Buttons.styleELibraryButton(signupButton);

        loginButton.setPreferredSize(new Dimension(150, 75));
        signupButton.setPreferredSize(new Dimension(150, 75));

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 40));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        // Layout constraints for right panel components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // column index
        gbc.gridy = 0; // row index
        gbc.insets = new Insets(20, 0, 50, 0); // adds padding
        gbc.anchor = GridBagConstraints.CENTER;

        // Add title label to the right panel
        rightPanel.add(titleLabel, gbc);

        gbc.gridy = 1; // Move to the next row for buttons
        rightPanel.add(buttonPanel, gbc);

        // Add the left panel and right panel to the main panel (FrontPage)
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Register button action listeners
        loginButton.addActionListener(this);
        signupButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == loginButton) 
        {
            mainFrame.showPage("login");
        }
        if (e.getSource() == signupButton) 
        {
            mainFrame.showPage("signup");
        }
    }
}