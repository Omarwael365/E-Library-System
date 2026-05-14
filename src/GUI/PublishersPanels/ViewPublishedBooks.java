package GUI.PublishersPanels;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.util.ArrayList;
import Classes.Publisher;
import Classes.Books;
import GUI.MainFrame;
import GUI.stylesAndComponents.StyledPanel;
import GUI.stylesAndComponents.TextFields;
import GUI.stylesAndComponents.Labels;

public class ViewPublishedBooks extends JPanel {
    MainFrame mainFrame;
    Publisher publisher;

    public ViewPublishedBooks(MainFrame mainFrame, Publisher publisher) {
        this.mainFrame = mainFrame;
        this.publisher = publisher;

        // Gradient background panel
        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        

        // Title
        JLabel titleLabel = new JLabel("Published Books by " + publisher.getName(), JLabel.CENTER);
        Labels.styleELibraryLabel(titleLabel, "title");
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Grid to hold book panels
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // smaller spacing between cards
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        ArrayList<Books> bookList = publisher.getBookList();
        
        for (int i = 0; i < bookList.size(); i++) {
            JScrollPane bookCard = createBookPanel(bookList.get(i));

            gbc.gridx = i % 2;
            gbc.gridy = i / 2;
            gbc.fill = GridBagConstraints.NONE;

            gridPanel.add(bookCard, gbc);
        }

        // Filler to push content to top
        gbc.gridx = 0;
        gbc.gridy = (bookList.size() + 1) / 2;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gridPanel.add(Box.createGlue(), gbc);

        // Main scroll pane
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Custom scroll bar UI (optional aesthetic)
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(120, 120, 120, 160);
            }
        });

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Final layout
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private JScrollPane createBookPanel(Books book) 
    {
        StyledPanel bookPanel = new StyledPanel();
        bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
        bookPanel.setBackground(new Color(255, 255, 255, 200));
        bookPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Title
        JLabel title = new JLabel(book.getBookName() + " by " + publisher.getName());
        Labels.styleELibraryLabel(title, "small");
        bookPanel.add(title);

        // Cover image
        if (book.getCoverImageFile() != null && book.getCoverImageFile().exists()) {
            ImageIcon imageIcon = new ImageIcon(book.getCoverImageFile().getPath());
            Image scaledImage = imageIcon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            
            imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            bookPanel.add(Box.createVerticalStrut(10));
            bookPanel.add(imageLabel);
        }

        // Price and category
        JLabel price = new JLabel("Price: $" + book.getPrice());
        JLabel category = new JLabel("Category: " + book.getCategory());
        Labels.styleELibraryLabel(price, "small");
        Labels.styleELibraryLabel(category, "small");

        bookPanel.add(Box.createVerticalStrut(5));
        bookPanel.add(price);
        bookPanel.add(category);

        // Book preview
        if (book.getBookTextFile() != null && book.getBookTextFile().exists()) {
            String preview = getBookTextPreview(book.getBookTextFile());
            JTextArea previewText = new JTextArea("Preview: " + preview + "...");
            TextFields.styleELibraryTextArea(previewText);

            JScrollPane previewScroll = new JScrollPane(previewText);
            previewScroll.setPreferredSize(new Dimension(100, 100));
            previewScroll.setBorder(null);
            previewScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            bookPanel.add(Box.createVerticalStrut(5));
            bookPanel.add(previewScroll);
        }

        // Outer scroll panel for the entire book card
        JScrollPane bookScrollPane = new JScrollPane(bookPanel);
        bookScrollPane.setPreferredSize(new Dimension(450, 450));
        bookScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bookScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bookScrollPane.setBorder(null);
        bookScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        bookScrollPane.setBackground(new Color(255, 255, 255, 200));

        // Optional: match scrollbar style
        bookScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(120, 120, 120, 160);
            }
        });

        return bookScrollPane;
    }

    // Preview helper
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
