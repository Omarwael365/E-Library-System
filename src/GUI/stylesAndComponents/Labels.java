package GUI.stylesAndComponents;

import javax.swing.*;
import java.awt.*;

public class Labels 
{
    public static void styleELibraryLabel(JLabel label, String sentence) 
    {
        if(sentence == "title")
        {
            label.setFont(new Font("Serif", Font.BOLD, 36));
            label.setForeground(Color.WHITE);
        }
        else if(sentence == "normal")
        {
            label.setFont(new Font("Serif", Font.BOLD, 30));
            label.setForeground(Color.WHITE);
        }
        else if(sentence == "small")
        {
            label.setFont(new Font("Serif", Font.BOLD, 25));
            label.setForeground(Color.WHITE);
        }
        else
        {
            System.out.println("Invalid Font");
        }
    }
}
