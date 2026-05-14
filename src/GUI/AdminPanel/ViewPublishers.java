package GUI.AdminPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Classes.Admin;
import Classes.Publisher;  // Assuming Publisher class exists
import GUI.MainFrame;
import GUI.stylesAndComponents.*;

public class ViewPublishers extends JPanel {

    Admin admine;
    MainFrame mainFrame;

    public ViewPublishers(MainFrame mainframe, Admin admine) 
    {
        this.mainFrame = mainframe;
        this.admine = admine;


        // Get already loaded publisher list
        ArrayList<Publisher> publisherList = Publisher.getPublisherList(); // Assuming getPublisherList() is a method in Publisher class

        // Main styled panel
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("All Publishers");
        Labels.styleELibraryLabel(titleLabel, "title");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel to hold all publisher cards
        JPanel publisherPanel = new JPanel();
        publisherPanel.setLayout(new BoxLayout(publisherPanel, BoxLayout.Y_AXIS));
        publisherPanel.setBackground(new Color(245, 245, 245));

        // Add publisher info cards
        for (Publisher publisher : publisherList) {
            StyledPanel publisherCard = new StyledPanel();
            publisherCard.setLayout(new GridLayout(4, 1));
            publisherCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            JLabel idLabel = new JLabel("ID: " + publisher.getId());
            JLabel nameLabel = new JLabel("Name: " + publisher.getName());
            JLabel emailLabel = new JLabel("Email: " + publisher.getEmail());
            JLabel passwordLabel = new JLabel("Password: " + publisher.getPassword());

            Labels.styleELibraryLabel(idLabel, "small");
            Labels.styleELibraryLabel(nameLabel, "small");
            Labels.styleELibraryLabel(emailLabel, "small");
            Labels.styleELibraryLabel(passwordLabel, "small");

            publisherCard.add(idLabel);
            publisherCard.add(nameLabel);
            publisherCard.add(emailLabel);
            publisherCard.add(passwordLabel);

            publisherPanel.add(publisherCard);
            publisherPanel.add(Box.createVerticalStrut(10));
        }

        // ScrollPane for the list
        JScrollPane scrollPane = new JScrollPane(publisherPanel);
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
