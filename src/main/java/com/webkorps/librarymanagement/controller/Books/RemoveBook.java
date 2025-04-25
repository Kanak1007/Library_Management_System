/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.webkorps.librarymanagement.controller.Books;

import com.webkorps.librarymanagement.model.Book;
import com.webkorps.librarymanagement.service.BookService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author kanak
 */
@WebServlet(name = "RemoveBook", urlPatterns = {"/RemoveBook"})
public class RemoveBook extends HttpServlet {

    private BookService bookservice;
    @Override
    public void init()  {
       this. bookservice=new   BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
         HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect("adminlogin.jsp");
            return;
        }
     
      String name=request.getParameter("bookId");
        int bookId=Integer.parseInt(name); 
        Boolean isDeleted=bookservice.DeleteBook(bookId);
                if(isDeleted){
                    request.setAttribute("success", "Book Deleted Successfully!!!");
         request.getRequestDispatcher("viewbooks.jsp").forward(request, response);
                    
                }
                    else{
             request.setAttribute("error", "something went wrongI!!!");
         request.getRequestDispatcher("viewbooks.jsp").forward(request, response);
       }
    }
   
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect("adminlogin.jsp");
            return;
        }
      String name=request.getParameter("bookId");
        int bookId=Integer.parseInt(name);
        
      System.out.println("Here is my bookid:-   "+bookId);
      
        Boolean isDeleted=bookservice.DeleteBook(bookId);
      

        if(isDeleted){
//           response.sendRedirect("success.jsp");
         request.setAttribute("success", "Book Deleted Successfully!!!");
         request.getRequestDispatcher("removebook.jsp").forward(request, response);
       }
       else{
             request.setAttribute("error", "Couldn't  Delete book Invalid Book idI!!!");
         request.getRequestDispatcher("removebook.jsp").forward(request, response);
       }
    }
}
