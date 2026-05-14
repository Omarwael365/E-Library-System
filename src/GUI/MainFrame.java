package GUI;

import javax.swing.*;

import GUI.stylesAndComponents.Menu;

import java.awt.*;

public class MainFrame extends JFrame 
{
    CardLayout cardLayout;
    public JPanel mainPanel;

    public MainFrame() 
    {
        setTitle("E-Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);  // remove window borders
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(55, 89, 182));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create the menu bar and set it to this frame
        Menu menu = new Menu(); // Create instance
        menu.createMenuBar(this); // Get the styled menu bar

        // Add panels (pages)
        FrontPage frontPage = new FrontPage(this);
        LoginPage loginPage = new LoginPage(this);
        SignupPage signuppage = new SignupPage(this);


        mainPanel.add(frontPage, "front");
        mainPanel.add(loginPage, "login");
        mainPanel.add(signuppage,"signup");


        add(mainPanel);
        setVisible(true);

        // Show front page initially
        cardLayout.show(mainPanel, "front");

    }

    // Method to switch views
    public void showPage(String pageName) 
    {
        cardLayout.show(mainPanel, pageName);
    }
}
