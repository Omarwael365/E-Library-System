package Database.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import Classes.OwnedBooks;
import Database.DBConnection;

public class OwnedBooksDAO {

    public static void insertOwnedBook(int customerId, int bookId) 
    {
        String query = "INSERT INTO OwnedBooks (customer_id, book_id) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public static void loadAllOwnedBooks() 
    { 
        ArrayList<OwnedBooks> list = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> customerOwnedBooks = new HashMap<>();

        String query = "SELECT * FROM OwnedBooks";
        try (Connection con = DBConnection.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) 
            {
                OwnedBooks ownedbook = new OwnedBooks(
                rs.getInt("customer_id"),
                rs.getInt("book_id")
                );
                list.add(ownedbook);

                // Step 3: Group books by publisherId
                int customerID = ownedbook.getcustomerID();

                // Check if the publisherId already has a list in the map
                if (!customerOwnedBooks.containsKey(customerID)) 
                {
                    customerOwnedBooks.put(customerID, new ArrayList<>());
                }

                // Add the book to the publisher's list
                customerOwnedBooks.get(customerID).add(ownedbook.getbookid());
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        OwnedBooks.setownedbookslList(list);
        OwnedBooks.setcustomerOwnedBooks(customerOwnedBooks);
    }
}
