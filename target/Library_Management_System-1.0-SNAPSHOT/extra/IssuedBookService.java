ackage com.webkorps.librarymanagement.service;

import com.webkorps.librarymanagement.dao.IssuedBookDao;
import com.webkorps.librarymanagement.model.IssuedBook;
import java.sql.Date;
import java.util.List;

public class IssuedBookService {
    private IssuedBookDao issuedBookDao;
    
    public IssuedBookService() {
        this.issuedBookDao = new IssuedBookDao();
    }
    
    public boolean issueBook(int bookId, int userId) {
        // Check if user has any overdue books
        if (issuedBookDao.hasOverdueBooks(userId)) {
            return false;
        }
        
        // Check if book is available
        if (!issuedBookDao.isBookAvailable(bookId)) {
            return false;
        }
        
        // Create issued book record
        IssuedBook issuedBook = new IssuedBook(bookId, userId, new Date(System.currentTimeMillis()));
        boolean issued = issuedBookDao.issueBook(issuedBook);
        
        if (issued) {
            // Decrease book quantity
            return issuedBookDao.updateBookQuantity(bookId, -1);
        }
        return false;
    }
    
    public boolean returnBook(int issueId, int bookId) {
        boolean returned = issuedBookDao.returnBook(issueId);
        
        if (returned) {
            // Increase book quantity
            return issuedBookDao.updateBookQuantity(bookId, 1);
        }
        return false;
    }
    
    public List<IssuedBook> getIssuedBooksByUser(int userId) {
        return issuedBookDao.getIssuedBooksByUser(userId);
    }
    
    public List<IssuedBook> getAllIssuedBooks() {
        return issuedBookDao.getAllIssuedBooks();
    }
    
    public List<IssuedBook> getOverdueBooks(int userId) {
        return issuedBookDao.getOverdueBooks(userId);
    }
    
    public boolean isBookAvailable(int bookId) {
        return issuedBookDao.isBookAvailable(bookId);
    }
    
    public boolean hasOverdueBooks(int userId) {
        return issuedBookDao.hasOverdueBooks(userId);
    }
} 