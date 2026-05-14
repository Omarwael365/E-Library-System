package GUI.PublishersPanels;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;

import Classes.Publisher;
import GUI.MainFrame;
import GUI.stylesAndComponents.StyledPanel;

public class ViewYourBookReviews extends JPanel
{
    MainFrame mainFrame;
    Publisher publisher;
    
    public ViewYourBookReviews(MainFrame mainFrame, Publisher publisher)
    {
        this.mainFrame = mainFrame;
        this.publisher = publisher;

        StyledPanel mainpanel = new StyledPanel();
        mainpanel.setLayout(new FlowLayout());
        add(mainpanel);
    }
}