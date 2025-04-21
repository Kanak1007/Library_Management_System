package com.webkorps.librarymanagement.controller.Books;

import com.webkorps.librarymanagement.model.Book;
import com.webkorps.librarymanagement.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FindBookToUpdate", urlPatterns = {"/FindBookToUpdate"})
public class FindBookToUpdate extends HttpServlet {
    private BookService bookService;
    
    @Override
    public void init() throws ServletException {
        this.bookService = new BookService();
    }
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    doPost(request, response); // Reuse the same logic
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            Book book = bookService.getBookById(bookId);
            
            if (book != null) {
                request.setAttribute("book", book);
                request.getRequestDispatcher("updatebook.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Book not found with ID: " + bookId);
                request.getRequestDispatcher("enter_book_id.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid book ID format");
            request.getRequestDispatcher("enter_book_id.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error finding book: " + e.getMessage());
            request.getRequestDispatcher("enter_book_id.jsp").forward(request, response);
        }
    }
} 