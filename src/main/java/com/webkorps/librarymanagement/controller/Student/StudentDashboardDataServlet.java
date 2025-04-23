package com.webkorps.librarymanagement.controller.Student;

import com.webkorps.librarymanagement.dao.BookDao;
import com.webkorps.librarymanagement.dao.IssuedBookDao;
import com.webkorps.librarymanagement.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/StudentDashboardData")
public class StudentDashboardDataServlet extends HttpServlet {
    private IssuedBookDao issuedBookDao;
    private BookDao bookDao;

    @Override
    public void init() throws ServletException {
        issuedBookDao = new IssuedBookDao();
        bookDao = new BookDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        
        if (student == null) {
            response.sendRedirect("studentlogin.jsp");
            return;
        }
        
        // Get total available books
        int availableBooks = bookDao.getAvailableBookCount();
        
        // Get issued books for the student
        List<Map<String, Object>> issuedBooks = issuedBookDao.getIssuedBooksWithDetails(student.getMembershipId());
        
        // Count books in different categories
        int totalIssued = 0;
        int dueSoon = 0;
        int overdue = 0;
        
        for (Map<String, Object> book : issuedBooks) {
            totalIssued++;
            long daysRemaining = (long) book.get("daysRemaining");
            boolean isOverdue = (boolean) book.get("isOverdue");
            
            if (isOverdue) {
                overdue++;
            } else if (daysRemaining <= 3) { // Books due within 3 days are considered "due soon"
                dueSoon++;
            }
        }
        
        // Set attributes for the dashboard
        request.setAttribute("availableBooks", availableBooks);
        request.setAttribute("totalIssued", totalIssued);
        request.setAttribute("dueSoon", dueSoon);
        request.setAttribute("overdue", overdue);
        
        // Forward to the dashboard
        request.getRequestDispatcher("studentdashboard.jsp").forward(request, response);
    }
} 