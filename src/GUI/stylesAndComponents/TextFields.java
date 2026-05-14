package GUI.stylesAndComponents;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.*;

public class TextFields {

    public static void styleELibraryTextField(JTextField textField) {
        // Apply basic styling for the text field
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Set font size
        textField.setForeground(Color.WHITE); // White text color
        textField.setCaretColor(Color.WHITE); // White cursor
        textField.setOpaque(true); // Set to true so we can apply a solid color background
        
        // Set the dark purple background color
        textField.setBackground(new Color(48, 25, 52)); // Dark Purple color
        
        // Set a gray border around the text field
        textField.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169), 2)); // Gray border with 2px width
        
        // Set padding inside the text field
        textField.setBorder(BorderFactory.createCompoundBorder(
            textField.getBorder(), 
            BorderFactory.createEmptyBorder(10, 40, 10, 20) // Add padding inside the text field
        ));

        // Set preferred size to make the text field wider
        textField.setPreferredSize(new Dimension(300, 50));  // Width: 300px, Height: 50px

        // Focus listener to repaint the text field when it gains or loses focus
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.repaint(); // Repaint the text field on focus
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.repaint(); // Repaint the text field when focus is lost
            }
        });
    }
    public static void styleELibraryTextField(JPasswordField textField) {
        // Apply basic styling for the text field
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Set font size
        textField.setForeground(Color.WHITE); // White text color
        textField.setCaretColor(Color.WHITE); // White cursor
        textField.setOpaque(true); // Set to true so we can apply a solid color background
        
        // Set the dark purple background color
        textField.setBackground(new Color(48, 25, 52)); // Dark Purple color
        
        // Set a gray border around the text field
        textField.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169), 2)); // Gray border with 2px width
        
        // Set padding inside the text field
        textField.setBorder(BorderFactory.createCompoundBorder(
            textField.getBorder(), 
            BorderFactory.createEmptyBorder(10, 40, 10, 20) // Add padding inside the text field
        ));

        // Set preferred size to make the text field wider
        textField.setPreferredSize(new Dimension(300, 50));  // Width: 300px, Height: 50px

        // Focus listener to repaint the text field when it gains or loses focus
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.repaint(); // Repaint the text field on focus
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.repaint(); // Repaint the text field when focus is lost
            }
        });
    }
    
    public static void styleELibraryTextArea(JTextArea textArea) {
        // Enable line wrap and word wrap
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    
        // Font and colors
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.setOpaque(true);
        textArea.setBackground(new Color(48, 25, 52));
    
        // Padding and border
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(169, 169, 169), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
    
        // Set a stable preferred size (initial size)
        textArea.setPreferredSize(new Dimension(200, 100));
    
        // Focus repaint (still useful for UI polish)
        textArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textArea.repaint();
            }
    
            @Override
            public void focusLost(FocusEvent e) {
                textArea.repaint();
            }
        });
    
        // Auto-expand height based on content size
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                resizeTextArea(textArea);
            }
    
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                resizeTextArea(textArea);
            }
    
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                resizeTextArea(textArea);
            }
        });
    }
    
    // Method to resize the JTextArea based on the content size
    private static void resizeTextArea(JTextArea textArea) 
    {
        textArea.setSize(textArea.getWidth(), Short.MAX_VALUE);
        int newHeight = textArea.getPreferredSize().height;
        textArea.setPreferredSize(new Dimension(textArea.getWidth(), newHeight));
        textArea.revalidate();
    }
    public static JScrollPane createStyledScrollPane(JTextArea textArea) 
    {
        // Wrap the JTextArea inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
    
        // Ensure that the scroll pane resizes along with the JTextArea
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
        // Setting scroll bar preferences for smoother scrolling
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
    
        // Optional: set the scroll pane's border to match the style
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169), 2));
    
        // Return the scroll pane
        return scrollPane;
    }
}    