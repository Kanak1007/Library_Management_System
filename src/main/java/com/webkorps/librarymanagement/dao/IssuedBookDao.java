package com.webkorps.librarymanagement.dao;

import com.webkorps.librarymanagement.model.IssueBook;
import com.webkorps.librarymanagement.utility.DBconnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
} 