package Database.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Classes.Users;
import Database.DBConnection;

public class UsersDAO 
{

    // UPDATE publisher's user data (email, password)
    public static void updateUser(int id, String email, String password) 
    {
        try (Connection conn = DBConnection.getConnection()) 
        {
            String sql = "UPDATE Users SET email = ?, password = ? WHERE id = ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int id) 
    {
        try (Connection conn = DBConnection.getConnection()) 
        {
            // First, delete reviews where the user is the reviewer
            String deleteReviews = "DELETE FROM Reviews WHERE user_id = ?";
            PreparedStatement stmt1 = conn.prepareStatement(deleteReviews);
            stmt1.setInt(1, id);
            stmt1.executeUpdate();
            
            // Then, delete the user from the Users table
            String deleteUser = "DELETE FROM Users WHERE id = ?";
            PreparedStatement stmt2 = conn.prepareStatement(deleteUser);
            stmt2.setInt(1, id);
            stmt2.executeUpdate();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    

    //gor login
    public static String getUserByEmailAndPassword(String email, String passwordInput) 
    {
        try (Connection conn = DBConnection.getConnection()) 
        {
            String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, passwordInput);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) 
            {
                String role = rs.getString("role");
    
                if (role.equalsIgnoreCase("customer")) 
                {
                    return role;
                }
                else if (role.equalsIgnoreCase("publisher")) 
                {
                    return role;
                } 
                else if (role.equalsIgnoreCase("admin")) 
                {
                    return role;
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
    
    //for sign up
    public static Boolean getUserByEmail(String email) 
    {
        try (Connection conn = DBConnection.getConnection()) 
        {
            String sql = "SELECT * FROM Users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) 
            {
                return true;
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean checkIfEmailExists(String email) 
    {
    try (Connection conn = DBConnection.getConnection()) 
    {
        String sql = "SELECT * FROM Users WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) 
        {
            return true;
        }
    } 
    catch (Exception e) 
    {
        e.printStackTrace();
    }
    return false;
    }

    public static int giveID() 
    {
        Random R = new Random();

        // 1) Load all used IDs into a Set
        Set<Integer> usedIds = new HashSet<>();

        String sql = "SELECT id FROM Users";
        try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) 
            {
                usedIds.add(rs.getInt("id"));
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        // 2) Generate until we find an unused one
        int potentialID;
        do 
        {
            potentialID = R.nextInt(Integer.MAX_VALUE) + 1;
        } 
        while (usedIds.contains(potentialID));

        return potentialID;
    }
}