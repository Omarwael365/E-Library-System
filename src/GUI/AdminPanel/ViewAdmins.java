package GUI.AdminPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Classes.Admin;
import GUI.MainFrame;
import GUI.stylesAndComponents.*;

public class ViewAdmins extends JPanel 
{
    MainFrame mainFrame;
    Admin admine;

    public ViewAdmins(MainFrame mainFrame, Admin admine) 
    {
        this.mainFrame = mainFrame;
        this.admine = admine;

        // Get already loaded admin list
        ArrayList<Admin> adminList = Admin.getAdminList();

        // Main styled panel
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("All Admins");
        Labels.styleELibraryLabel(titleLabel, "title");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel to hold all admin cards
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
        adminPanel.setBackground(new Color(245, 245, 245));

        // Add admin info cards
        for (Admin admin : adminList) {
            StyledPanel adminCard = new StyledPanel();
            adminCard.setLayout(new GridLayout(4, 1));
            adminCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            JLabel idLabel = new JLabel("ID: " + admin.getId());
            JLabel nameLabel = new JLabel("Name: " + admin.getName());
            JLabel emailLabel = new JLabel("Email: " + admin.getEmail());
            JLabel passwordLabel = new JLabel("Password: " + admin.getPassword());

            Labels.styleELibraryLabel(idLabel, "small");
            Labels.styleELibraryLabel(nameLabel, "small");
            Labels.styleELibraryLabel(emailLabel, "small");
            Labels.styleELibraryLabel(passwordLabel, "small");

            adminCard.add(idLabel);
            adminCard.add(nameLabel);
            adminCard.add(emailLabel);
            adminCard.add(passwordLabel);

            adminPanel.add(adminCard);
            adminPanel.add(Box.createVerticalStrut(10));
        }

        // ScrollPane for the list
        JScrollPane scrollPane = new JScrollPane(adminPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Final setup
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }
}
