/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

/**
 *
 * @author kanak
 */
@WebServlet(name = "GetAllBooks", urlPatterns = {"/GetAllBooks"})
public class GetAllBooks extends HttpServlet {
    private BookService bookservice;
    
    @Override
    public void init() throws ServletException {
        this.bookservice = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> booklist = bookservice.getAllBooks();
        request.setAttribute("books", booklist);
        request.getRequestDispatcher("viewbooksforstudent.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
