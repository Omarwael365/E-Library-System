package Database.DAO;

import java.sql.*;
import java.util.ArrayList;
import Classes.Customers;
import Database.DBConnection;

public class CustomerDAO 
{
    // CREATE: Add new customer
    public static void addCustomer(Customers customer) 
    {
        try (Connection conn = DBConnection.getConnection()) 
        {
            String sql = "INSERT INTO Users (id, name, email, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customer.getId());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, "customer");
            stmt.executeUpdate();

            // Insert into Customers table after Users table
            String sql2 = "INSERT INTO Customers (id) VALUES (?)";
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setInt(1, customer.getId());
            stmt2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ: Get all customers
    public static void loadAllCustomers() 
    {
        ArrayList<Customers> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT u.id, u.name, u.email, u.password " +
                        "FROM Users u " +
                        "INNER JOIN Customers c ON u.id = c.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customers customer = new Customers(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getInt("id")
                );
                list.add(customer);
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        Customers.setCustomersList(list);
    }

}
