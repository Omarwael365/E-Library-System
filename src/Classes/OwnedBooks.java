package Classes;

import java.util.ArrayList;
import java.util.HashMap;

public class OwnedBooks 
{
    private int bookid;
    private int customerID;

    private static ArrayList<OwnedBooks> ownedBookslList = new ArrayList<>();
    private static HashMap<Integer, ArrayList<Integer>> customerOwnedBooks = new HashMap<>();

    public OwnedBooks(int customerID, int bookid)
    {
        this.bookid=bookid;
        this.customerID=customerID;
    }

    public static ArrayList<OwnedBooks> getownedbookslList()
    {
        return ownedBookslList;
    }
    public static void setownedbookslList(ArrayList<OwnedBooks> ownedBookslList2)
    {
        ownedBookslList = ownedBookslList2;
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

    public static void setcustomerOwnedBooks(HashMap<Integer, ArrayList<Integer>> customerOwnedBooks2)
    {
        customerOwnedBooks = customerOwnedBooks2;
    }
    public static HashMap<Integer, ArrayList<Integer>> getcustomerOwnedBooks()
    {
        return customerOwnedBooks;
    }
}
