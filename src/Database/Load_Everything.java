package Database;

import Classes.*;
import Database.DAO.*;
public class Load_Everything 
{
    public static void load()
    { 
        AdminsDAO.loadAllAdmins(); //✅      
        CustomerDAO.loadAllCustomers(); //✅
        PublisherDAO.loadAllPublishers(); //✅
        
        BookDAO.loadAllBooks(); //✅
        
        for(Publisher p: Publisher.getPublisherList()) //✅
        {
            if (Books.getbooksByPublisher().containsKey(p.getId())) 
            {
                p.setBookList(Books.getbooksByPublisher().get(p.getId()));
            }
        }

        BorrowedBooksDAO.loadAllBorrowedBooks(); //✅
        OwnedBooksDAO.loadAllOwnedBooks(); //✅

        // Load Borrowed Books for each customer
        for (Customers c : Customers.getCustomersList()) {
            if (BorrowedBooks.getcustomerBorrowedBooks().containsKey(c.getId())) {
                for (int i = 0; i < BorrowedBooks.getcustomerBorrowedBooks().get(c.getId()).size(); i++) {
                    Integer bookId = BorrowedBooks.getcustomerBorrowedBooks().get(c.getId()).get(i);
                    if (Books.getAllBooksList().containsKey(bookId)) {
                        c.getBorrowedBookList().add(Books.getAllBooksList().get(bookId)); 
                    }
                }
            }
        }

        // Load Owned Books for each customer
        for (Customers c : Customers.getCustomersList()) {
            if (OwnedBooks.getcustomerOwnedBooks().containsKey(c.getId())) {
                for (int i = 0; i < OwnedBooks.getcustomerOwnedBooks().get(c.getId()).size(); i++) {
                    Integer bookId = OwnedBooks.getcustomerOwnedBooks().get(c.getId()).get(i);
                    if (Books.getAllBooksList().containsKey(bookId)) {
                        c.getOwnedBookList().add(Books.getAllBooksList().get(bookId));
                    }
                }
            }
        }


        Database.DAO.ReviewDAO.loadAllReviews(); //✅

        for(Integer key: Books.getAllBooksList().keySet()) //✅
        {
            if(Reviews.getReviewsofBooks().containsKey(key))
            {
                Books.getAllBooksList().get(key).setreviews(Reviews.getReviewsofBooks().get(key));
            }
        }
    }
}
