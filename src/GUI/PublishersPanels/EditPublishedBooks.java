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

public class EditPublishedBooks extends JPanel implements ActionListener
{
    MainFrame mainFrame;
    Publisher publisher;

    JComboBox<String> publisherBooksNameList;
    JTextField newPriceField;
    JButton updatePriceButton;

    public EditPublishedBooks(MainFrame mainFrame, Publisher publisher)
    {
        this.mainFrame = mainFrame;
        this.publisher = publisher;

        // Panel setup
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label: Select Book
        JLabel selectLabel = new JLabel("Select a Book:");
        Labels.styleELibraryLabel(selectLabel, "normal");

        ArrayList<String> bookNames = new ArrayList<>();
        for (Books b : publisher.getBookList()) {
            bookNames.add(b.getBookName());
        }
        publisherBooksNameList = new JComboBox<>(bookNames.toArray(new String[0]));
        ComboBoxes.styleELibraryComboBox(publisherBooksNameList);

        // Label: Enter New Price
        JLabel priceLabel = new JLabel("Enter New Price:");
        Labels.styleELibraryLabel(priceLabel, "normal");

        newPriceField = new JTextField(10);
        TextFields.styleELibraryTextField(newPriceField);

        // Button
        updatePriceButton = new JButton("Update Price");
        Buttons.styleELibraryButton(updatePriceButton);
        updatePriceButton.addActionListener(this);

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(selectLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(publisherBooksNameList, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(newPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(updatePriceButton, gbc);

        // Add to this JPanel
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == updatePriceButton)
        {
            String selectedBookName = (String) publisherBooksNameList.getSelectedItem();
            String newPriceText = newPriceField.getText().trim();

            if (selectedBookName == null || newPriceText.isEmpty()) 
            {
                CustomDialogUtil.showStyledMessage(null, "Please select a book and enter a new price.", "Error");
                return;
            }

            try 
            {
                double newPrice = Double.parseDouble(newPriceText);
                if (newPrice < 0) throw new NumberFormatException();

                // Find the book object
                Books selectedBook = null;
                for (Books book : publisher.getBookList()) 
                {
                    if (book.getBookName().equals(selectedBookName)) 
                    {
                        selectedBook = book;
                        break;
                    }
                }

                if (selectedBook == null) 
                {
                    CustomDialogUtil.showStyledMessage(null, "Book not found.", "Error");
                    return;
                }

                // Update price
                    selectedBook.setPrice(newPrice);
                    boolean updated = BookDAO.updateBookPrice(selectedBook.getBookId(), newPrice);

                if (updated) 
                {
                    CustomDialogUtil.showStyledMessage(null, "Book price updated successfully.", "Success");
                } 
                else 
                {
                    CustomDialogUtil.showStyledMessage(null, "Failed to update price in database.", "Error");
                }

            } 
            catch (NumberFormatException ex) {

                CustomDialogUtil.showStyledMessage(null, "Please enter a valid numeric price.", "Invalid Input");
            }
        }
    }
}
