package GUI.CustomerPanels;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

import Classes.*;
import GUI.MainFrame;
import GUI.stylesAndComponents.StyledPanel;
import GUI.stylesAndComponents.TextFields;
import GUI.stylesAndComponents.Buttons;
import GUI.stylesAndComponents.CustomDialogUtil;
import GUI.stylesAndComponents.Labels;

public class BrowseBooks extends JPanel {
    MainFrame mainFrame;
    Customers customer;
    JTextField searchField;

    public BrowseBooks(MainFrame mainFrame, Customers customer) {
        this.mainFrame = mainFrame;
        this.customer = customer;

        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search bar
        StyledPanel searchPanel = new StyledPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setOpaque(false);

        searchField = new JTextField();
        TextFields.styleELibraryTextField(searchField);
        searchField.setPreferredSize(new Dimension(100, 50));
        searchField.setToolTipText("Search books by name...");

        searchPanel.add(searchField);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Grid panel
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(120, 120, 120, 160);
            }
        });

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Initial book display
        displayBooks(Books.getAllBooksList().values(), gridPanel, gbc);

        // Search functionality
        searchField.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyReleased(KeyEvent e) 
            {
                String searchText = searchField.getText().toLowerCase();
                gridPanel.removeAll();
                ArrayList<Books> filtered = new ArrayList<>();
                for (Books book : Books.getAllBooksList().values()) 
                {
                    if (book.getBookName().toLowerCase().contains(searchText)) 
                    {
                        filtered.add(book);
                    }
                }
                displayBooks(filtered, gridPanel, gbc);
                gridPanel.revalidate();
                gridPanel.repaint();
            }
        });
    }

    private void displayBooks(Collection<Books> bookList, JPanel gridPanel, GridBagConstraints gbc) {
        int i = 0;
        for (Books book : bookList) {
            JScrollPane bookCard = createBookPanel(book);
            gbc.gridx = i % 2;
            gbc.gridy = i / 2;
            gbc.fill = GridBagConstraints.NONE;
            gridPanel.add(bookCard, gbc);
            i++;
        }

        gbc.gridx = 0;
        gbc.gridy = (bookList.size() + 1) / 2;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gridPanel.add(Box.createGlue(), gbc);
    }

    private JScrollPane createBookPanel(Books book) {
        StyledPanel bookPanel = new StyledPanel();
        bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
        bookPanel.setBackground(new Color(255, 255, 255, 200));
        bookPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    
        // --- Title ---
        for (Publisher p : Publisher.getPublisherList()) {
            if (p.getId() == book.getPublisherID()) {
                JLabel title = new JLabel(book.getBookName() + " by " + p.getName());
                Labels.styleELibraryLabel(title, "small");
                title.setAlignmentX(Component.LEFT_ALIGNMENT);
                bookPanel.add(title);
                break;
            }
        }
    
        // --- Cover Image ---
        if (book.getCoverImageFile() != null && book.getCoverImageFile().exists()) {
            ImageIcon imageIcon = new ImageIcon(book.getCoverImageFile().getPath());
            Image scaledImage = imageIcon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            bookPanel.add(Box.createVerticalStrut(10));
            bookPanel.add(imageLabel);
        }
    
        // --- Price & Category ---
        JLabel price = new JLabel("Price: $" + book.getPrice());
        JLabel category = new JLabel("Category: " + book.getCategory());
        Labels.styleELibraryLabel(price, "small");
        Labels.styleELibraryLabel(category, "small");
        price.setAlignmentX(Component.LEFT_ALIGNMENT);
        category.setAlignmentX(Component.LEFT_ALIGNMENT);
        bookPanel.add(Box.createVerticalStrut(5));
        bookPanel.add(price);
        bookPanel.add(category);
    
        // --- Preview TextArea ---
        if (book.getBookTextFile() != null && book.getBookTextFile().exists()) {
            String preview = getBookTextPreview(book.getBookTextFile());
            JTextArea previewText = new JTextArea("Preview: " + preview + "...");
            TextFields.styleELibraryTextArea(previewText);
            previewText.setLineWrap(true);
            previewText.setWrapStyleWord(true);
            JScrollPane previewScroll = new JScrollPane(previewText);
            previewScroll.setPreferredSize(new Dimension(300, 80));
            previewScroll.setBorder(null);
            previewScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
            previewScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            bookPanel.add(Box.createVerticalStrut(5));
            bookPanel.add(previewScroll);
        }
    
        // --- Buttons: Borrow & Buy ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
        JButton borrowBtn = new JButton("Borrow Book");
        JButton buyBtn = new JButton("Buy Book");
        Buttons.styleELibraryButton(borrowBtn);
        Buttons.styleELibraryButton(buyBtn);
        borrowBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        buyBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
    
        borrowBtn.addActionListener(e -> {
            if (customer.getBorrowedBookList().contains(book)) {
                CustomDialogUtil.showStyledMessage(null, "You have already borrowed this book.", "Already Borrowed");
            } else if (customer.getOwnedBookList().contains(book)) {
                CustomDialogUtil.showStyledMessage(null, "You already own this book.", "Already Owned");
            } else if (customer.getpotentialOwnedBooks().contains(book)) {
                CustomDialogUtil.showStyledMessage(null, "This book is already in your purchase cart.", "Already in Purchase Cart");
            } else if (customer.getpotentialBorroedbooks().contains(book)) {
                CustomDialogUtil.showStyledMessage(null, "This book is already in your borrow cart.", "Already in Borrow Cart");
            } else {
                customer.getpotentialBorroedbooks().add(book);
                System.out.println("Added to borrow list: " + book.getBookName());
                CustomDialogUtil.showStyledMessage(null, "Book added to borrowed books.\nConfirm in the View Order panel.", "Book Added");
            }
        });
        
        buyBtn.addActionListener(e -> {
            if (customer.getOwnedBookList().contains(book)) {
                CustomDialogUtil.showStyledMessage(null, "You already own this book.", "Already Owned");
            } else if (customer.getBorrowedBookList().contains(book)) {
                CustomDialogUtil.showStyledMessage(null, "You already borrowed this book.", "Already Borrowed");
            } else if (customer.getpotentialOwnedBooks().contains(book)) {
                CustomDialogUtil.showStyledMessage(null, "This book is already in your purchase cart.", "Already in Purchase Cart");
            } else if (customer.getpotentialBorroedbooks().contains(book)) {
                CustomDialogUtil.showStyledMessage(null, "This book is already in your borrow cart.", "Already in Borrow Cart");
            } else {
                customer.getpotentialOwnedBooks().add(book);
                System.out.println("Added to buy list: " + book.getBookName());
                CustomDialogUtil.showStyledMessage(null, "Book added to owned books.\nConfirm in the View Order panel.", "Book Added");
            }
        });
        
    
        buttonPanel.add(borrowBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(buyBtn);
    
        bookPanel.add(Box.createVerticalStrut(10));
        bookPanel.add(buttonPanel);
    
        // --- Wrapping into ScrollPane ---
        JScrollPane bookScrollPane = new JScrollPane(bookPanel);
        bookScrollPane.setPreferredSize(new Dimension(450, 450));
        bookScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bookScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bookScrollPane.setBorder(null);
        bookScrollPane.setBackground(new Color(255, 255, 255, 200));
        bookScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        bookScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(120, 120, 120, 160);
            }
        });
    
        return bookScrollPane;
    }
    


    private String getBookTextPreview(File file) {
        StringBuilder preview = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int count = 0;
            String line;
            while ((line = reader.readLine()) != null && count < 100) {
                preview.append(line).append(" ");
                count += line.length();
                if (count >= 100) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return preview.toString().trim();
    }
}
