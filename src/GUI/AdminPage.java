package GUI;

import javax.swing.*;
import Classes.Admin;
import GUI.AdminPanel.ManageBooks;
import GUI.AdminPanel.UpdateProfile;
import GUI.AdminPanel.ViewAdmins;
import GUI.AdminPanel.ViewCustomers;
import GUI.AdminPanel.ViewPublishers;
import GUI.CustomerPanels.ViewBookReviews;
import GUI.stylesAndComponents.AsidePanel;
import GUI.stylesAndComponents.ClickableLabel;
import GUI.stylesAndComponents.StyledPanel;

import java.awt.*;
import java.awt.event.*;

public class AdminPage extends JPanel {
    Admin admin;
    MainFrame mainFrame;

    CardLayout cardLayout;

    public AdminPage(MainFrame mainframe, Admin admin) {
        this.mainFrame = mainframe;
        this.admin = admin;

        // Set the background color of this panel to a darker shade
        setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        // Main content with gradient background
        cardLayout = new CardLayout();
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(cardLayout);

        // Sidebar (aside)
        AsidePanel asidePanel = new AsidePanel();
        asidePanel.setLayout(new GridBagLayout());  // Change to GridBagLayout for flexibility

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 20, 10, 10); // Spacing around components
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row counter
        int row = 0;

        // Create ClickableLabels for Admin actions
        ClickableLabel viewCustomersLabel = new ClickableLabel("View Customers");
        ClickableLabel viewPublishersLabel = new ClickableLabel("View Publishers");
        ClickableLabel viewAdminsLabel = new ClickableLabel("View Admins");
        ClickableLabel manageBooksLabel = new ClickableLabel("Manage Books");
        ClickableLabel editProfileLabel = new ClickableLabel("Edit Profile");

        // Set preferred sizes for all labels
        ClickableLabel[] labels = {
            viewCustomersLabel, viewPublishersLabel, 
            viewAdminsLabel, manageBooksLabel, editProfileLabel
        };

        // Add labels to the sidebar
        for (ClickableLabel label : labels) {
            label.setPreferredSize(new Dimension(200, 60));
            gbc.gridy = row++;
            asidePanel.add(label, gbc);
        }

        // ActionListener for ClickableLabels
        ActionListener labelClickListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClickableLabel clickedLabel = (ClickableLabel) e.getSource();
                String labelText = clickedLabel.getText();

                // Handle actions based on label text
                switch (labelText) 
                {
                    case "View Customers":
                        // Handle action for "View Customers"
                        ViewCustomers vr = new ViewCustomers(mainFrame, admin);
                        mainPanel.add(vr, "viewcustomers");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "viewcustomers");
                        System.out.println("View Customers clicked");
                        break;

                    case "View Publishers":
                        // Handle action for "View Publishers"
                        ViewPublishers vp = new ViewPublishers(mainFrame, admin);
                        mainPanel.add(vp, "viewpublishers");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "viewpublishers");
                        System.out.println("View Publishers clicked");
                        break;

                    case "View Admins":
                        // Handle action for "View Admins"
                        ViewAdmins va = new ViewAdmins(mainFrame, admin);
                        mainPanel.add(va, "viewadmins");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "viewadmins");
                        System.out.println("View Admins clicked");
                        break;

                    case "Manage Books":
                        // Handle action for "Manage Books"
                        ManageBooks mb = new ManageBooks(mainFrame, admin);
                        mainPanel.add(mb, "managebooks");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "managebooks");
                        System.out.println("Manage Books clicked");
                        break;

                    case "Edit Profile":
                        // Handle action for "Edit Profile"
                        UpdateProfile up = new UpdateProfile(mainFrame, admin);
                        mainPanel.add(up, "updateprofile");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "updateprofile");
                        System.out.println("Edit Profile clicked");
                        break;
                        
                    default:
                        break;
                }
            }
        };

        // Attach the listener to each label
        for (ClickableLabel label : labels) {
            label.addActionListener(labelClickListener);
        }

        // Split Pane (Aside on the left, Main on the right)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, asidePanel, mainPanel);
        splitPane.setDividerLocation(200); // Set reasonable width for sidebar
        splitPane.setDividerSize(5);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER); // ✅ Correct position
    }
}
