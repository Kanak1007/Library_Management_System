package com.webkorps.librarymanagement.controller.Admin;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.webkorps.librarymanagement.dao.BookDao;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author kanak
 */
@WebServlet(urlPatterns = {"/AdminDashboardServlet"})
public class AdminDashboardServlet extends HttpServlet {
    private BookDao bookdao;

    @Override
    public void init() throws ServletException {
      this.bookdao= new BookDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        int totalBooks =bookdao.getTotalBooks();     // total book quantity
        int issuedBooks = bookdao.getIssuedBooks();   // total issued books

        request.setAttribute("totalBooks", totalBooks);
        request.setAttribute("issuedBooks", issuedBooks);

        request.getRequestDispatcher("admindashboard.jsp").forward(request, response);
    }
}

