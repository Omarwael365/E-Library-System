package Database.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Classes.BorrowedBooks;
import Database.DBConnection;

public class BorrowedBooksDAO 
{

    public static void insertBorrowedBook(int customerId, int bookId) 
    {
        String query = "INSERT INTO BorrowedBooks (customer_id, book_id) VALUES (?, ?)";
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

    public static void deleteBorrowedBook(int customerId, int bookId) 
    {
        String query = "DELETE FROM BorrowedBooks WHERE customer_id = ? AND book_id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadAllBorrowedBooks() 
    {
        ArrayList<BorrowedBooks> list = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> customerBorrowedBooks = new HashMap<>();

        String query = "SELECT * FROM BorrowedBooks";
        try (Connection con = DBConnection.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) 
            {
                BorrowedBooks borrowedBooks = new BorrowedBooks(
                rs.getInt("customer_id"),
                rs.getInt("book_id")
                );
                list.add(borrowedBooks);

                // Step 3: Group books by publisherId
                int customerID = borrowedBooks.getcustomerID();

                // Check if the publisherId already has a list in the map
                if (!customerBorrowedBooks.containsKey(customerID)) 
                {
                    customerBorrowedBooks.put(customerID, new ArrayList<>());
                }

                // Add the book to the publisher's list
                customerBorrowedBooks.get(customerID).add(borrowedBooks.getbookid());
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
        BorrowedBooks.setBorrowedList(list);
        BorrowedBooks.setcustomerBorrowedBooks(customerBorrowedBooks);
    }
}
