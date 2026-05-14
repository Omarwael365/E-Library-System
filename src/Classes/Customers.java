package Classes;
import java.util.*;

import Database.DAO.CustomerDAO;

public class Customers extends Users 
{
    private static ArrayList<Customers> customersList = new ArrayList<Customers>();

    private ArrayList<Books> ownedBookList;
    private ArrayList<Books> borrowedBookList;

    private ArrayList<Books> potentialOwnedBooks = new ArrayList<>();
    private ArrayList<Books> potentialBorrowedBooks = new ArrayList<>();

    public Customers(String name, String email,String password, int id)
    {
        super(name,"customer",email,password,id);
        this.ownedBookList = new ArrayList<Books>();
        this.borrowedBookList = new ArrayList<Books>();
    }
    public Customers(String name, String email,String password)
    {
        super(name,"customer",email,password);
        this.ownedBookList = new ArrayList<Books>();
        this.borrowedBookList = new ArrayList<Books>();
    }


    public static ArrayList<Customers> getCustomersList() 
    {
        return customersList;
    }
    public static void setCustomersList(ArrayList<Customers> customersList2)
    {
        customersList = customersList2;
    }

    public ArrayList<Books> getOwnedBookList() 
    {
        return ownedBookList;
    }

    public ArrayList<Books> getBorrowedBookList() 
    {
        return borrowedBookList;
    }    
    
    static public void addCustomers(String name, String email,String password)
    {
        Customers newc = new Customers(name,email,password);
        customersList.add(newc);

        CustomerDAO.addCustomer(newc);
    }

    public static void borrowBook(int CustomersId, String bookName)
    {
        Customers customers = findCustomersById(CustomersId);
        if (customers == null) 
        {
            System.out.println("Customers not found!");
            return;
        }

        Books bookToBorrow = Books.findBookByName(bookName);
        if (bookToBorrow == null) 
        {
            System.out.println("Book not available");
            return;
        }

        //Add to BorrowedBooks class Array
        BorrowedBooks.getBorrowedList().add(new BorrowedBooks(customers.getId(),bookToBorrow.getBookId()));
        
        customers.borrowedBookList.add(bookToBorrow);

        System.out.println(customers.getName()+ " borrowed " + bookName );
    }

    public static void returnBook(int customerId, String bookName)  //TODO
    {
        Customers customer = findCustomersById(customerId);
        if (customer == null) 
        {
            System.out.println("Customer not found!");
            return;
        }
    
        Books bookToRemove = null; 
        for (Books book : customer.borrowedBookList) 
        {
            if (book.getBookName().equalsIgnoreCase(bookName)) 
            {
                bookToRemove = book; 
                break; 
            }
        }
    
        if (bookToRemove != null) 
        {
            customer.borrowedBookList.remove(bookToRemove); 
            System.out.println(customer.getName() + " returned " + bookName);
        } 
        else 
        {
            System.out.println(customer.getName() + " did not borrow " + bookName);
        }
    }

    public static void buyBook(int CustomersId, String bookName) 
    {
        Customers customers = findCustomersById(CustomersId);
        if (customers == null) {
            System.out.println("Customers not found!");
            return;
        }

        Books bookToBuy = Books.findBookByName(bookName);
        if (bookToBuy == null) {
            System.out.println("Book not available");
            return;
        }

        //Add to BorrowedBooks class Array
        OwnedBooks.getownedbookslList().add(new OwnedBooks(customers.getId(),bookToBuy.getBookId()));

        customers.ownedBookList.add(bookToBuy);

        System.out.println(customers.getName() + " purchased \"" + bookName);
    }


    public static Customers findCustomersById(int CustomersId) 
    {
        for (Customers Customers : customersList) 
        {
            if (Customers.getId() == CustomersId) 
            {
                return Customers;
            }
        }
        return null;
    }

    //seters and getters
    public ArrayList<Books> getpotentialOwnedBooks()
    {
        return potentialOwnedBooks;
    }
    
    public void setpotentialOwnedBooks(ArrayList<Books> potentialOwnedBooks)
    {
        this.potentialOwnedBooks = potentialOwnedBooks;
    }
    

    public ArrayList<Books> getpotentialBorroedbooks()
    {
        return potentialBorrowedBooks;
    }

    public void setpotentialBorroedbooks(ArrayList<Books> potentialBorrowedBooks)
    {
        this.potentialBorrowedBooks = potentialBorrowedBooks;
    }
}