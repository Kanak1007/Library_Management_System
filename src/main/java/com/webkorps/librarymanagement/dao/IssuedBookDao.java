package com.webkorps.librarymanagement.dao;

import com.webkorps.librarymanagement.model.BookRequest;
import com.webkorps.librarymanagement.model.IssueBook;
import com.webkorps.librarymanagement.utility.DBconnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssuedBookDao {
    
    public boolean issueBook(IssueBook issueBook) {
        String sql = "INSERT INTO issued_book (book_id, student_id, issue_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, issueBook.getBookId());
            stmt.setInt(2, issueBook.getStudentId());
            stmt.setDate(3, issueBook.getIssueDate());
            stmt.setDate(4, issueBook.getReturnDate());
            stmt.setString(5, "ISSUED");
            
            System.out.println("IssuedBookDao: Executing SQL - " + sql);
            System.out.println("IssuedBookDao: Parameters - bookId: " + issueBook.getBookId() + 
                             ", studentId: " + issueBook.getStudentId() +
                             ", issueDate: " + issueBook.getIssueDate() +
                             ", returnDate: " + issueBook.getReturnDate());
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("IssuedBookDao: Rows affected by insert: " + rowsAffected);
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("IssuedBookDao: Error issuing book - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
 public int getBookIdByIssueId(int issueId) {
    String sql = "SELECT book_id FROM issued_book WHERE issue_id = ?";
    try (Connection conn = DBconnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, issueId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("book_id");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1; // Return -1 if not found or error occurs
}


    public List<Map<String, Object>> getIssuedBooksWithDetails(int studentId) {
        List<Map<String, Object>> issuedBooksWithDetails = new ArrayList<>();
        String sql = "SELECT i.issue_id, i.book_id, i.student_id, i.issue_date, i.return_date, i.status, " +
                    "b.name as book_name, b.author, b.edition " +
                    "FROM issued_book i " +
                    "JOIN book b ON i.book_id = b.book_id " +
                    "WHERE i.student_id = ? AND i.status = 'ISSUED' " +
                    "ORDER BY i.issue_date DESC";
        
        System.out.println("IssuedBookDao: Starting getIssuedBooksWithDetails for student: " + studentId);
        System.out.println("IssuedBookDao: SQL Query: " + sql);
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            System.out.println("IssuedBookDao: Executing query with studentId: " + studentId);
            
            ResultSet rs = stmt.executeQuery();
            System.out.println("IssuedBookDao: Query executed successfully");
            
            while (rs.next()) {
                Map<String, Object> bookDetails = new HashMap<>();
                bookDetails.put("issueId", rs.getInt("issue_id"));
                bookDetails.put("bookId", rs.getInt("book_id"));
                bookDetails.put("bookName", rs.getString("book_name"));
                bookDetails.put("author", rs.getString("author"));
                bookDetails.put("edition", rs.getString("edition"));
                bookDetails.put("issueDate", rs.getDate("issue_date"));
                bookDetails.put("returnDate", rs.getDate("return_date"));
                bookDetails.put("status", rs.getString("status"));
                
                // Calculate days remaining or overdue
                long daysRemaining = calculateDaysRemaining(rs.getDate("return_date"));
                bookDetails.put("daysRemaining", daysRemaining);
                bookDetails.put("isOverdue", daysRemaining < 0);
                
                System.out.println("IssuedBookDao: Found book - " + 
                                 "IssueID: " + bookDetails.get("issueId") +
                                 ", BookID: " + bookDetails.get("bookId") +
                                 ", Name: " + bookDetails.get("bookName") + 
                                 ", Author: " + bookDetails.get("author") + 
                                 ", Status: " + bookDetails.get("status") +
                                 ", IssueDate: " + bookDetails.get("issueDate") +
                                 ", ReturnDate: " + bookDetails.get("returnDate") +
                                 ", Days Remaining: " + daysRemaining);
                
                issuedBooksWithDetails.add(bookDetails);
            }
            
            System.out.println("IssuedBookDao: Total books found: " + issuedBooksWithDetails.size());
            
        } catch (SQLException e) {
            System.out.println("IssuedBookDao: Error fetching issued books with details - " + e.getMessage());
            System.out.println("IssuedBookDao: SQL State: " + e.getSQLState());
            System.out.println("IssuedBookDao: Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
        
        return issuedBooksWithDetails;
    }
    
    private long calculateDaysRemaining(Date returnDate) {
        if (returnDate == null) return 0;
        
        long currentTime = System.currentTimeMillis();
        long returnTime = returnDate.getTime();
        return (returnTime - currentTime) / (1000 * 60 * 60 * 24);
    }

    public List<IssueBook> getIssuedBooksByStudent(int studentId) {
        List<IssueBook> issuedBooks = new ArrayList<>();
        String sql = "SELECT i.*, b.name as book_name FROM issued_book i " +
                    "JOIN book b ON i.book_id = b.book_id " +
                    "WHERE i.student_id = ? AND i.status = 'ISSUED'";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            System.out.println("IssuedBookDao: Checking issued books for student: " + studentId);
            System.out.println("IssuedBookDao: Executing SQL - " + sql + " with studentId=" + studentId);
            
            ResultSet rs = stmt.executeQuery();
            System.out.println("IssuedBookDao: Query executed successfully");
            
            while (rs.next()) {
                IssueBook issuedbook = new IssueBook();
                issuedbook.setIssueId(rs.getInt("issue_id"));
                issuedbook.setBookId(rs.getInt("book_id"));
                issuedbook.setStudentId(rs.getInt("student_id"));
                issuedbook.setIssueDate(rs.getDate("issue_date"));
                issuedbook.setReturnDate(rs.getDate("return_date"));
                issuedbook.setStatus(rs.getString("status"));
                
                System.out.println("IssuedBookDao: Found active issue - " +
                                 "IssueID: " + issuedbook.getIssueId() + 
                                 ", BookID: " + issuedbook.getBookId() + 
                                 ", BookName: " + rs.getString("book_name") +
                                 ", Status: " + issuedbook.getStatus() +
                                 ", IssueDate: " + issuedbook.getIssueDate() +
                                 ", ReturnDate: " + issuedbook.getReturnDate());
                
                issuedBooks.add(issuedbook);
            }
            
            System.out.println("IssuedBookDao: Total active issued books found for student " + 
                             studentId + ": " + issuedBooks.size());
            
        } catch (SQLException e) {
            System.out.println("IssuedBookDao: Error getting issued books - " + e.getMessage());
            System.out.println("IssuedBookDao: SQL State: " + e.getSQLState());
            e.printStackTrace();
        }
        
        return issuedBooks;
    }
    
    public boolean returnBook(int issueId) {
        String sql = "UPDATE issued_book SET status = 'RETURNED' WHERE issue_id = ?";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, issueId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("IssuedBookDao: Rows affected by return: " + rowsAffected);
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("IssuedBookDao: Error returning book - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<IssueBook> getOverdueBooks() {
        List<IssueBook> overdueBooks = new ArrayList<>();
        String sql = "SELECT * FROM issued_book WHERE status = 'ISSUED' AND return_date < CURRENT_DATE";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                IssueBook book = new IssueBook();
                book.setIssueId(rs.getInt("issue_id"));
                book.setBookId(rs.getInt("book_id"));
                book.setStudentId(rs.getInt("student_id"));
                book.setIssueDate(rs.getDate("issue_date"));
                book.setReturnDate(rs.getDate("return_date"));
                book.setStatus(rs.getString("status"));
                overdueBooks.add(book);
            }
            
        } catch (SQLException e) {
            System.out.println("IssuedBookDao: Error getting overdue books - " + e.getMessage());
            e.printStackTrace();
        }
        
        return overdueBooks;
    }

    public boolean renewBook(int issueId, String issueDate, String returnDate) {
        String sql = "UPDATE issued_book SET issue_date = ?, return_date = ? WHERE issue_id = ?";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(issueDate));
            stmt.setDate(2, Date.valueOf(returnDate));
            stmt.setInt(3, issueId);
            
            System.out.println("IssuedBookDao: Renewing book with issueId: " + issueId);
            System.out.println("IssuedBookDao: New issue date: " + issueDate);
            System.out.println("IssuedBookDao: New return date: " + returnDate);
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("IssuedBookDao: Rows affected by renewal: " + rowsAffected);
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("IssuedBookDao: Error renewing book - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, Object>> getAllIssuedBooksWithDetails() {
        List<Map<String, Object>> issuedBooksWithDetails = new ArrayList<>();
        String sql = "SELECT i.issue_id, i.book_id, i.student_id, i.issue_date, i.return_date, i.status, " +
                    "b.name as book_name, b.author, b.edition " +
                    "FROM issued_book i " +
                    "JOIN book b ON i.book_id = b.book_id " +
                    "WHERE i.status = 'ISSUED' " +
                    "ORDER BY i.issue_date DESC";
        
        System.out.println("IssuedBookDao: Starting getAllIssuedBooksWithDetails");
        System.out.println("IssuedBookDao: SQL Query: " + sql);
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            System.out.println("IssuedBookDao: Query executed successfully");
            
            while (rs.next()) {
                Map<String, Object> bookDetails = new HashMap<>();
                bookDetails.put("issueId", rs.getInt("issue_id"));
                bookDetails.put("bookId", rs.getInt("book_id"));
                bookDetails.put("studentId", rs.getInt("student_id"));
                bookDetails.put("bookName", rs.getString("book_name"));
                bookDetails.put("author", rs.getString("author"));
                bookDetails.put("edition", rs.getString("edition"));
                bookDetails.put("issueDate", rs.getDate("issue_date"));
                bookDetails.put("returnDate", rs.getDate("return_date"));
                bookDetails.put("status", rs.getString("status"));
                
                // Calculate days remaining or overdue
                long daysRemaining = calculateDaysRemaining(rs.getDate("return_date"));
                bookDetails.put("daysRemaining", daysRemaining);
                bookDetails.put("isOverdue", daysRemaining < 0);
                
                System.out.println("IssuedBookDao: Found book - " + 
                                 "IssueID: " + bookDetails.get("issueId") +
                                 ", BookID: " + bookDetails.get("bookId") +
                                 ", Name: " + bookDetails.get("bookName") + 
                                 ", Author: " + bookDetails.get("author") + 
                                 ", Status: " + bookDetails.get("status") +
                                 ", IssueDate: " + bookDetails.get("issueDate") +
                                 ", ReturnDate: " + bookDetails.get("returnDate") +
                                 ", Days Remaining: " + daysRemaining);
                
                issuedBooksWithDetails.add(bookDetails);
            }
            
            System.out.println("IssuedBookDao: Total books found: " + issuedBooksWithDetails.size());
            
        } catch (SQLException e) {
            System.out.println("IssuedBookDao: Error fetching all issued books with details - " + e.getMessage());
            System.out.println("IssuedBookDao: SQL State: " + e.getSQLState());
            System.out.println("IssuedBookDao: Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
        
        return issuedBooksWithDetails;
    }

    public List<Map<String, Object>> getReturnedBooksWithDetails(int studentId) {
        List<Map<String, Object>> returnedBooksWithDetails = new ArrayList<>();
        String sql = "SELECT i.issue_id, i.book_id, i.student_id, i.issue_date, i.return_date, i.status, " +
                    "b.name, b.author, b.edition, " +
                    "r.actual_return_date " +
                    "FROM issued_book i " +
                    "JOIN book b ON i.book_id = b.book_id " +
                    "JOIN return_book r ON i.issue_id = r.issue_id " +
                    "WHERE i.student_id = ? AND i.status = 'RETURNED' " +
                    "ORDER BY r.actual_return_date DESC";
        
        System.out.println("IssuedBookDao: Starting getReturnedBooksWithDetails for student: " + studentId);
        System.out.println("IssuedBookDao: SQL Query: " + sql);
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            System.out.println("IssuedBookDao: Executing query with studentId: " + studentId);
            
            ResultSet rs = stmt.executeQuery();
            System.out.println("IssuedBookDao: Query executed successfully");
            
            while (rs.next()) {
                Map<String, Object> bookDetails = new HashMap<>();
                bookDetails.put("issueId", rs.getInt("issue_id"));
                bookDetails.put("bookId", rs.getInt("book_id"));
                bookDetails.put("bookName", rs.getString("name"));
                bookDetails.put("author", rs.getString("author"));
                bookDetails.put("edition", rs.getString("edition"));
                bookDetails.put("issueDate", rs.getDate("issue_date"));
                bookDetails.put("returnDate", rs.getDate("return_date"));
                bookDetails.put("actualReturnDate", rs.getDate("actual_return_date"));
                bookDetails.put("status", "RETURNED");
                
                // Debug logging
                System.out.println("IssuedBookDao: Found book - " + 
                                 "\n  IssueID: " + bookDetails.get("issueId") +
                                 "\n  BookID: " + bookDetails.get("bookId") +
                                 "\n  Name: " + bookDetails.get("bookName") + 
                                 "\n  Author: " + bookDetails.get("author") + 
                                 "\n  Edition: " + bookDetails.get("edition") +
                                 "\n  IssueDate: " + bookDetails.get("issueDate") +
                                 "\n  ReturnDate: " + bookDetails.get("returnDate") +
                                 "\n  ActualReturnDate: " + bookDetails.get("actualReturnDate") +
                                 "\n  Status: " + bookDetails.get("status"));
                
                returnedBooksWithDetails.add(bookDetails);
            }
            
            System.out.println("IssuedBookDao: Total returned books found: " + returnedBooksWithDetails.size());
            
        } catch (SQLException e) {
            System.out.println("IssuedBookDao: Error fetching returned books with details - " + e.getMessage());
            System.out.println("IssuedBookDao: SQL State: " + e.getSQLState());
            System.out.println("IssuedBookDao: Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
        
        return returnedBooksWithDetails;
    }
public boolean saveRequest(BookRequest bookRequest){
  boolean result = false;
      
        String query = "INSERT INTO book_request (book_id, student_id, request_date, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bookRequest.getBookId());
            ps.setInt(2, bookRequest.getStudentId());
            ps.setDate(3, bookRequest.getRequestDate());
            ps.setString(4, bookRequest.getStatus());

            int rows = ps.executeUpdate();
            result = rows > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // or log the exception properly
        }

        return result;
    }

public boolean hasAlreadyRequested(int bookId, int studentId) {
    boolean alreadyRequested = false;
    String query = "SELECT 1 FROM book_request WHERE book_id = ? AND student_id = ? AND status = 'Pending'";

    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, bookId);
        ps.setInt(2, studentId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                alreadyRequested = true; // found a matching record
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return alreadyRequested;
}

   public BookRequest getFirstPendingRequest(int bookId) {
    String sql = "SELECT * FROM book_request WHERE book_id = ? AND status = 'Pending' ORDER BY request_date ASC LIMIT 1";
    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, bookId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            BookRequest request = new BookRequest();
            request.setRequestId(rs.getInt("request_id"));
            request.setBookId(rs.getInt("book_id"));
            request.setStudentId(rs.getInt("student_id"));
            request.setRequestDate(rs.getDate("request_date"));
            request.setStatus(rs.getString("status"));
            return request;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

    public boolean markRequestAsFulfilled(int requestId) {
    String sql = "UPDATE book_request SET status = 'Fulfilled' WHERE request_id = ?";
    try (Connection conn = DBconnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, requestId);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
 


    public boolean autoIssueBookToRequester(BookRequest request) {
    try (Connection con = DBconnection.getConnection();
         PreparedStatement pst = con.prepareStatement(
             "INSERT INTO issued_book (book_id, student_id, issue_date, return_date, status) VALUES (?, ?, ?, ?, ?)")) {
        pst.setInt(1, request.getBookId());
        pst.setInt(2, request.getStudentId());
        pst.setDate(3, Date.valueOf(LocalDate.now()));
        pst.setDate(4, Date.valueOf(LocalDate.now().plusDays(7))); // Set return date as 2 weeks later (example)
        pst.setString(5, "ISSUED");
        return pst.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

    
public boolean isBookAlreadyIssuedToStudent(int bookId, int studentId) {
    boolean result = false;
    String sql = "SELECT COUNT(*) FROM issued_book WHERE book_id = ? AND student_id = ? AND status = 'ISSUED'";
    
    try (Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, bookId);
        ps.setInt(2, studentId);
        
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            result = rs.getInt(1) > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    return result;
}

} 
