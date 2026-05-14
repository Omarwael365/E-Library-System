package Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Database.DAO.ReviewDAO;

public class Reviews
{
    private String review;
    private int id;
    private int bookID;
    private int raterID;

    // Static list to hold all reviews globally
    private static ArrayList<Reviews> AllReviewsList = new ArrayList<>();

    //hashmap the key is the Books ID and the cvalue is an array list of type Reviews
    private static Map<Integer, ArrayList<Reviews>> ReviewsofBooks = new HashMap<>();
    
    public Reviews(int bookID,int raterID, String review)
    {
        this.review = review;
        this.id = ReviewDAO.giveID();
        this.bookID = bookID;
        this.raterID = raterID;
    }
    public Reviews(int id,int bookID,int raterID, String review)
    {
        this.review = review;
        this.id = id;
        this.bookID = bookID;
        this.raterID = raterID;
    }
    
    //getters and setters

    // Static method to get all reviews
    public static ArrayList<Reviews> getAllReviewsList() 
    {
        return AllReviewsList;
    }
    public static void setAllReviewsList(ArrayList<Reviews> AllReviewsList2) 
    {
        AllReviewsList = AllReviewsList2;
    }

    public static Map<Integer, ArrayList<Reviews>> getReviewsofBooks() 
    {
        return ReviewsofBooks;
    }
    public static void setReviewsofBooks(Map<Integer, ArrayList<Reviews>> AllReviewsList2) 
    {
        ReviewsofBooks = AllReviewsList2;
    }


    public String getReview()
    {
        return review;
    }
    public void setReview(String review)
    {
        this.review = review;
    }
    public int getBookID()
    {
        return bookID;
    }
    public int getRaterId()
    {
        return raterID;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

}
