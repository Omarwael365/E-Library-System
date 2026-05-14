package GUI.CustomerPanels;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

import Classes.Books;
import Classes.Customers;
import GUI.MainFrame;
import GUI.stylesAndComponents.Labels;
import GUI.stylesAndComponents.StyledPanel;
import GUI.stylesAndComponents.TextFields;
import GUI.stylesAndComponents.Buttons;
import GUI.stylesAndComponents.ComboBoxes;
import GUI.stylesAndComponents.CustomDialogUtil;

public class AddReview extends JPanel {
    private MainFrame mainFrame;
    private Customers customer;

    public AddReview(MainFrame mainFrame, Customers customer) {
        this.mainFrame = mainFrame;
        this.customer = customer;

        // Main panel with vertical layout to allow natural scroll
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // some padding

        // Title
        JLabel title = new JLabel("Add Review");
        Labels.styleELibraryLabel(title, "title");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(20)); // spacing

        // Book ComboBox
        HashSet<String> addedBooks = new HashSet<>();
        JComboBox<Books> bookComboBox = new JComboBox<>();

        // Add borrowed books to the combo box
        for (Books book : customer.getBorrowedBookList()) {
            if (addedBooks.add(book.getBookName())) {
                bookComboBox.addItem(book);
            }
        }

        // Add owned books to the combo box
        for (Books book : customer.getOwnedBookList()) {
            if (addedBooks.add(book.getBookName())) {
                bookComboBox.addItem(book);
            }
        }

        // If no books are available to review, show a message
        if (bookComboBox.getItemCount() == 0) {
            JLabel noBooksLabel = new JLabel("You don't own or have not borrowed any books to review.");
            Labels.styleELibraryLabel(noBooksLabel, "title");
            noBooksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(noBooksLabel);
        } else {
            ComboBoxes.styleELibraryComboBox(bookComboBox);
            bookComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // responsive width
            mainPanel.add(bookComboBox);
            mainPanel.add(Box.createVerticalStrut(20));

            // Review Area
            JTextArea reviewArea = new JTextArea(4, 30);
            TextFields.styleELibraryTextArea(reviewArea);
            reviewArea.setLineWrap(true);
            reviewArea.setWrapStyleWord(true);

            // Declare scroll pane before the listener
            final JScrollPane reviewScrollPane = new JScrollPane(reviewArea);
            reviewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            reviewScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
            reviewScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300)); // limit max height
            reviewScrollPane.getVerticalScrollBar().setUnitIncrement(16);

            // Auto-expand height based on content
            reviewArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                private void resize() {
                    reviewArea.setSize(reviewArea.getWidth(), Short.MAX_VALUE);
                    int newHeight = reviewArea.getPreferredSize().height;
                    reviewArea.setPreferredSize(new Dimension(reviewArea.getWidth(), newHeight));
                    reviewArea.revalidate();
                    reviewScrollPane.revalidate();
                }

                @Override
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    resize();
                }

                @Override
                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    resize();
                }

                @Override
                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    resize();
                }
            });

            mainPanel.add(reviewScrollPane);
            mainPanel.add(Box.createVerticalStrut(20));

            // Submit Button
            JButton submitBtn = new JButton("Submit Review");
            Buttons.styleELibraryButton(submitBtn);
            submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(submitBtn);

            // Button Action
            submitBtn.addActionListener(e -> {
                Books selectedBook = (Books) bookComboBox.getSelectedItem();
                String reviewText = reviewArea.getText().trim();

                if (selectedBook == null) {
                    CustomDialogUtil.showStyledMessage(null, "Please select a book.", "Error");
                    return;
                }

                if (reviewText.length() < 20) {
                    CustomDialogUtil.showStyledMessage(null,
                            "Your review is too short. Please write at least 20 characters. Current length: " + reviewText.length(),
                            "Review Too Short");
                    return;
                }

                // Add the review for the selected book
                addReview(selectedBook, reviewText);
                CustomDialogUtil.showStyledMessage(null, "Review submitted successfully!", "Success");
                reviewArea.setText(""); // Clear the review area
            });
        }

        // Final outer scroll
        JScrollPane outerScrollPane = new JScrollPane(mainPanel);
        outerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outerScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        setLayout(new BorderLayout());
        add(outerScrollPane, BorderLayout.CENTER);
    }

    // Method to add a review for the selected book
    private void addReview(Books selectedBook, String reviewText) 
    {
        // Assuming the Books class has a method to add reviews
        Books.addReview(selectedBook, customer.getId(), reviewText);
    }
}
