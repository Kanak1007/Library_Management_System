package com.webkorps.librarymanagement.controller.Issue;

import com.webkorps.librarymanagement.dao.BookDao;
import com.webkorps.librarymanagement.dao.IssuedBookDao;
import com.webkorps.librarymanagement.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/IssueBook")
public class IssueBook extends HttpServlet {
    private BookDao bookDao;
    private IssuedBookDao issuedBookDao;

    @Override
    public void init() {
        bookDao = new BookDao();
        issuedBookDao = new IssuedBookDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Check if student is logged in
        if (session == null || session.getAttribute("studentid") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Get parameters from request
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            String issueDateStr = request.getParameter("issueDate");
            String returnDateStr = request.getParameter("returnDate");
            
            // Get student ID from session
            int studentId = (Integer) session.getAttribute("studentid");

            // Check if book exists and is available
            Book book = bookDao.getBookById(bookId);
            if (book == null) {
                request.setAttribute("error", "Book not found");
                response.sendRedirect("GetAvailableBooks");
                return;
            }

            if (book.getBookQuantity() <= 0) {
                request.setAttribute("error", "Book is not available for issue");
                response.sendRedirect("GetAvailableBooks");
                return;
            }

            // Convert string dates to SQL Date
            Date issueDate = Date.valueOf(issueDateStr);
            Date returnDate = Date.valueOf(returnDateStr);

            // Create new issued book record
            IssueBook issueBook = new IssueBook();
            issueBook.setBookId(bookId);
            issueBook.setStudentId(studentId);
            issueBook.setIssueDate(issueDate);
            issueBook.setReturnDate(returnDate);
            issueBook.setStatus("ISSUED");
            
            boolean success = issuedBookDao.issueBook(issueBook);

            if (success) {
                // Update book quantity
                book.setBookQuantity(book.getBookQuantity() - 1);
                bookDao.UpdateBook(book, bookId);
                
                request.setAttribute("success", "Book issued successfully");
            } else {
                request.setAttribute("error", "Failed to issue book. Please try again.");
            }

            response.sendRedirect("GetAvailableBooks");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid book ID");
            response.sendRedirect("GetAvailableBooks");
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            response.sendRedirect("GetAvailableBooks");
        }
    }
} 