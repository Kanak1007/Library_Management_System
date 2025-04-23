package com.webkorps.librarymanagement.controller.Books;

import com.webkorps.librarymanagement.dao.BookDao;
import com.webkorps.librarymanagement.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
//import javax.servlet.http.HttpSession;

@WebServlet("/GetAvailableBooks")
public class GetAvailableBooks extends HttpServlet {
    
    private BookDao bookDao;
    
    @Override
    public void init() throws ServletException {
        bookDao = new BookDao();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentid") == null) {
            response.sendRedirect("studentlogin.jsp");
            return;
        }
        
        List<Book> availableBooks = bookDao.getAvailableBooks();
        request.setAttribute("books", availableBooks);
        request.getRequestDispatcher("/issuebook.jsp").forward(request, response);
    }
} 