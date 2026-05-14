package Database.DAO;

import java.sql.*;
import java.util.ArrayList;

import Classes.Admin;
import Database.DBConnection;


public class AdminsDAO 
{

    // READ: Get all admins
    public static void loadAllAdmins() 
    {
        ArrayList<Admin> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) 
        {
            String sql = "SELECT u.id, u.name, u.email, u.password " +
            "FROM Users u " +
            "INNER JOIN Admins a ON u.id = a.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                Admin admin = new Admin
                (rs.getString("name"), 
                rs.getString("email"), 
                rs.getString("password"),
                rs.getInt("id")
                );
                list.add(admin);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        Admin.setAdminList(list);
    }
    
}