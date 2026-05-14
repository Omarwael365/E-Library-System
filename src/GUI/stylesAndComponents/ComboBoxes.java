package GUI.stylesAndComponents;

import javax.swing.*;
import java.awt.*;

public class ComboBoxes {

    public static void styleELibraryComboBox(JComboBox<?> comboBox) {
        comboBox.setPreferredSize(new Dimension(300, 50));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBox.setForeground(Color.WHITE);
        comboBox.setBackground(new Color(48, 25, 52));
        comboBox.setFocusable(false);

        // Optional: Customize dropdown list background (L&F dependent)
        comboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = new JButton();
                arrowButton.setBorder(BorderFactory.createEmptyBorder());
                arrowButton.setBackground(new Color(48, 25, 52));
                arrowButton.setForeground(Color.WHITE);
                return arrowButton;
            }
        });

        // Optional: Make border invisible if desired
        comboBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
}

