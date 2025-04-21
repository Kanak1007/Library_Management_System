package com.webkorps.librarymanagement.controller.Books;

import com.webkorps.librarymanagement.model.IssuedBook;
import com.webkorps.librarymanagement.model.User;
import com.webkorps.librarymanagement.service.IssuedBookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewIssuedBooks", urlPatterns = {"/ViewIssuedBooks"})
public class ViewIssuedBooks extends HttpServlet {
    private IssuedBookService issuedBookService;
    
    @Override
    public void init() throws ServletException {
        this.issuedBookService = new IssuedBookService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
            return;
        }
        
        // Get issued books for the current user
        List<IssuedBook> issuedBooks = issuedBookService.getIssuedBooksByUser(user.getUserId());
        request.setAttribute("issuedBooks", issuedBooks);
        request.getRequestDispatcher("viewissuedbooks.jsp").forward(request, response);
    }
} 