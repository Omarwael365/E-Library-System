package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Classes.Customers;
import Classes.Publisher;
import Database.DAO.UsersDAO;
import GUI.stylesAndComponents.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.regex.Pattern;

public class SignupPage extends JPanel implements ActionListener
{
    MainFrame mainFrame;

    JButton SignupAsCustomer = new JButton("Signup As Customer");
    JButton SignupAsPublisher = new JButton("Signup As Publisher");

    JTextField name;
    JTextField email;
    JPasswordField password;

    public SignupPage(MainFrame frame) 
    {
        this.mainFrame = frame;

        setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        // Title Panel
        StyledPanel topOfPage = new StyledPanel();
        JLabel titleLabel = new JLabel("Signup Page", SwingConstants.CENTER);
        
        Labels.styleELibraryLabel(titleLabel,"title");
        titleLabel.setBorder(new EmptyBorder(50, 0, 30, 0)); // Adjusted padding to move up the title label

        topOfPage.add(titleLabel);
        add(topOfPage, BorderLayout.NORTH);

        // Center panel
        StyledPanel centerPanel = new StyledPanel();

        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(30, 30, 30));


        // name Label and TextField
        JLabel nameLabel = new JLabel("Enter Name: ");
        Labels.styleELibraryLabel(nameLabel,"normal");
        name = new JTextField();
        TextFields.styleELibraryTextField(name);


        // email Label and TextField
        JLabel emailLabel = new JLabel("Enter Email: ");
        Labels.styleELibraryLabel(emailLabel,"normal");
        email = new JTextField();
        TextFields.styleELibraryTextField(email);

        // Password Label and TextField
        JLabel passwordLabel = new JLabel("Enter Password: ");
        Labels.styleELibraryLabel(passwordLabel,"normal");
        password = new JPasswordField();
        TextFields.styleELibraryTextField(password);


        Buttons.styleELibraryButton(SignupAsCustomer);
        Buttons.styleELibraryButton(SignupAsPublisher);
        SignupAsCustomer.setPreferredSize(new Dimension(200, 75));
        SignupAsPublisher.setPreferredSize(new Dimension(200, 75));

        // Button panel
        StyledPanel buttonPanel = new StyledPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 40));
        buttonPanel.add(SignupAsCustomer);
        buttonPanel.add(SignupAsPublisher);

        // GridBagConstraints for positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // column index
        gbc.gridy = 0; // row index
        gbc.insets = new Insets(30, 50, 0, 50); // Reduced padding to move elements up more
        gbc.anchor = GridBagConstraints.CENTER;

        centerPanel.add(nameLabel);

        gbc.gridx = 1;
        centerPanel.add(name);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(email, gbc);

        gbc.gridy = 2; // Move to the next row
        gbc.gridx = 0;
        centerPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(password, gbc);

        add(centerPanel, BorderLayout.CENTER); // Add centerPanel to the center of the layout
        add(buttonPanel, BorderLayout.SOUTH); // Add centerPanel to the center of the layout

        SignupAsCustomer.addActionListener(this);
        SignupAsPublisher.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String isEmailUsed = email.getText().trim();
        String Inputname = name.getText().trim();

        if (e.getSource() == SignupAsCustomer) 
        {
            if(!isValidFullName(Inputname))
            {
                CustomDialogUtil.showStyledMessage(null, "Name Field can't be empty", "Invalid Name");
            }
            else if(UsersDAO.getUserByEmail(isEmailUsed))
            {
                CustomDialogUtil.showStyledMessage(null, "Email already exists in Database \n Account Exists", "Email exists");
            }
            else if(!isValidEmail(isEmailUsed)) 
            {
                CustomDialogUtil.showStyledMessage(null, "Make sure Email is written correctly", "Invalid email");
            }
            else if(!isValidPassword(password))
            {
                CustomDialogUtil.showStyledMessage(null, "Password is either too short or contains spaces", "Invalid Password");
            }
            else
            {
                CustomDialogUtil.showStyledMessage(null, "Acount Added Successfully \n you'll be redirected to the Login Page", "Approved");
                
                Customers.addCustomers(Inputname, isEmailUsed, new String (password.getPassword()));
                
                mainFrame.showPage("login");
            }
        }

        if (e.getSource() == SignupAsPublisher) 
        {
            if(!isValidFullName(Inputname))
            {
                CustomDialogUtil.showStyledMessage(null, "Name Field can't be empty", "Invalid Name");
            }
            else if(UsersDAO.checkIfEmailExists(isEmailUsed))
            {
                CustomDialogUtil.showStyledMessage(null, "Email already exists in Database \n Account Exists", "Email exists");
            }
            else if(!isValidEmail(isEmailUsed)) 
            {
                CustomDialogUtil.showStyledMessage(null, "Make sure Email is written correctly", "Invalid email");
            }
            else if(!isValidPassword(password))
            {
                CustomDialogUtil.showStyledMessage(null, "Password is either too short or contains spaces", "Invalid Password");
            }
            else 
            {
                CustomDialogUtil.showStyledMessage(null, "Acount Added Successfully \n you'll be redirected to the Login Page", "Approved");
                
                Publisher.addPublisher(Inputname, isEmailUsed, new String (password.getPassword()));

                mainFrame.showPage("login");
            }

            mainFrame.showPage("login");
        }
    }

    public static boolean isValidEmail(String email) 
    {
        // Simple regex for email validation
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex, email);
    }

    public static boolean isValidPassword(JPasswordField passwordField) 
    {
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
    
        // Check length
        if (password.length() <= 10) {
            return false;
        }
    
        // Check for spaces
        if (password.contains(" ")) {
            return false;
        }
    
        return true;
    }

    public static boolean isValidFullName(String name) 
    {
        if (name == null || name.isEmpty()) {
            return false;
        }
        // Letters and single spaces between words
        return name.matches("^[A-Za-z]+(?: [A-Za-z]+)*$");
    }
    
    

}
