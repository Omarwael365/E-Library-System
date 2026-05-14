package GUI.CustomerPanels;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.util.ArrayList;

import Classes.Books;
import Classes.Customers;
import GUI.MainFrame;
import GUI.stylesAndComponents.StyledPanel;
import GUI.stylesAndComponents.TextFields;
import GUI.stylesAndComponents.Labels;
import GUI.stylesAndComponents.Buttons;
import GUI.stylesAndComponents.CustomDialogUtil;

public class ViewBorrowedBooks extends JPanel {
    MainFrame mainFrame;
    Customers customer;

    public ViewBorrowedBooks(MainFrame mainFrame, Customers customer) {
        this.mainFrame = mainFrame;
        this.customer = customer;

        StyledPanel mainPanel = new StyledPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Borrowed Books", JLabel.CENTER);
        Labels.styleELibraryLabel(titleLabel, "title");
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        ArrayList<Books> bookList = customer.getBorrowedBookList();

        if (bookList.isEmpty()) 
        {
            JLabel noBooksLabel = new JLabel("You have no borrowed books.", JLabel.CENTER);
            Labels.styleELibraryLabel(noBooksLabel, "small");
            gridPanel.add(noBooksLabel, gbc);
        } 
        else 
        {
            // Add books to the grid as usual
            for (int i = 0; i < bookList.size(); i++) {
                JScrollPane bookCard = createBookPanel(bookList.get(i));
                gbc.gridx = i % 2;
                gbc.gridy = i / 2;
                gridPanel.add(bookCard, gbc);
            }
        }

        gbc.gridx = 0;
        gbc.gridy = (bookList.size() + 1) / 2;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gridPanel.add(Box.createGlue(), gbc);

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

        JLabel title = new JLabel(book.getBookName());
        Labels.styleELibraryLabel(title, "small");
        bookPanel.add(title);

        if (book.getCoverImageFile() != null && book.getCoverImageFile().exists()) {
            ImageIcon imageIcon = new ImageIcon(book.getCoverImageFile().getPath());
            Image scaledImage = imageIcon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            bookPanel.add(Box.createVerticalStrut(10));
            bookPanel.add(imageLabel);
        }

        JLabel category = new JLabel("Category: " + book.getCategory());
        Labels.styleELibraryLabel(category, "small");
        bookPanel.add(Box.createVerticalStrut(5));
        bookPanel.add(category);

        if (book.getBookTextFile() != null && book.getBookTextFile().exists()) {
            String preview = getBookTextPreview(book.getBookTextFile());
            JTextArea previewText = new JTextArea("Preview: " + preview + "...");
            TextFields.styleELibraryTextArea(previewText);
            JScrollPane previewScroll = new JScrollPane(previewText);
            previewScroll.setPreferredSize(new Dimension(300, 80));
            previewScroll.setBorder(null);
            previewScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
            previewScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            bookPanel.add(Box.createVerticalStrut(5));
            bookPanel.add(previewScroll);
        }

        // Download Button
        JButton downloadBtn = new JButton("Download Book");
        Buttons.styleELibraryButton(downloadBtn);
        downloadBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        downloadBtn.addActionListener(e -> {
            try {
                File bookFile = book.getBookTextFile();
                if (bookFile != null && bookFile.exists()) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setSelectedFile(new File(book.getBookName() + ".txt"));
                    int option = fileChooser.showSaveDialog(this);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File dest = fileChooser.getSelectedFile();
                        try (InputStream in = new FileInputStream(bookFile);
                            OutputStream out = new FileOutputStream(dest)) {
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = in.read(buffer)) > 0) {
                                out.write(buffer, 0, len);
                            }
                        }
                        CustomDialogUtil.showStyledMessage(null, "Book downloaded successfully.", "Download Complete");
                    }
                } else {
                    CustomDialogUtil.showStyledMessage(null, "Book file not found.", "Error");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                CustomDialogUtil.showStyledMessage(null, "An error occurred while downloading.", "Error");
            }
        });

        bookPanel.add(Box.createVerticalStrut(10));
        bookPanel.add(downloadBtn);

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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return preview.toString().trim();
    }
}
