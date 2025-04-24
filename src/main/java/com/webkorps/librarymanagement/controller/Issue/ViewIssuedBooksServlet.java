package com.webkorps.librarymanagement.controller.Issue;

import com.webkorps.librarymanagement.dao.IssuedBookDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/ViewIssuedBooks")
public class ViewIssuedBooksServlet extends HttpServlet {
    private IssuedBookDao issuedBookDao;

    @Override
    public void init() {
        issuedBookDao = new IssuedBookDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("ViewIssuedBooksServlet: Starting doGet method");
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("studentid") == null) {
            System.out.println("ViewIssuedBooksServlet: No valid session found");
            response.sendRedirect("studentlogin.jsp");
            return;
        }

        try {
            int studentId = (Integer) session.getAttribute("studentid");
            System.out.println("ViewIssuedBooksServlet: Fetching issued books for student: " + studentId);
            
            List<Map<String, Object>> issuedBooks = issuedBookDao.getIssuedBooksWithDetails(studentId);
            
            // Set attributes for the JSP
            request.setAttribute("issuedBooks", issuedBooks);
            request.setAttribute("totalBooks", issuedBooks.size());
            
            // Count overdue books
            long overdueCount = issuedBooks.stream()
                    .filter(book -> (Boolean) book.get("isOverdue"))
                    .count();
            request.setAttribute("overdueCount", overdueCount);
            
            System.out.println("ViewIssuedBooksServlet: Found " + issuedBooks.size() + 
                             " issued books, " + overdueCount + " overdue");
            
            // Forward to the JSP
            request.getRequestDispatcher("issuedbooks.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("ViewIssuedBooksServlet Error: " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("error", "Error fetching issued books: " + e.getMessage());
            response.sendRedirect("error.jsp");
        }
    }
} 