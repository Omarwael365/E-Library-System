package GUI.stylesAndComponents;

import javax.swing.*;
import java.awt.*;

public class StyledPanel extends JPanel {

    private Color colorStart = new Color(30, 30, 30);
    private Color colorEnd = new Color(48, 25, 52);

    public StyledPanel() 
    {
        setOpaque(false); // Important for transparency and proper gradient painting
    }

    public StyledPanel(Color start, Color end) {
        this.colorStart = start;
        this.colorEnd = end;
        setOpaque(false);
    }

    public void setGradientColors(Color start, Color end) {
        this.colorStart = start;
        this.colorEnd = end;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, colorStart, width, height, colorEnd);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
    }
}
