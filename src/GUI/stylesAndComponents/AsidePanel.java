package GUI.stylesAndComponents;

import javax.swing.*;
import java.awt.*;

public class AsidePanel extends JPanel {
    private Color color1 = new Color(128, 0, 128);     // Purple
    private Color color2 = new Color(0, 0, 139);     // Dark Blue

    public AsidePanel() 
    {
        setOpaque(false); // Ensures background is painted transparently
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        GradientPaint gradient = new GradientPaint(0, 0, color1, width, height, color2);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }

    // Optional: allow customization
    public void setGradientColors(Color start, Color end) {
        this.color1 = start;
        this.color2 = end;
        repaint();
    }
}

