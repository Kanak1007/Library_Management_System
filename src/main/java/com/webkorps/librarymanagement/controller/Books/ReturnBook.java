package com.webkorps.librarymanagement.controller.Books;

import com.webkorps.librarymanagement.service.IssuedBookService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReturnBook")
public class ReturnBook extends HttpServlet {
    private IssuedBookService issuedBookService;

    @Override
    public void init() {
        issuedBookService = new IssuedBookService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int issueId = Integer.parseInt(request.getParameter("issueId"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));

            boolean returned = issuedBookService.returnBook(issueId, bookId);
            
            if (returned) {
                request.getSession().setAttribute("success", "Book returned successfully!");
            } else {
                request.getSession().setAttribute("error", "Failed to return book. Please try again.");
            }
            
            // Check if user still has overdue books
            int userId = (int) request.getSession().getAttribute("userId");
            if (issuedBookService.hasOverdueBooks(userId)) {
                response.sendRedirect("overduebooks.jsp");
            } else {
                response.sendRedirect("viewissuedbooks.jsp");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Invalid book or issue ID");
            response.sendRedirect("overduebooks.jsp");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "An error occurred while returning the book");
            response.sendRedirect("overduebooks.jsp");
        }
    }
} 