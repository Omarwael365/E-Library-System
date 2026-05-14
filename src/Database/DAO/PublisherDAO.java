package Database.DAO;

import java.sql.*;
import java.util.ArrayList;

import Classes.Customers;
import Classes.Publisher;
import Database.DBConnection;


public class PublisherDAO {

    
    // CREATE: Add new customer
    public static void addPublishers(Publisher publisher) 
    {
        try (Connection conn = DBConnection.getConnection()) 
        {
            String sql = "INSERT INTO Users (id, name, email, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, publisher.getId());
            stmt.setString(2, publisher.getName());
            stmt.setString(3, publisher.getEmail());
            stmt.setString(4, publisher.getPassword());
            stmt.setString(5, "publisher");
            stmt.executeUpdate();

            // Insert into Customers table after Users table
            String sql2 = "INSERT INTO Publishers (id) VALUES (?)";
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setInt(1, publisher.getId());
            stmt2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ all publishers
    public static void loadAllPublishers() 
    {
        ArrayList<Publisher> publishers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {

            String sql = "SELECT u.id, u.name, u.email, u.password FROM Users u JOIN Publishers p ON u.id = p.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) 
            {
                Publisher publisher = new Publisher(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getInt("id")
                );
                publishers.add(publisher);
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Publisher.setPublisherList(publishers);
    }
}
