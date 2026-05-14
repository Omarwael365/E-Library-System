package GUI.stylesAndComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import GUI.MainFrame;

public class Menu implements ActionListener
{
    MainFrame frame;
    JMenuItem about;
    JMenuItem signout;
    JMenuItem exit;

    public void createMenuBar(MainFrame frame) 
    {
        this.frame = frame;
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(102, 51, 153));
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Add spacer to push next items to the right
        menuBar.add(Box.createHorizontalGlue());

        // Menus
        JMenu helpMenu = new JMenu("Help");
        JMenu exitMenu = new JMenu("Exit");

        helpMenu.setForeground(Color.WHITE);
        exitMenu.setForeground(Color.WHITE);
        helpMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exitMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Menu Items
        about = createStyledMenuItem("About");
        signout = createStyledMenuItem("Sign Out");
        exit = createStyledMenuItem("Exit");

        helpMenu.add(about);
        exitMenu.add(signout);
        exitMenu.add(exit);

        menuBar.add(helpMenu);
        menuBar.add(exitMenu);

        menuBar.setPreferredSize(new Dimension(0, 40));

        exit.addActionListener(this);
        signout.addActionListener(this);
        about.addActionListener(this);

        frame.setJMenuBar(menuBar); // Attach to the frame
    }

    private JMenuItem createStyledMenuItem(String text) 
    {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        item.setForeground(Color.WHITE);
        item.setBackground(new Color(50, 50, 50));
        item.setOpaque(true);

        // Hover effect
        item.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                item.setBackground(new Color(80, 80, 80));
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                item.setBackground(new Color(50, 50, 50));
            }
        });

        return item;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == exit)
        {
            frame.dispose();
            System.exit(0);
        }
        else if(e.getSource() == signout)
        {
            frame.showPage("front");
        }
        else if(e.getSource() == about)
        {
            CustomDialogUtil.showStyledMessage(null,"Welcome to the E-Library System\n" + "Your digital gateway to reading, renting, and publishing books — all in one place.\n" + "\n" + "Explore a wide collection of books, rent your favorites with ease, or publish your own work to share with the world." + "\n" + "Whether you're a reader, a learner, or an aspiring author, the E-Library System is here to support your journey.","About E-Library");
        }
    }
}
