package com.webkorps.librarymanagement.dao;

import com.webkorps.librarymanagement.model.ReturnBook;
import com.webkorps.librarymanagement.utility.DBconnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

public class ReturnBookDao {
    
    public boolean saveReturnBook(ReturnBook returnBook) {
        String sql = "INSERT INTO return_book (issue_id, book_id, student_id, issue_date, " +
                    "return_date, actual_return_date, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, returnBook.getIssueId());
            stmt.setInt(2, returnBook.getBookId());
            stmt.setInt(3, returnBook.getStudentId());
            stmt.setDate(4, returnBook.getIssueDate());
            stmt.setDate(5, returnBook.getReturnDate());
            stmt.setDate(6, returnBook.getActualReturnDate());
            stmt.setString(7, returnBook.getStatus());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error saving return book: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateBookQuantity(int bookId) {
        String sql = "UPDATE book SET quantity = quantity + 1 WHERE book_id = ?";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating book quantity: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public Map<String, Object> getIssueDetails(int issueId) {
        String sql = "SELECT i.*, b.book_id, b.name as book_name, s.membership_id, s.name as student_name " +
                    "FROM issued_book i " +
                    "JOIN book b ON i.book_id = b.book_id " +
                    "JOIN student s ON i.student_id = s.membership_id " +
                    "WHERE i.issue_id = ?";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, issueId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Map<String, Object> details = new HashMap<>();
                details.put("issueId", rs.getInt("issue_id"));
                details.put("bookId", rs.getInt("book_id"));
                details.put("bookName", rs.getString("book_name"));
                details.put("studentId", rs.getInt("student_id"));
                details.put("studentName", rs.getString("student_name"));
                details.put("issueDate", rs.getDate("issue_date"));
                details.put("returnDate", rs.getDate("return_date"));
                return details;
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting issue details: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
} 