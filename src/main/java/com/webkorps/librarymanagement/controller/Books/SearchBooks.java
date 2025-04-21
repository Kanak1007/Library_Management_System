package com.webkorps.librarymanagement.controller.Books;

import com.webkorps.librarymanagement.model.Book;
import com.webkorps.librarymanagement.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchBooks", urlPatterns = {"/SearchBooks"})
public class SearchBooks extends HttpServlet {
    private BookService bookService;

    @Override
    public void init() throws ServletException {
        this.bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            List<Book> books = bookService.searchBooks(searchTerm);
            request.setAttribute("books", books);
            request.setAttribute("searchTerm", searchTerm);
        } else {
            // If no search term, show all books
            List<Book> books = bookService.getAllBooks();
            request.setAttribute("books", books);
        }
        
        request.getRequestDispatcher("viewbooks.jsp").forward(request, response);
    }
} 