package GUI.PublishersPanels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.swing.*;
import Classes.Publisher;
import GUI.MainFrame;
import GUI.stylesAndComponents.*;

public class PublishBook extends JPanel implements ActionListener 
{
    MainFrame mainFrame;
    Publisher publisher;
    JButton addbook, uploadImageButton, uploadTextButton;
    JTextField bookname, bookprice;
    JComboBox<String> categoryDropdown;
    File coverImageFile, bookTextFile;

    public PublishBook(MainFrame mainFrame, Publisher publisher)
    {
        this.mainFrame = mainFrame;
        this.publisher = publisher;

        setLayout(new BorderLayout());

        StyledPanel mainpanel = new StyledPanel();
        mainpanel.setLayout(new GridBagLayout());

        bookname = new JTextField();
        TextFields.styleELibraryTextField(bookname);

        bookprice = new JTextField();
        TextFields.styleELibraryTextField(bookprice);

        String[] categories = { "Fiction", "Non-Fiction", "Science", "Biography", "Fantasy", "Technology", "History", "Romance" };
        categoryDropdown = new JComboBox<>(categories);
        ComboBoxes.styleELibraryComboBox(categoryDropdown);

        JLabel booknameLabel = new JLabel("Book Name:");
        Labels.styleELibraryLabel(booknameLabel, "normal");

        JLabel bookpriceLabel = new JLabel("Book Price:");
        Labels.styleELibraryLabel(bookpriceLabel, "normal");

        JLabel bookcategoryLabel = new JLabel("Category:");
        Labels.styleELibraryLabel(bookcategoryLabel, "normal");

        JLabel uploadImageLabel = new JLabel("Book Cover:");
        Labels.styleELibraryLabel(uploadImageLabel, "normal");

        JLabel uploadTextLabel = new JLabel("Book File:");
        Labels.styleELibraryLabel(uploadTextLabel, "normal");

        uploadImageButton = new JButton("Upload Cover Image");
        Buttons.styleELibraryButton(uploadImageButton);
        uploadImageButton.addActionListener(this);

        uploadTextButton = new JButton("Upload Book File (.txt)");
        Buttons.styleELibraryButton(uploadTextButton);
        uploadTextButton.addActionListener(this);

        addbook = new JButton("Add Book");
        Buttons.styleELibraryButton(addbook);
        addbook.setPreferredSize(new Dimension(200, 75));
        addbook.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 30, 20, 30);
        gbc.anchor = GridBagConstraints.WEST;

        // Book Name
        gbc.gridx = 0; gbc.gridy = 0;
        mainpanel.add(booknameLabel, gbc);
        gbc.gridx = 1;
        mainpanel.add(bookname, gbc);

        // Book Price
        gbc.gridx = 0; gbc.gridy = 1;
        mainpanel.add(bookpriceLabel, gbc);
        gbc.gridx = 1;
        mainpanel.add(bookprice, gbc);

        // Book Category
        gbc.gridx = 0; gbc.gridy = 2;
        mainpanel.add(bookcategoryLabel, gbc);
        gbc.gridx = 1;
        mainpanel.add(categoryDropdown, gbc);

        // Upload Book Cover Image
        gbc.gridx = 0; gbc.gridy = 3;
        mainpanel.add(uploadImageLabel, gbc);
        gbc.gridx = 1;
        mainpanel.add(uploadImageButton, gbc);

        // Upload Book File
        gbc.gridx = 0; gbc.gridy = 4;
        mainpanel.add(uploadTextLabel, gbc);
        gbc.gridx = 1;
        mainpanel.add(uploadTextButton, gbc);

        // Submit Button
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainpanel.add(addbook, gbc);

        add(mainpanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == uploadImageButton) 
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Book Cover Image");
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) 
            {
                coverImageFile = fileChooser.getSelectedFile();
            }
        } 
        else if (e.getSource() == uploadTextButton) 
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Book File (.txt)");
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                bookTextFile = fileChooser.getSelectedFile();
                if (!bookTextFile.getName().endsWith(".txt")) 
                {
                    CustomDialogUtil.showStyledMessage(null, "Please upload a .txt file for the book.", "File Error");
                    bookTextFile = null;
                }
            }
        } 
        else if (e.getSource() == addbook) 
        {
            String name = bookname.getText().trim();
            String priceStr = bookprice.getText().trim();
            String category = (String) categoryDropdown.getSelectedItem();

            if (name.isEmpty()) 
            {
                CustomDialogUtil.showStyledMessage(null, "Please enter a valid book name.", "Validation Error");
                return;
            }

            double price;
            try 
            {
                price = Double.parseDouble(priceStr);
                if (price < 0) throw new NumberFormatException();
            } 
            catch (NumberFormatException ex) 
            {
                CustomDialogUtil.showStyledMessage(null, "Please enter a valid positive price.", "Validation Error");
                return;
            }

            if (coverImageFile == null)
            {
                CustomDialogUtil.showStyledMessage(null, "Please upload a book cover image.", "Validation Error");
                return;
            }

            if (bookTextFile == null) 
            {
                CustomDialogUtil.showStyledMessage(null, "Please upload the book file (.txt).", "Validation Error");
                return;
            }

            // Save book information 
            publisher.addBook(name, price, category,coverImageFile,bookTextFile);

            // Clear form
            bookname.setText("");
            bookprice.setText("");
            categoryDropdown.setSelectedIndex(0);
            coverImageFile = null;
            bookTextFile = null;

            CustomDialogUtil.showStyledMessage(null, "Book published successfully!", "Success");
        }
    }

}
