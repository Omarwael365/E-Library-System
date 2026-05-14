package GUI.stylesAndComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClickableLabel extends JLabel {

    private Color normalColor = Color.WHITE;       // Text color by default
    private Color hoverColor = new Color(0, 102, 204);   // Hover text color
    private Color clickColor = new Color(0, 51, 102);    // Clicked text color

    // ActionListener to handle clicks
    private ActionListener actionListener;

    public ClickableLabel(String text) {
        super(text);

        setOpaque(false); // No background
        setForeground(normalColor);
        setFont(new Font("Arial", Font.BOLD, 15));
        setHorizontalAlignment(SwingConstants.CENTER);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setForeground(clickColor);
                // Trigger action if listener is set
                if (actionListener != null) {
                    // Pass the current label as the event source
                    actionListener.actionPerformed(new ActionEvent(ClickableLabel.this, ActionEvent.ACTION_PERFORMED, "labelClicked"));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(normalColor);
            }
        });
    }

    // Custom color setters
    public void setNormalColor(Color color) {
        this.normalColor = color;
        setForeground(color);
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    public void setClickColor(Color color) {
        this.clickColor = color;
    }

    // Method to add an ActionListener (for click actions)
    public void addActionListener(ActionListener listener) {
        this.actionListener = listener;
    }
}
