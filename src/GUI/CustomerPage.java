package GUI;

import javax.swing.*;
import Classes.Customers;
import GUI.CustomerPanels.AddReview;
import GUI.CustomerPanels.BrowseBooks;
import GUI.CustomerPanels.ReturnBorrowedBooks;
import GUI.CustomerPanels.CUpdateProfile;
import GUI.CustomerPanels.ViewBookReviews;
import GUI.CustomerPanels.ViewBorrowedBooks;
import GUI.CustomerPanels.ViewOrders;
import GUI.CustomerPanels.ViewOwnedBooks;
import GUI.PublishersPanels.PublishBook;
import GUI.stylesAndComponents.AsidePanel;
import GUI.stylesAndComponents.ClickableLabel;
import GUI.stylesAndComponents.StyledPanel;

import java.awt.*;
import java.awt.event.*;

public class CustomerPage extends JPanel {
    MainFrame mainFrame;
    Customers customer;

    CardLayout cardLayout;

    public CustomerPage(MainFrame mainframe, Customers customer) {
        this.mainFrame = mainframe;
        this.customer = customer;

        // Set the background color of this panel to a darker shade
        setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());


        // Main content with gradient background
        cardLayout = new CardLayout();
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(cardLayout);


        // Sidebar (aside)
        AsidePanel asidePanel = new AsidePanel();
        asidePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 20, 10, 10); // Spacing around components
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row counter
        int row = 0;

        // Create ClickableLabels for Customer actions
        ClickableLabel browseBooksLabel = new ClickableLabel("Browse Books");
        ClickableLabel viewOrdersLabel = new ClickableLabel("View Order");
        ClickableLabel viewReviewsLabel = new ClickableLabel("View Reviews");
        ClickableLabel addReviewLabel = new ClickableLabel("Add Review");
        ClickableLabel viewOwnedBooksLabel = new ClickableLabel("View Owned Books");
        ClickableLabel viewBorrowedBooksLabel = new ClickableLabel("View Borrowed Books");
        ClickableLabel returnBorrowedBooksLabel = new ClickableLabel("Return Borrowed Books");
        ClickableLabel updateProfileLabel = new ClickableLabel("Update Profile");
        ClickableLabel AICustomerSupport = new ClickableLabel("AI Customer Support");

        // Set preferred sizes for all labels
        ClickableLabel[] labels = {
            browseBooksLabel, viewOrdersLabel, viewReviewsLabel, addReviewLabel,
            viewOwnedBooksLabel, viewBorrowedBooksLabel, returnBorrowedBooksLabel, updateProfileLabel, AICustomerSupport
        };

        // Add labels to the sidebar
        for (ClickableLabel label : labels) {
            label.setPreferredSize(new Dimension(200, 60));
            gbc.gridy = row++;
            asidePanel.add(label, gbc);
        }

        // ActionListener for ClickableLabels
        ActionListener labelClickListener = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClickableLabel clickedLabel = (ClickableLabel) e.getSource();
                String labelText = clickedLabel.getText();

                // Handle actions based on label text
                switch (labelText) {
                    case "Browse Books":
                        
                        BrowseBooks pb = new BrowseBooks(mainFrame, customer);
                        mainPanel.add(pb, "browsebooks");
                        mainPanel.revalidate(); // ✅ Update layout
                        mainPanel.repaint();    // ✅ Refresh visuals

                        cardLayout.show(mainPanel, "browsebooks");

                        System.out.println("Browse Books clicked");
                        break;

                    case "View Order":
                        ViewOrders cp = new ViewOrders(mainFrame, customer);
                        mainPanel.add(cp, "checkoutpanel");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "checkoutpanel");
                        System.out.println("View Order clicked");
                        break;

                    case "View Reviews":
                        // Handle action for "View Reviews"
                        System.out.println("View Reviews clicked");
                        ViewBookReviews vbr = new ViewBookReviews(mainFrame, customer);
                        mainPanel.add(vbr, "viewbookreviews");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "viewbookreviews");
                        break;

                    case "Add Review":
                        AddReview ad = new AddReview(mainFrame, customer);
                        mainPanel.add(ad, "addreview");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "addreview");
                        System.out.println("Add Reviews clicked");
                        break;
                        
                    case "View Owned Books":
                        // Handle action for "View Owned Books"
                        ViewOwnedBooks vob = new ViewOwnedBooks(mainFrame, customer);
                        mainPanel.add(vob, "viewownedbooks");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "viewownedbooks");
                        System.out.println("View Owned Books clicked");
                        break;
                        
                    case "View Borrowed Books":
                        // Handle action for "View Borrowed Books"
                        ViewBorrowedBooks vbb = new ViewBorrowedBooks(mainFrame, customer);
                        mainPanel.add(vbb, "viewborrowedbooks");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "viewborrowedbooks");
                        System.out.println("View Borrowed Books clicked");
                        break;

                    case "Return Borrowed Books":
                        // Handle action for "Return Borrowed Books"
                        ReturnBorrowedBooks rbb = new ReturnBorrowedBooks(mainFrame, customer);
                        mainPanel.add(rbb, "returnborrowedbooks");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "returnborrowedbooks");
                        System.out.println("Return Borrowed Books clicked");
                        break;

                    case "Update Profile":
                        //Handle action for "Update Profile"
                        CUpdateProfile up = new CUpdateProfile(mainFrame, customer);
                        mainPanel.add(up, "updateprofile");
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        cardLayout.show(mainPanel, "updateprofile");
                        System.out.println("Update Profile clicked");
                        break;

                    case "AI Customer Support" :
                        new PythonAICustomerSupport(customer.getId());
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
