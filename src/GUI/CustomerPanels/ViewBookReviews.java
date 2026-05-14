package GUI.CustomerPanels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import Classes.Books;
import Classes.Customers;
import Classes.Reviews;
import GUI.MainFrame;
import GUI.stylesAndComponents.*;

public class ViewBookReviews extends JPanel {
    private MainFrame mainFrame;
    private Customers customer;

    public ViewBookReviews(MainFrame mainFrame, Customers customer) {
        this.mainFrame = mainFrame;
        this.customer = customer;

        // Main styled panel
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel titleLabel = new JLabel("View Book Reviews");
        Labels.styleELibraryLabel(titleLabel, "title");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++;

        // ComboBox for selecting books
        JComboBox<String> bookComboBox = new JComboBox<>();
        HashMap<Integer, Books> allBooks = Books.getAllBooksList();

        for (Books book : allBooks.values()) {
            if (book != null && book.getBookName() != null) {
                bookComboBox.addItem(book.getBookName());
            }
        }

        ComboBoxes.styleELibraryComboBox(bookComboBox);
        mainPanel.add(bookComboBox, gbc);

        gbc.gridy++;

        // Panel to hold reviews
        StyledPanel reviewPanel = new StyledPanel();
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));

        // Basic styled scroll pane
        JScrollPane scrollPane = new JScrollPane(reviewPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 1)); // Optional custom border

        mainPanel.add(scrollPane, gbc);

        // ComboBox selection logic
        bookComboBox.addActionListener(e -> {
            String selectedBookName = (String) bookComboBox.getSelectedItem();
            reviewPanel.removeAll();

            if (selectedBookName != null && !selectedBookName.isEmpty()) {
                Books selectedBook = null;
                for (Books book : allBooks.values()) {
                    if (book.getBookName().equals(selectedBookName)) {
                        selectedBook = book;
                        break;
                    }
                }

                if (selectedBook != null) {
                    ArrayList<Reviews> reviews = Reviews.getReviewsofBooks().get(selectedBook.getBookId());

                    if (reviews != null && !reviews.isEmpty()) {
                        for (Reviews review : reviews) {
                            StyledPanel reviewStyledPanel = new StyledPanel();
                            reviewStyledPanel.setLayout(new BorderLayout());

                            JLabel customerNameLabel = new JLabel(getCustomerNameById(review.getRaterId()));
                            Labels.styleELibraryLabel(customerNameLabel, "small");

                            JTextArea reviewTextArea = new JTextArea(review.getReview());
                            TextFields.styleELibraryTextArea(reviewTextArea);

                            JScrollPane reviewTextScrollPane = new JScrollPane(reviewTextArea);
                            reviewTextScrollPane.setPreferredSize(new Dimension(400, 120));
                            reviewTextScrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));

                            reviewStyledPanel.add(customerNameLabel, BorderLayout.NORTH);
                            reviewStyledPanel.add(reviewTextScrollPane, BorderLayout.CENTER);

                            reviewPanel.add(reviewStyledPanel);
                            reviewPanel.add(Box.createVerticalStrut(10));
                        }
                    } else {
                        JLabel noReviewsLabel = new JLabel("This book has no reviews.");
                        Labels.styleELibraryLabel(noReviewsLabel, "title");
                        reviewPanel.add(noReviewsLabel);
                    }
                } else {
                    JLabel bookNotFoundLabel = new JLabel("Selected book not found.");
                    Labels.styleELibraryLabel(bookNotFoundLabel, "title");
                    reviewPanel.add(bookNotFoundLabel);
                }
            } else {
                JLabel selectBookLabel = new JLabel("Please select a book to view reviews.");
                Labels.styleELibraryLabel(selectBookLabel, "title");
                reviewPanel.add(selectBookLabel);
            }

            reviewPanel.revalidate();
            reviewPanel.repaint();
        });

        // Trigger review loading for the first book
        if (bookComboBox.getItemCount() > 0) {
            bookComboBox.setSelectedIndex(0);
        }

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private String getCustomerNameById(int customerId) {
        Customers customer = Customers.findCustomersById(customerId);
        return customer != null ? customer.getName() : "Unknown";
    }
}
