package GUI.CustomerPanels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Classes.Customers;
import Database.DAO.UsersDAO;
import GUI.MainFrame;
import GUI.SignupPage;
import GUI.stylesAndComponents.*;

public class CUpdateProfile extends JPanel implements ActionListener {
    MainFrame mainFrame;
    Customers customer;

    JTextField emailField;
    JPasswordField passwordField;
    JButton updateButton;

    public CUpdateProfile(MainFrame mainFrame, Customers customer) {
        this.mainFrame = mainFrame;
        this.customer = customer;

        // Panel styling and layout
        StyledPanel mainpanel = new StyledPanel();
        mainpanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email label and field
        JLabel emailLabel = new JLabel("New Email:");
        Labels.styleELibraryLabel(emailLabel, "normal");

        emailField = new JTextField();
        TextFields.styleELibraryTextField(emailField);

        // Password label and field
        JLabel passwordLabel = new JLabel("New Password:");
        Labels.styleELibraryLabel(passwordLabel, "normal");

        passwordField = new JPasswordField();
        TextFields.styleELibraryTextField(passwordField);

        // Update button
        updateButton = new JButton("Update Profile");
        Buttons.styleELibraryButton(updateButton);
        updateButton.setPreferredSize(new Dimension(200, 50));

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainpanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        mainpanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainpanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        mainpanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainpanel.add(updateButton, gbc);

        // Add to main panel
        setLayout(new BorderLayout());
        add(mainpanel, BorderLayout.CENTER);

        updateButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String isEmailUsed = emailField.getText().trim();
    
        if (e.getSource() == updateButton) 
        {
            if (UsersDAO.getUserByEmail(isEmailUsed)) 
            {
                CustomDialogUtil.showStyledMessage(null, "Email already exists in Database \n Account Exists", "Email exists");
            } 
            else if (!SignupPage.isValidEmail(isEmailUsed)) 
            {
                CustomDialogUtil.showStyledMessage(null, "Make sure Email is written correctly", "Invalid email");
            }
            else if (!SignupPage.isValidPassword(passwordField))
            {
                CustomDialogUtil.showStyledMessage(null, "Password is either too short or contains spaces", "Invalid Password");
            } 
            else 
            {
                CustomDialogUtil.showStyledMessage(null, "Profile Updated Successfully", "Approved");
                
                UsersDAO.updateUser(customer.getId(), isEmailUsed, new String(passwordField.getPassword()));
                customer.setEmail(isEmailUsed);
                customer.setPassword(new String(passwordField.getPassword()));
            }
        }
    }
}
