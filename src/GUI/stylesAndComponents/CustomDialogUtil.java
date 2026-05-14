package GUI.stylesAndComponents;

import javax.swing.*;
import java.awt.*;

public class CustomDialogUtil
{
    // Reusable method for showing a styled message dialog
    public static void showStyledMessage(Component parentComponent, String message,String title) {
        // Set custom styles
        UIManager.put("OptionPane.background", new Color(102, 51, 153));
        UIManager.put("Panel.background", new Color(102, 51, 153));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", new Color(102, 51, 153));

        // Show the styled dialog
        JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
