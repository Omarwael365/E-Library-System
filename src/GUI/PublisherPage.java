package GUI;

import javax.swing.*;
import Classes.Publisher;
import GUI.PublishersPanels.ChangeEmailandPassword;
import GUI.PublishersPanels.EditPublishedBooks;
import GUI.PublishersPanels.PublishBook;
import GUI.PublishersPanels.RemovePublishedBook;
import GUI.PublishersPanels.ViewPublishedBooks;
import GUI.PublishersPanels.ViewYourBookReviews;
import GUI.stylesAndComponents.AsidePanel;
import GUI.stylesAndComponents.ClickableLabel;
import GUI.stylesAndComponents.StyledPanel;

import java.awt.*;
import java.awt.event.*;

public class PublisherPage extends JPanel {
    MainFrame mainFrame;
    Publisher publisher;
    CardLayout cardLayout;

    public PublisherPage(MainFrame mainframe, Publisher publisher) {
        this.mainFrame = mainframe;
        this.publisher = publisher;

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
        gbc.insets = new Insets(20, 20, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row counter
        int row = 0;

        // Create ClickableLabels
        ClickableLabel publishBookLabel = new ClickableLabel("Publish Books");
        ClickableLabel viewPublishedBooksLabel = new ClickableLabel("View Published Books");
        ClickableLabel viewReviewsLabel = new ClickableLabel("View Your Book Reviews");
        ClickableLabel changeCredentialsLabel = new ClickableLabel("Change Email and Password");
        ClickableLabel editBooksLabel = new ClickableLabel("Edit Published Books");
        ClickableLabel deleteBooksLabel = new ClickableLabel("Remove a Published Book");

        // Set preferred sizes
        ClickableLabel[] labels = {
            publishBookLabel, viewPublishedBooksLabel, viewReviewsLabel, changeCredentialsLabel, editBooksLabel,
            deleteBooksLabel
        };

        for (ClickableLabel label : labels) {
            label.setPreferredSize(new Dimension(200, 60));
            gbc.gridy = row++;
            asidePanel.add(label, gbc);
        }

        // ActionListener for ClickableLabels
        ActionListener labelClickListener = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                ClickableLabel clickedLabel = (ClickableLabel) e.getSource();
                String labelText = clickedLabel.getText();

                switch (labelText)
                {
                    case "Publish Books":

                        PublishBook pb = new PublishBook(mainFrame, publisher);
                        mainPanel.add(pb, "publishbook");
                        mainPanel.revalidate(); // ✅ Update layout
                        mainPanel.repaint();    // ✅ Refresh visuals

                        cardLayout.show(mainPanel, "publishbook");
                        System.out.println("Publish Books clicked");
                        break;

                    case "View Published Books":

                        ViewPublishedBooks vpb = new ViewPublishedBooks(mainFrame, publisher);
                        mainPanel.add(vpb, "viewPublishedBook");
                        mainPanel.revalidate(); // ✅ Update layout
                        mainPanel.repaint();    // ✅ Refresh visuals
                        cardLayout.show(mainPanel, "viewPublishedBook");

                        System.out.println("View Published Books clicked");
                        break;
                        
                    case "View Your Book Reviews":

                        ViewYourBookReviews vpbR = new ViewYourBookReviews(mainFrame, publisher);
                        mainPanel.add(vpbR, "viewPublishedBookReviews");
                        mainPanel.revalidate(); // ✅ Update layout
                        mainPanel.repaint();    // ✅ Refresh visuals
                        cardLayout.show(mainPanel, "viewPublishedBookReviews");

                        System.out.println("View Reviews clicked");

                        break;
                    case "Change Email and Password":

                        ChangeEmailandPassword ceap = new ChangeEmailandPassword(mainFrame, publisher);
                        mainPanel.add(ceap, "changeEmail&Password");
                        mainPanel.revalidate(); // ✅ Update layout
                        mainPanel.repaint();    // ✅ Refresh visuals
                        cardLayout.show(mainPanel, "changeEmail&Password");

                        System.out.println("Change Credentials clicked");
                        break;

                    case "Edit Published Books":

                        EditPublishedBooks epb = new EditPublishedBooks(mainFrame, publisher);
                        mainPanel.add(epb, "editPublishedBooks");
                        mainPanel.revalidate(); // ✅ Update layout
                        mainPanel.repaint();    // ✅ Refresh visuals
                        cardLayout.show(mainPanel, "editPublishedBooks");

                        System.out.println("Edit Published Books clicked");
                        break;

                    case "Remove a Published Book":

                        RemovePublishedBook rpb = new RemovePublishedBook(mainFrame, publisher);
                        mainPanel.add(rpb, "removePublishedBook");
                        mainPanel.revalidate(); // ✅ Update layout
                        mainPanel.repaint();    // ✅ Refresh visuals
                        cardLayout.show(mainPanel, "removePublishedBook");

                        System.out.println("Remove a Published Book clicked");
                        break;

                    default:
                        break;
                }
            }
        };
 
        for (ClickableLabel label : labels) {
            label.addActionListener(labelClickListener);
        }

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, asidePanel, mainPanel);
        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(5);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);
    }
}
