//package com.webkorps.librarymanagement.controller.Books;
//
//import com.webkorps.librarymanagement.model.Book;
//import com.webkorps.librarymanagement.model.IssuedBook;
//import com.webkorps.librarymanagement.model.Student;
//import com.webkorps.librarymanagement.model.User;
//import com.webkorps.librarymanagement.service.BookService;
//import com.webkorps.librarymanagement.service.IssuedBookService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet(name = "IssueBook", urlPatterns = {"/IssueBook"})
//public class IssueBook extends HttpServlet {
//    private BookService bookService;
//    private IssuedBookService issuedBookService;
//    
//    @Override
//    public void init() throws ServletException {
//        this.bookService = new BookService();
//        this.issuedBookService = new IssuedBookService();
//    }
//    
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("user") == null) {
//            response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
//            return;
//        }
//        
//        User user = (User) session.getAttribute("user");
//        
//        // Check for overdue books
//        if (issuedBookService.hasOverdueBooks(user.getUserId())) {
//            List<IssuedBook> overdueBooks = issuedBookService.getOverdueBooks(user.getUserId());
//            request.setAttribute("overdueBooks", overdueBooks);
//            request.setAttribute("error", "You have overdue books. Please return them before issuing new books.");
//            request.getRequestDispatcher("overduebooks.jsp").forward(request, response);
//            return;
//        }
//        
//        // Get all available books
//        request.setAttribute("books", bookService.getAllBooks());
//        request.getRequestDispatcher("issuebook.jsp").forward(request, response);
//    }
//    
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("user") == null) {
//            response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
//            return;
//        }
//        
//        try {
//            int bookId = Integer.parseInt(request.getParameter("bookId"));
//            Student user = (Student ) session.getAttribute("user");
//            
//            // Check for overdue books
//            if (issuedBookService.hasOverdueBooks(user.getUserId())) {
//                List<IssuedBook> overdueBooks = issuedBookService.getOverdueBooks(user.getUserId());
//                request.setAttribute("overdueBooks", overdueBooks);
//                request.setAttribute("error", "You have overdue books. Please return them before issuing new books.");
//                request.getRequestDispatcher("overduebooks.jsp").forward(request, response);
//                return;
//            }
//            
//            // Check if book is available
//            if (!issuedBookService.isBookAvailable(bookId)) {
//                request.setAttribute("error", "Book is not available for issuing");
//                request.getRequestDispatcher("issuebook.jsp").forward(request, response);
//                return;
//            }
//            
//            // Issue the book
//            boolean issued = issuedBookService.issueBook(bookId, user.getUserId());
//            
//            if (issued) {
//                request.setAttribute("success", "Book issued successfully! Please return it within 10 days.");
//            } else {
//                request.setAttribute("error", "Failed to issue book. Please try again.");
//            }
//            
//            // Get updated list of books
//            request.setAttribute("books", bookService.getAllBooks());
//            request.getRequestDispatcher("issuebook.jsp").forward(request, response);
//            
//        } catch (NumberFormatException e) {
//            request.setAttribute("error", "Invalid book ID");
//            request.getRequestDispatcher("issuebook.jsp").forward(request, response);
//        }
//    }
//} 