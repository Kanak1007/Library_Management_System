package com.webkorps.librarymanagement.controller.Issue;

import com.webkorps.librarymanagement.dao.IssuedBookDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GetAllIssuedBooksServlet", urlPatterns = {"/GetAllIssuedBooks"})
public class GetAllIssuedBooksServlet extends HttpServlet {
    private IssuedBookDao issuedBookDao;

    @Override
    public void init() throws ServletException {
        this.issuedBookDao = new IssuedBookDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get all issued books with details
            List<Map<String, Object>> issuedBooks = issuedBookDao.getAllIssuedBooksWithDetails();
            
            // Set the books in request attribute
            request.setAttribute("issuedBooks", issuedBooks);
            
            // Forward to the view page
            request.getRequestDispatcher("viewissuedbooks.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while fetching issued books.");
            request.getRequestDispatcher("admindashboard.jsp").forward(request, response);
        }
    }
} 