package GUI.AdminPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Classes.Admin;
import Classes.Customers;
import GUI.MainFrame;
import GUI.stylesAndComponents.*;

public class ViewCustomers extends JPanel {

    MainFrame mainFrame;
    Admin admine;

    public ViewCustomers(MainFrame mainFrame, Admin admine) 
    {
        this.mainFrame = mainFrame;
        this.admine = admine;

        // Get already loaded customer list
        ArrayList<Customers> customerList = Customers.getCustomersList();

        // Main styled panel
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("All Customers");
        Labels.styleELibraryLabel(titleLabel, "title");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel to hold all customer cards
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));
        customerPanel.setBackground(new Color(245, 245, 245));

        // Add customer info cards
        for (Customers customer : customerList) {
            StyledPanel customerCard = new StyledPanel();
            customerCard.setLayout(new GridLayout(4, 1));
            customerCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            JLabel idLabel = new JLabel("ID: " + customer.getId());
            JLabel nameLabel = new JLabel("Name: " + customer.getName());
            JLabel emailLabel = new JLabel("Email: " + customer.getEmail());
            JLabel passwordLabel = new JLabel("Password: " + customer.getPassword());

            Labels.styleELibraryLabel(idLabel, "small");
            Labels.styleELibraryLabel(nameLabel, "small");
            Labels.styleELibraryLabel(emailLabel, "small");
            Labels.styleELibraryLabel(passwordLabel, "small");

            customerCard.add(idLabel);
            customerCard.add(nameLabel);
            customerCard.add(emailLabel);
            customerCard.add(passwordLabel);

            customerPanel.add(customerCard);
            customerPanel.add(Box.createVerticalStrut(10));
        }

        // ScrollPane for the list
        JScrollPane scrollPane = new JScrollPane(customerPanel);
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
