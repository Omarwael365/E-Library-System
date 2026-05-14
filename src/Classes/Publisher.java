package Classes;
import java.io.File;
import java.util.ArrayList;

import Database.DAO.BookDAO;
import Database.DAO.PublisherDAO;

public class Publisher extends Users
{
    private static ArrayList<Publisher> publisherList = new ArrayList<Publisher>();

    private ArrayList<Books> bookList;
    
    public Publisher(String name, String email, String password, int id)
    { 
        super(name,"publisher", email, password, id);
        this.bookList = new ArrayList<Books>();
    }
    public Publisher(String name, String email, String password)
    { 
        super(name,"publisher", email, password);
        this.bookList = new ArrayList<Books>();
    }
    
    // Getters and Setters
    public static ArrayList<Publisher> getPublisherList() 
    {
        return publisherList;
    }
    public static void setPublisherList(ArrayList<Publisher> publisherList2) 
    {
        publisherList = publisherList2;
    }

    public ArrayList<Books> getBookList() 
    {
        return bookList;
    }

    public void setBookList(ArrayList<Books> bookList2) 
    {
        this.bookList = bookList2;
    }

    public static void addPublisher(String name, String email, String password)
    {
        Publisher newp = new Publisher(name, email, password);
        publisherList.add(newp);

        PublisherDAO.addPublishers(newp);
    }

    public void addBook(String bookName, double price, String category, File coverImageFile, File bookTextFile) 
    {
        // Create the new book
        Books newBook = new Books(this.getId(), bookName, price, category, coverImageFile, bookTextFile);
        
        // Add to the publisher's list
        // Prevent adding duplicates
        if (!this.bookList.contains(newBook)) 
        {
            this.bookList.add(newBook);
        }

        // Add to Book Hashmap
        if (!Books.getbooksByPublisher().containsKey(this.getId())) 
        {
            Books.getbooksByPublisher().put(this.getId(), new ArrayList<>());
        }

        // Add the book to the publisher's list in the hashmap
        ArrayList<Books> publisherBooks = Books.getbooksByPublisher().get(this.getId());
        if (!publisherBooks.contains(newBook)) 
        {
            publisherBooks.add(newBook);
        }

            
        // Add to the global book list
        Books.getAllBooksList().put(newBook.getBookId(), newBook);

        BookDAO.addBookWithFiles(newBook, coverImageFile, bookTextFile);
        return;
    }
}