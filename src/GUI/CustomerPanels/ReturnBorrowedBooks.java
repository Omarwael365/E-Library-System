package GUI.CustomerPanels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Classes.Books;
import Classes.Customers;
import Database.DAO.BorrowedBooksDAO;
import GUI.MainFrame;
import GUI.stylesAndComponents.Labels;
import GUI.stylesAndComponents.StyledPanel;
import GUI.stylesAndComponents.Buttons;
import GUI.stylesAndComponents.ComboBoxes;
import GUI.stylesAndComponents.CustomDialogUtil;

public class ReturnBorrowedBooks extends JPanel {
    private MainFrame mainFrame;
    private Customers customer;

    public ReturnBorrowedBooks(MainFrame mainFrame, Customers customer) {
        this.mainFrame = mainFrame;
        this.customer = customer;

        // Main StyledPanel container
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Return Borrowed Book");
        Labels.styleELibraryLabel(title, "title");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(title, gbc);

        gbc.gridy++;

        // ComboBox for selecting borrowed books
        JComboBox<String> bookComboBox = new JComboBox<>();
        for (Books book : customer.getBorrowedBookList()) {
            bookComboBox.addItem(book.getBookName());
        }

        ComboBoxes.styleELibraryComboBox(bookComboBox);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(bookComboBox, gbc);

        gbc.gridy++;

        // Return Button
        JButton returnBtn = new JButton("Return Selected Book");
        Buttons.styleELibraryButton(returnBtn);
        returnBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
        returnBtn.setForeground(Color.RED);

        // Action listener for the return button
        returnBtn.addActionListener(e -> {
            String selectedBookName = (String) bookComboBox.getSelectedItem();

            if (selectedBookName != null) {
                Books selectedBook = null;
                for (Books book : customer.getBorrowedBookList()) {
                    if (book.getBookName().equals(selectedBookName)) {
                        selectedBook = book;
                        break;
                    }
                }
            
                if (selectedBook != null) {
                    customer.getBorrowedBookList().remove(selectedBook); // remove from the list
                    BorrowedBooksDAO.deleteBorrowedBook(customer.getId(), selectedBook.getBookId()); // remove from DB
                    CustomDialogUtil.showStyledMessage(null, selectedBook.getBookName() + " has been returned.", "Returned");
                    refreshPanel();
                } else {
                    CustomDialogUtil.showStyledMessage(null, "Book not found in your borrowed list.", "Error");
                }
            } else {
                CustomDialogUtil.showStyledMessage(null, "No book selected to return.", "Error");
            }
        });

        mainPanel.add(returnBtn, gbc);

        // Add StyledPanel to this JPanel
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private void refreshPanel() {
        removeAll();
        revalidate();
        repaint();
        add(new ReturnBorrowedBooks(mainFrame, customer)); // Refresh the panel
    }
}
