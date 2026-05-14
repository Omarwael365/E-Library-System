package Database.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Classes.Reviews;
import Database.DBConnection;

public class ReviewDAO {

    // CREATE - Add a new review
    public static void addReview(Reviews review) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Reviews (review_id, book_id, user_id, review_text) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, review.getId());
            stmt.setInt(2, review.getBookID());
            stmt.setInt(3, review.getRaterId());
            stmt.setString(4, review.getReview());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE - Update an existing review
    public static void updateReview(int reviewId, String newText) 
    {
        try (Connection conn = DBConnection.getConnection()) 
        {
            String sql = "UPDATE Reviews SET review_text = ? WHERE review_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newText);
            stmt.setInt(2, reviewId);
            stmt.executeUpdate();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    // DELETE - Already implemented
    public static void deleteReview(int reviewId) 
    {
        try (Connection conn = DBConnection.getConnection()) 
        {
            String sql = "DELETE FROM Reviews WHERE review_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reviewId);
            stmt.executeUpdate();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    // READ: Get all reviews from the database
public static void loadAllReviews() {
    // Initialize the list for reviews and the map for book reviews
    ArrayList<Reviews> reviewsList = new ArrayList<>();
    Map<Integer, ArrayList<Reviews>> reviewsByBookId = new HashMap<>();

    // Use try-with-resources to automatically close resources (connection, statement, result set)
    String query = "SELECT * FROM Reviews"; // Define query once for reuse
    try (Connection conn = DBConnection.getConnection(); // Get connection
         PreparedStatement stmt = conn.prepareStatement(query); // Prepare the statement
         ResultSet rs = stmt.executeQuery()) { // Execute the query

        // Process each result from the query
        while (rs.next()) {
            // Create a new Review object for each record retrieved
            Reviews review = new Reviews(
                rs.getInt("review_id"),
                rs.getInt("book_id"),
                rs.getInt("user_id"),
                rs.getString("review_text")
            );

            // Add the review to the global list
            reviewsList.add(review);

            // Group reviews by book ID
            int bookId = review.getBookID();
            reviewsByBookId.computeIfAbsent(bookId, k -> new ArrayList<>()).add(review); // Add the review to the corresponding book list
        }
    } catch (SQLException e) {
        System.err.println("Error while loading reviews from the database: " + e.getMessage()); // More informative error message
        e.printStackTrace(); // Log exception if something goes wrong
    }

    // Set the globally accessible review data in the Reviews class
    Reviews.setAllReviewsList(reviewsList);
    Reviews.setReviewsofBooks(reviewsByBookId);
}


    public static int giveID() 
    {
        Random R = new Random();

        // 1) Load all used IDs into a Set
        Set<Integer> usedIds = new HashSet<>();

        String sql = "SELECT review_id FROM Reviews";
        try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) 
            {
                usedIds.add(rs.getInt("review_id"));
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
