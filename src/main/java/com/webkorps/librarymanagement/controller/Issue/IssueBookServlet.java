//package com.webkorps.librarymanagement.controller.Issue;
//
//import com.webkorps.librarymanagement.dao.BookDao;
//import com.webkorps.librarymanagement.dao.IssuedBookDao;
//import com.webkorps.librarymanagement.model.Book;
//import com.webkorps.librarymanagement.model.IssueBook;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import java.sql.Date;
//import java.util.List;
//
//@WebServlet("/IssueBook")
//public class IssueBookServlet extends HttpServlet {
//    private BookDao bookDao;
//    private IssuedBookDao issuedBookDao;
//
//    @Override
//    public void init() {
//        bookDao = new BookDao();
//        issuedBookDao = new IssuedBookDao();
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        System.out.println("IssueBookServlet: Starting doPost method");
//        HttpSession session = request.getSession(false);
//        
//        try {
//            // Check if student is logged in
//            if (session == null || session.getAttribute("studentid") == null) {
//                System.out.println("IssueBookServlet: No valid session found");
//                response.sendRedirect("login.jsp");
//                return;
//            }
//
//            // Get parameters from request
//            String bookIdStr = request.getParameter("bookId");
//            String issueDateStr = request.getParameter("issueDate");
//            String returnDateStr = request.getParameter("returnDate");
//            
//            System.out.println("IssueBookServlet: Received parameters - bookId: " + bookIdStr + 
//                             ", issueDate: " + issueDateStr + 
//                             ", returnDate: " + returnDateStr);
//            
//            if (bookIdStr == null || issueDateStr == null || returnDateStr == null) {
//                System.out.println("IssueBookServlet: Missing required parameters");
//                session.setAttribute("error", "Missing required parameters");
//                response.sendRedirect("GetAvailableBooks");
//                return;
//            }
//            
//            int bookId = Integer.parseInt(bookIdStr);
//            int studentId = (Integer) session.getAttribute("studentid");
//            
//            System.out.println("IssueBookServlet: Processing for studentId: " + studentId);
//
//            // Check if book exists and is available
//            Book book = bookDao.getBookById(bookId);
//            if (book == null) {
//                System.out.println("IssueBookServlet: Book not found with ID: " + bookId);
//                session.setAttribute("error", "Book not found");
//                response.sendRedirect("GetAvailableBooks");
//                return;
//            }
//
//            if (book.getBookQuantity() <= 0) {
//                System.out.println("IssueBookServlet: Book not available - ID: " + bookId);
//                session.setAttribute("error", "Book is not available for issue");
//                response.sendRedirect("GetAvailableBooks");
//                return;
//            }
//
//            // Convert string dates to SQL Date
//            Date issueDate = Date.valueOf(issueDateStr);
//            Date returnDate = Date.valueOf(returnDateStr);
//
//            // Validate dates
//            if (returnDate.before(issueDate)) {
//                System.out.println("IssueBookServlet: Invalid dates - return date before issue date");
//                session.setAttribute("error", "Return date cannot be before issue date");
//                response.sendRedirect("GetAvailableBooks");
//                return;
//            }
//
//            // Check if student already has this book issued
//            
////            List<IssueBook> issuedBooks = issuedBookDao.getIssuedBooksByStudent(studentId);
////            for (IssueBook existingIssue : issuedBooks) {
////                if (existingIssue.getBookId() == bookId && "ISSUED".equals(existingIssue.getStatus())) {
////                    System.out.println("IssueBookServlet: Student already has this book issued");
////                    session.setAttribute("error", "You already have this book issued");
////                    response.sendRedirect("GetAvailableBooks");
////                    return;
////                }
////            }
//
//            // Create new issued book record
//            IssueBook issueBook = new IssueBook();
//            issueBook.setBookId(bookId);
//            issueBook.setStudentId(studentId);
//            issueBook.setIssueDate(issueDate);
//            issueBook.setReturnDate(returnDate);
//            issueBook.setStatus("ISSUED");
//            
//            System.out.println("IssueBookServlet: Attempting to issue book - " + issueBook.toString());
//            
//            boolean success = issuedBookDao.issueBook(issueBook);
//
//            if (success) {
//                System.out.println("IssueBookServlet: Successfully issued book");
//                // Update book quantity
//                book.setBookQuantity(book.getBookQuantity() - 1);
//                boolean updateSuccess = bookDao.UpdateBook(book, bookId);
//                
//                if (!updateSuccess) {
//                    System.out.println("IssueBookServlet: Failed to update book quantity");
//                    session.setAttribute("error", "Failed to update book quantity");
//                } else {
//                    session.setAttribute("success", "Book issued successfully");
//                    response.sendRedirect("success.jsp");
//                }
//            }
//            else {
//                System.out.println("IssueBookServlet: Failed to issue book");
//                session.setAttribute("error", "Failed to issue book. Please try again.");
//            }
//
//            response.sendRedirect("error.jsp");
//
//        } catch (NumberFormatException e) {
//            System.out.println("IssueBookServlet: Invalid number format - " + e.getMessage());
//            session.setAttribute("error", "Invalid book ID");
//            response.sendRedirect("error.jsp");
//        } catch (IllegalArgumentException e) {
//            System.out.println("IssueBookServlet: Invalid date format - " + e.getMessage());
//            session.setAttribute("error", "Invalid date format");
//            response.sendRedirect("error.jsp");
//        } catch (Exception e) {
//            System.out.println("IssueBookServlet: Unexpected error - " + e.getMessage());
//            e.printStackTrace();
//            session.setAttribute("error", "An error occurred: " + e.getMessage());
//            response.sendRedirect("error.jsp");
//        }
//    }
//} 

//----------------------------------------------------------------------------------------------------------------------------------------------------------
package com.webkorps.librarymanagement.controller.Issue;

import com.webkorps.librarymanagement.dao.BookDao;
import com.webkorps.librarymanagement.dao.IssuedBookDao;
import com.webkorps.librarymanagement.model.Book;
import com.webkorps.librarymanagement.model.IssueBook;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/IssueBook")
public class IssueBookServlet extends HttpServlet {
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
        System.out.println("IssueBookServlet: Starting doPost method");
        HttpSession session = request.getSession(false);
        String nextPage = "GetAvailableBooks";
        
        try {
            // Check if student is logged in
            if (session == null || session.getAttribute("studentid") == null) {
                System.out.println("IssueBookServlet: No valid session found");
                nextPage = "login.jsp";
            } else {
                // Get parameters from request
                String bookIdStr = request.getParameter("bookId");
                String issueDateStr = request.getParameter("issueDate");
                String returnDateStr = request.getParameter("returnDate");
                
                System.out.println("IssueBookServlet: Parameters - bookId=" + bookIdStr + 
                                 ", issueDate=" + issueDateStr + 
                                 ", returnDate=" + returnDateStr);
                
                if (validateParameters(bookIdStr, issueDateStr, returnDateStr)) {
                    int bookId = Integer.parseInt(bookIdStr);
                    int studentId = (Integer) session.getAttribute("studentid");
                    
                    // Process book issue
                    if (processBookIssue(session, bookId, studentId, issueDateStr, returnDateStr)) {
                        nextPage = "success.jsp";
                    }
                } else {
                    session.setAttribute("error", "Missing or invalid parameters");
                }
            }
            
        } catch (Exception e) {
            System.out.println("IssueBookServlet Error: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("error", "An error occurred: " + e.getMessage());
        }
        
        response.sendRedirect(nextPage);
    }
    
    private boolean validateParameters(String bookId, String issueDate, String returnDate) {
        return bookId != null && !bookId.trim().isEmpty() &&
               issueDate != null && !issueDate.trim().isEmpty() &&
               returnDate != null && !returnDate.trim().isEmpty();
    }
    
    private boolean isBookAlreadyIssuedToStudent(int bookId, int studentId) {
        List<IssueBook> issuedBooks = issuedBookDao.getIssuedBooksByStudent(studentId);
        for (IssueBook existingIssue : issuedBooks) {
            if (existingIssue.getBookId() == bookId && "ISSUED".equals(existingIssue.getStatus())) {
                System.out.println("IssueBookServlet: Book already issued to student - bookId: " + 
                                 bookId + ", studentId: " + studentId);
                return true;
            }
        }
        return false;
    }
    
    private boolean processBookIssue(HttpSession session, int bookId, int studentId, 
                                String issueDateStr, String returnDateStr) {
        try {
            // Check if book exists and is available
            Book book = bookDao.getBookById(bookId);
            if (book == null) {
                session.setAttribute("error", "Book not found");
                return false;
            }
            
            if (book.getBookQuantity() <= 0) {
                session.setAttribute("error", "Book is not available for issue");
                return false;
            }
            
            // Check if student already has this book
            if (isBookAlreadyIssuedToStudent(bookId, studentId)) {
                session.setAttribute("error", "You already have this book issued");
                return false;
            }
            
            // Convert and validate dates
            Date issueDate = Date.valueOf(issueDateStr);
            Date returnDate = Date.valueOf(returnDateStr);
            
            if (returnDate.before(issueDate)) {
                session.setAttribute("error", "Return date cannot be before issue date");
                return false;
            }
            
            // Create and save issue record
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
                if (bookDao.UpdateBook(book, bookId)) {
                    session.setAttribute("success", "Book issued successfully");
                    return true;
                } else {
                    session.setAttribute("error", "Failed to update book quantity");
                }
            } else {
                session.setAttribute("error", "Failed to issue book");
            }
            
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", "Invalid date format");
        } catch (Exception e) {
            session.setAttribute("error", "Error processing book issue: " + e.getMessage());
        }
        return false;
    }
}
