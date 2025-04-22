package com.webkorps.librarymanagement.controller;

import com.webkorps.librarymanagement.dao.IssuedBookDao;
import com.webkorps.librarymanagement.model.IssuedBook;
import com.webkorps.librarymanagement.model.Student;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ViewIssuedBooks")
public class ViewIssuedBooks extends HttpServlet {
    private IssuedBookDao issuedBookDao;
    
    @Override
    public void init() {
        issuedBookDao = new IssuedBookDao();
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
        
        try {
            List<IssuedBook> issuedBooks = issuedBookDao.getIssuedBooksByStudentId(student.getStudentId());
            List<Map<String, Object>> booksWithStatus = new ArrayList<>();
            
            for (IssuedBook book : issuedBooks) {
                Map<String, Object> bookWithStatus = new HashMap<>();
                bookWithStatus.put("book", book);
                
                // Calculate days remaining and overdue status
                Date returnDate = book.getReturnDate();
                Date currentDate = new Date();
                long diffInMillies = returnDate.getTime() - currentDate.getTime();
                long daysRemaining = diffInMillies / (1000 * 60 * 60 * 24);
                
                bookWithStatus.put("daysRemaining", daysRemaining);
                bookWithStatus.put("isOverdue", daysRemaining < 0);
                
                booksWithStatus.add(bookWithStatus);
            }
            
            request.setAttribute("issuedBooks", booksWithStatus);
            request.getRequestDispatcher("issuedbooks.jsp").forward(request, response);
            
        } catch (SQLException ex) {
            Logger.getLogger(ViewIssuedBooks.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Error retrieving issued books");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}