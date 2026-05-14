package GUI.stylesAndComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Buttons 
{
    public static void styleELibraryButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        // Multi-color gradient for normal and hover states
        final Color[] normalColors = 
        {
            new Color(52, 152, 219), // Blue
            new Color(41, 128, 185)  // Darker Blue
        };
        final Color[] hoverColors = 
        {
            new Color(241, 85, 127), // Pink
            new Color(252, 142, 68)  // Orange
        };
    
        final boolean[] isHover = {false};
    
        // Custom painting for the colorful gradient button
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
                // Shadow effect when hovered
                if (isHover[0]) {
                    g2.setColor(new Color(0, 0, 0, 50)); // Semi-transparent shadow
                    g2.fillRoundRect(4, 4, c.getWidth() - 8, c.getHeight() - 8, 20, 20);
                }
    
                // Gradient effect based on hover state
                GradientPaint gp;
                if (isHover[0]) 
                {
                    gp = new GradientPaint
                    (
                        0, 0, hoverColors[0],
                        0, c.getHeight(), hoverColors[1]
                    );
                } else 
                {
                    gp = new GradientPaint(
                        0, 0, normalColors[0],
                        0, c.getHeight(), normalColors[1]
                    );
                }
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
    
                // Draw the text centered in the button
                FontMetrics fm = g2.getFontMetrics();
                String text = button.getText();
                int x = (c.getWidth() - fm.stringWidth(text)) / 2;
                int y = (c.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
    
                g2.setColor(button.getForeground());
                g2.setFont(button.getFont());
                g2.drawString(text, x, y);
    
                g2.dispose();
            }
        });
    
        // Hover effect tracking with smooth transition
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHover[0] = true;
                button.repaint();
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                isHover[0] = false;
                button.repaint();
            }
        });
    
        // Optional: Add a timer for smoother color transition
        Timer timer = new Timer(10, e -> button.repaint());
        timer.start();
    }
}