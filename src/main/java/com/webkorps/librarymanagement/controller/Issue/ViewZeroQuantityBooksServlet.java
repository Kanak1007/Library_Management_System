package com.webkorps.librarymanagement.controller.Issue;

import com.webkorps.librarymanagement.dao.BookDao;
import com.webkorps.librarymanagement.model.Book;
import com.webkorps.librarymanagement.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ViewZeroQuantityBooks")
public class ViewZeroQuantityBooksServlet extends HttpServlet {
    private BookDao bookDao;
    
    @Override
    public void init() throws ServletException {
        bookDao = new BookDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            response.sendRedirect("studentlogin.jsp");
            return;
        }
        
        // Get books with zero quantity
        List<Book> zeroQuantityBooks = bookDao.getZeroQuantityBooks();
        request.setAttribute("zeroQuantityBooks", zeroQuantityBooks);
        
        // Forward to the request books page
        request.getRequestDispatcher("requestbooks.jsp").forward(request, response);
    }
} 