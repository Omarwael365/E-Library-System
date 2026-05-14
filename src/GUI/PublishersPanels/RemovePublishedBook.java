package GUI.PublishersPanels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import Classes.*;
import Database.DAO.BookDAO;
import GUI.MainFrame;
import GUI.stylesAndComponents.*;

public class RemovePublishedBook extends JPanel implements ActionListener
{
    MainFrame mainFrame;
    Publisher publisher;
    JComboBox<String> publisherBooksNameList;
    JButton removeBookButton;

    public RemovePublishedBook(MainFrame mainFrame, Publisher publisher)
    {
        this.mainFrame = mainFrame;
        this.publisher = publisher;

        // Collect book names from the publisher
        ArrayList<String> bookNames = new ArrayList<>();
        for (Books b : publisher.getBookList()) {
            bookNames.add(b.getBookName());
        }

        // Styled panel with layout
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label
        JLabel selectLabel = new JLabel("Select a Book to Remove:");
        Labels.styleELibraryLabel(selectLabel, "normal");

        // Styled combo box
        publisherBooksNameList = new JComboBox<>(bookNames.toArray(new String[0]));
        ComboBoxes.styleELibraryComboBox(publisherBooksNameList);

        // Styled button
        removeBookButton = new JButton("Remove Book");
        Buttons.styleELibraryButton(removeBookButton);
        removeBookButton.setPreferredSize(new Dimension(180, 40));

        // Add components to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(selectLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(publisherBooksNameList, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(removeBookButton, gbc);

        // Add mainPanel to this JPanel
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        removeBookButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == removeBookButton) 
        {
            String selectedBookName = (String) publisherBooksNameList.getSelectedItem();
            if (selectedBookName == null) {
                CustomDialogUtil.showStyledMessage(null, "No book selected.", "Error");
                return;
            }
    
            // Find the book by name
            Books selectedBook = null;
            for (Books book : publisher.getBookList()) 
            {
                if (book.getBookName().equals(selectedBookName)) {
                    selectedBook = book;
                    break;
                }
            }
    
            if (selectedBook == null) 
            {
                CustomDialogUtil.showStyledMessage(null, "Book not found.", "Error");
                return;
            }
    
            boolean success = BookDAO.deleteBook(selectedBook.getBookId());
    
            if (success) 
            {
                publisher.getBookList().remove(selectedBook);
                publisherBooksNameList.removeItem(selectedBookName);
                ArrayList<Books> booksByPub = Books.getbooksByPublisher().get(publisher.getId());
                if (booksByPub != null) 
                {
                    booksByPub.remove(selectedBook);
                }
    
                // Remove from allBooksList map
                Books.getAllBooksList().remove(selectedBook.getBookId());
                
                CustomDialogUtil.showStyledMessage(null, "Book removed successfully.", "Success");
            }
            else
            {
                CustomDialogUtil.showStyledMessage(null, "Failed to remove book from database.", "Error");
            }
        }
    }
}
