package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Classes.Admin;
import Classes.Customers;
import Classes.Publisher;
import Database.DAO.UsersDAO;
import GUI.stylesAndComponents.Buttons;
import GUI.stylesAndComponents.CustomDialogUtil;
import GUI.stylesAndComponents.Labels;
import GUI.stylesAndComponents.StyledPanel;
import GUI.stylesAndComponents.TextFields;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JPanel  implements ActionListener
{
    MainFrame mainFrame;
    JButton login;
    JTextField email;
    JPasswordField password;

    public LoginPage(MainFrame frame) 
    {
        this.mainFrame = frame;

        setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        // Title Panel
        StyledPanel topOfPage = new StyledPanel();
        JLabel titleLabel = new JLabel("Login Page", SwingConstants.CENTER);
        
        Labels.styleELibraryLabel(titleLabel,"title");
        titleLabel.setBorder(new EmptyBorder(50, 0, 30, 0)); // Adjusted padding to move up the title label

        topOfPage.add(titleLabel);
        add(topOfPage, BorderLayout.NORTH);

        // Center panel
        StyledPanel centerPanel = new StyledPanel();

        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(30, 30, 30));

        // email Label and TextField
        JLabel emailLabel = new JLabel("Enter email: ");
        Labels.styleELibraryLabel(emailLabel,"normal");
        email = new JTextField();
        TextFields.styleELibraryTextField(email);

        // Password Label and TextField
        JLabel passwordLabel = new JLabel("Enter Password");
        Labels.styleELibraryLabel(passwordLabel,"normal");
        password = new JPasswordField();
        TextFields.styleELibraryTextField(password);

        //
        login = new JButton("Login");
        login.setPreferredSize(new Dimension(150, 75));
        Buttons.styleELibraryButton(login);

        StyledPanel buttonPanel = new StyledPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 40));
        buttonPanel.add(login);

        // GridBagConstraints for positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // column index
        gbc.gridy = 0; // row index
        gbc.insets = new Insets(10, 0, 20, 50); // Reduced padding to move elements up more
        gbc.anchor = GridBagConstraints.CENTER;

        centerPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(email, gbc);

        gbc.gridy = 1; // Move to the next row
        gbc.gridx = 0;
        centerPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(password, gbc);

        add(centerPanel, BorderLayout.CENTER); // Add centerPanel to the center of the layout
        add(buttonPanel,BorderLayout.SOUTH);

        login.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == login)
        {
            String inputEmail = email.getText().trim();
            String inputPassword = new String(password.getPassword());
            String role = UsersDAO.getUserByEmailAndPassword(inputEmail, inputPassword);

            if(role == null)
            {
                CustomDialogUtil.showStyledMessage(null,"Invalid Email or Password  \n PLease Try Again","Invalid Login Credentials");
            }
            else if (role.equalsIgnoreCase("customer")) 
            {

                Customers customer = null;
                for(Customers c: Customers.getCustomersList())
                {
                    if (c.getEmail().equalsIgnoreCase(inputEmail))
                    {
                        customer = c;
                        break;
                    }
                }
                if (customer != null) 
                {
                    CustomerPage customerPage = new CustomerPage(mainFrame,customer);
                    mainFrame.mainPanel.add(customerPage,"customerPage");

                    mainFrame.showPage("customerPage");
                } 
                else 
                {
                    CustomDialogUtil.showStyledMessage(null, "Login failed", "Error");
                }
                
            }
            else if (role.equalsIgnoreCase("publisher")) 
            {
                Publisher publisher = null;
                for(Publisher p: Publisher.getPublisherList())
                {
                    if (p.getEmail().equalsIgnoreCase(inputEmail))
                    {
                        publisher = p;
                        break;
                    }
                }
                if (publisher != null) 
                {
                    PublisherPage publisherpage = new PublisherPage(mainFrame,publisher);
                    mainFrame.mainPanel.add(publisherpage,"publisherpage");

                    mainFrame.showPage("publisherpage");
                } 
                else 
                {
                    CustomDialogUtil.showStyledMessage(null, "Login failed", "Error");
                }
                
            } 
            else if (role.equalsIgnoreCase("admin")) 
            {
                Admin admin = null;
                for(Admin a: Admin.getAdminList())
                {
                    if (a.getEmail().equalsIgnoreCase(inputEmail))
                    {
                        admin = a;
                        break;
                    }
                }
                if (admin != null) 
                {
                    AdminPage adminPage = new AdminPage(mainFrame,admin);
                    mainFrame.mainPanel.add(adminPage,"adminPage");

                    mainFrame.showPage("adminPage");
                } 
                else 
                {
                    CustomDialogUtil.showStyledMessage(null, "Login failed", "Error");
                }
                
            }
        }
        email.setText("");
        password.setText("");
    }
}