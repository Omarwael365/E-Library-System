package GUI.CustomerPanels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Classes.Books;
import Classes.Customers;
import Database.DAO.BorrowedBooksDAO;
import Database.DAO.OwnedBooksDAO;
import GUI.MainFrame;
import GUI.stylesAndComponents.Labels;
import GUI.stylesAndComponents.StyledPanel;
import GUI.stylesAndComponents.Buttons;
import GUI.stylesAndComponents.CustomDialogUtil;

public class ViewOrders extends JPanel {
    private MainFrame mainFrame;
    private Customers customer;

    public ViewOrders(MainFrame mainFrame, Customers customer) {
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

        JLabel title = new JLabel("Pending Orders");
        Labels.styleELibraryLabel(title, "title");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(title, gbc);

        gbc.gridy++;

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabbedPane.addTab("Owned Books", createBookListPanel(customer.getpotentialOwnedBooks(), true));
        tabbedPane.addTab("Borrowed Books", createBookListPanel(customer.getpotentialBorroedbooks(), false));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainPanel.add(tabbedPane, gbc);

        // Add StyledPanel to this JPanel
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createBookListPanel(ArrayList<Books> bookList, boolean isOwned) {
        StyledPanel panel = new StyledPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        double totalPrice = 0;

        if (bookList.isEmpty()) {
            JLabel empty = new JLabel("No books in this order.");
            Labels.styleELibraryLabel(empty, "title");
            panel.add(empty, gbc);
        } else {
            for (Books book : new ArrayList<>(bookList)) {
                StyledPanel bookEntry = new StyledPanel();
                bookEntry.setLayout(new GridBagLayout());

                GridBagConstraints bookGbc = new GridBagConstraints();
                bookGbc.insets = new Insets(4, 4, 4, 4);
                bookGbc.gridx = 0;
                bookGbc.gridy = 0;
                bookGbc.anchor = GridBagConstraints.WEST;

                JLabel bookLabel = new JLabel("• " + book.getBookName() + (isOwned ? " ($" + book.getPrice() + ")" : ""));
                Labels.styleELibraryLabel(bookLabel, "normal");

                bookEntry.add(bookLabel, bookGbc);

                bookGbc.gridx++;
                bookGbc.anchor = GridBagConstraints.EAST;

                JButton removeBtn = new JButton("Remove");
                Buttons.styleELibraryButton(removeBtn);
                removeBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
                removeBtn.setForeground(Color.RED);

                removeBtn.addActionListener(e -> {
                    bookList.remove(book);
                    CustomDialogUtil.showStyledMessage(null, book.getBookName() + " removed from order.", "Removed");
                    refreshPanel();
                });

                bookEntry.add(removeBtn, bookGbc);

                panel.add(bookEntry, gbc);
                gbc.gridy++;

                if (isOwned) totalPrice += book.getPrice();
            }

            if (isOwned) {
                gbc.gridy++;
                JLabel totalLabel = new JLabel("Total Price: $" + String.format("%.2f", totalPrice));
                Labels.styleELibraryLabel(totalLabel, "normal");
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.CENTER;
                panel.add(totalLabel, gbc);
            }

            gbc.gridy++;
            JButton confirmBtn = new JButton("Confirm " + (isOwned ? "Purchase" : "Borrow"));
            Buttons.styleELibraryButton(confirmBtn);
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(confirmBtn, gbc);

            confirmBtn.addActionListener(e -> {
                if (isOwned) {
                    customer.getOwnedBookList().addAll(bookList);
                    for (Books b : bookList) {
                        OwnedBooksDAO.insertOwnedBook(customer.getId(), b.getBookId());
                    }
                    customer.getpotentialOwnedBooks().clear();
                    CustomDialogUtil.showStyledMessage(null, "Books added to your owned library!", "Order Confirmed");
                } else {
                    customer.getBorrowedBookList().addAll(bookList);
                    for (Books b : bookList) {
                        BorrowedBooksDAO.insertBorrowedBook(customer.getId(), b.getBookId());
                    }
                    customer.getpotentialBorroedbooks().clear();
                    CustomDialogUtil.showStyledMessage(null, "Books added to your borrowed list!", "Order Confirmed");
                }
                refreshPanel();
            });
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        StyledPanel wrapper = new StyledPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(scrollPane, BorderLayout.CENTER);
        return wrapper;
    }

    private void refreshPanel() {
        removeAll();
        revalidate();
        repaint();
        add(new ViewOrders(mainFrame, customer));
    }
}
