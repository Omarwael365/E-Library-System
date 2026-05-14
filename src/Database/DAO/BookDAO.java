package Database.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Classes.Books;
import Database.DBConnection;


public class BookDAO 
{
    // UPDATE: Update book price
    public static boolean updateBookPrice(int bookId, double newPrice) 
    {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Books SET price = ? WHERE book_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean deleteBook(int bookId) {
        try (Connection conn = DBConnection.getConnection()) {
            // First, delete from BorrowedBooks
            String deleteFromBorrowedBooks = "DELETE FROM BorrowedBooks WHERE book_id = ?";
            PreparedStatement stmt1 = conn.prepareStatement(deleteFromBorrowedBooks);
            stmt1.setInt(1, bookId);
            stmt1.executeUpdate();
    
            // Then, delete from OwnedBooks
            String deleteFromOwnedBooks = "DELETE FROM OwnedBooks WHERE book_id = ?";
            PreparedStatement stmt2 = conn.prepareStatement(deleteFromOwnedBooks);
            stmt2.setInt(1, bookId);
            stmt2.executeUpdate();
    
            // Finally, delete from Books
            String sql = "DELETE FROM Books WHERE book_id = ?";
            PreparedStatement stmt3 = conn.prepareStatement(sql);
            stmt3.setInt(1, bookId);
            stmt3.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Return false if an exception occurs
        }
        return true;
    }
    


    // CREATE: Add a new book with cover image and text file
    public static void addBookWithFiles(Books book, File coverImageFile, File bookTextFile) 
    {
        try (Connection conn = DBConnection.getConnection()) 
        {
            // SQL query to insert book details along with the files as BLOBs
            String sql = "INSERT INTO Books (book_id, book_name, price, category, publisher_id, cover_image, book_text) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            // Set book details
            stmt.setInt(1, book.getBookId());
            stmt.setString(2, book.getBookName());
            stmt.setDouble(3, book.getPrice());
            stmt.setString(4, book.getCategory());
            stmt.setInt(5, book.getPublisherID());
            
            // Set cover image as BLOB
            FileInputStream coverImageStream = new FileInputStream(coverImageFile);
            stmt.setBinaryStream(6, coverImageStream, (int) coverImageFile.length());

            // Set book text file as BLOB
            FileInputStream bookTextStream = new FileInputStream(bookTextFile);
            stmt.setBinaryStream(7, bookTextStream, (int) bookTextFile.length());
            
            // Execute the query
            stmt.executeUpdate();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public static void loadAllBooks() 
    {
        HashMap<Integer, Books> allBooksList = new HashMap<>();
        HashMap<Integer, ArrayList<Books>> booksByPublisher = new HashMap<>();
        Books book;

        String sql = "SELECT * FROM Books";
    
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) 
        {
            while (rs.next()) 
            {
                int bookId = rs.getInt("book_id");
                int publisherId = rs.getInt("publisher_id");
                String bookName = rs.getString("book_name");
                double price = rs.getDouble("price");
                String category = rs.getString("category");

                // Read cover image BLOB
                InputStream coverImageStream = rs.getBinaryStream("cover_image");
                File coverImageFile = null;
                if (coverImageStream != null) 
                {
                    coverImageFile = new File("covers/book_" + bookId + "_cover.jpg");
                    coverImageFile.getParentFile().mkdirs(); // create folder if not exist
                    try (FileOutputStream fos = new FileOutputStream(coverImageFile)) 
                    {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = coverImageStream.read(buffer)) != -1) 
                        {
                        fos.write(buffer, 0, bytesRead); // Fixed offset
                        }
                    }
                }

                // Read text file BLOB
                InputStream bookTextStream = rs.getBinaryStream("book_text");
                File bookTextFile = null;
                if (bookTextStream != null) 
                {
                    bookTextFile = new File("books/book_" + bookId + "_text.txt");
                    bookTextFile.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(bookTextFile)) 
                    {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = bookTextStream.read(buffer)) != -1) 
                        {
                        fos.write(buffer, 0, bytesRead); // Fixed offset
                        }
                    }
                }

                // Create Book object using updated constructor
                book = new Books(publisherId, bookName, price, category, bookId, coverImageFile, bookTextFile);

                allBooksList.put(bookId, book);

                // Add to Book Hashmap
                if (!booksByPublisher.containsKey(book.getPublisherID())) 
                {
                    booksByPublisher.put(book.getPublisherID(), new ArrayList<>());
                }

                // Add the book to the publisher's list in the hashmap
                booksByPublisher.get(book.getPublisherID()).add(book);
            }
        }    
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        Books.setAllBooksList(allBooksList);
        Books.setbooksByPublisher(booksByPublisher);
    }


    public static int giveID() 
    {
        Random R = new Random();

        // 1) Load all used IDs into a Set
        Set<Integer> usedIds = new HashSet<>();

        String sql = "SELECT book_id FROM Books";
        try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) 
            {
                usedIds.add(rs.getInt("book_id"));
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
