package Classes;

import java.util.ArrayList;
import java.util.HashMap;

public class BorrowedBooks 
{
    private int bookid;
    private int customerID;

    private static ArrayList<BorrowedBooks> BorrowedList = new ArrayList<>();
    private static HashMap<Integer, ArrayList<Integer>> customerBorrowedBooks = new HashMap<>(); //key is customerid

    public BorrowedBooks(int customerID,int bookid)
    {
        this.bookid=bookid;
        this.customerID=customerID;
    }

    public static void setBorrowedList(ArrayList<BorrowedBooks> BorrowedList2)
    {
        BorrowedList = BorrowedList2;
    }
    public static ArrayList<BorrowedBooks> getBorrowedList()
    {
        return BorrowedList;
    }

    public int getbookid()
    {
        return this.bookid;
    }
    public void setBookid(int bookid)
    {
        this.bookid = bookid;
    }

    public int getcustomerID()
    {
        return this.customerID;
    }
    public void setcustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    public static void setcustomerBorrowedBooks(HashMap<Integer, ArrayList<Integer>> customerBorrowedBooks2)
    {
        customerBorrowedBooks = customerBorrowedBooks2;
    }
    public static HashMap<Integer, ArrayList<Integer>> getcustomerBorrowedBooks()
    {
        return customerBorrowedBooks;
    }
}
