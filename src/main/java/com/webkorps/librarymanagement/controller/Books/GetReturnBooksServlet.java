package com.webkorps.librarymanagement.controller.Books;

import com.webkorps.librarymanagement.dao.IssuedBookDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/GetReturnBooks")
public class GetReturnBooksServlet extends HttpServlet {
    private IssuedBookDao issuedBookDao;

    public void init() throws ServletException {
        issuedBookDao = new IssuedBookDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get student ID from session
            Integer studentId = (Integer) request.getSession().getAttribute("studentid");
            
            if (studentId == null) {
                request.setAttribute("error", "Student session not found. Please login again.");
                request.getRequestDispatcher("studentlogin.jsp").forward(request, response);
                return;
            }

            // Get returned books for the student
            List<Map<String, Object>> returnedBooks = issuedBookDao.getReturnedBooksWithDetails(studentId);
            
            // Set the returned books as a request attribute
            request.setAttribute("returnedBooks", returnedBooks);
            
            // Forward to the returnbooks.jsp
            request.getRequestDispatcher("returnbooks.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("GetReturnBooksServlet: Error - " + e.getMessage());
            request.setAttribute("error", "An error occurred while fetching returned books.");
            request.getRequestDispatcher("studentdashboard.jsp").forward(request, response);
        }
    }
} 