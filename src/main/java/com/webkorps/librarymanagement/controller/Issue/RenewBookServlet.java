/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.webkorps.librarymanagement.controller.Issue;

import com.webkorps.librarymanagement.dao.IssuedBookDao;
import com.webkorps.librarymanagement.model.BookRequest;
import com.webkorps.librarymanagement.model.Student;
import jakarta.servlet.ServletConfig;
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
@WebServlet(name = "RenewBookServlet", urlPatterns = {"/RenewBookServlet"})
public class RenewBookServlet extends HttpServlet {
  private IssuedBookDao issuebookdao;

    @Override
    public void init() throws ServletException {
      this.issuebookdao=new IssuedBookDao();
    }
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            doPost(request,response);
            
    }

 

 



protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentid") == null) {
            response.sendRedirect("studentlogin.jsp");
            return;
        }

        int issueId = Integer.parseInt(request.getParameter("issueId"));
        String issueDate = request.getParameter("issueDate");
        String returnDate = request.getParameter("returnDate");

        // Get bookId and current student info
        int bookId = issuebookdao.getBookIdByIssueId(issueId);
        Student student = (Student) request.getSession().getAttribute("student");

        // Find the first pending request for the book
        BookRequest firstPending = issuebookdao.getFirstPendingRequest(bookId);

        // Check: if there's a pending request and it's NOT from the current student
 if (firstPending != null && firstPending.getStudentId() != student.getMembershipId())

       {        
            request.getSession().setAttribute("error", "Cannot renew. A request for this book is already pending by another student.");
             response.sendRedirect("issuedbooks.jsp");
            return;
        }

        // Proceed to renew
        boolean success = issuebookdao.renewBook(issueId, issueDate, returnDate);
        if (success) {
            request.getSession().setAttribute("success", "Book renewed successfully!");
        } else {
            request.getSession().setAttribute("error", "Failed to renew book. Please try again.");
        }
     response.sendRedirect("issuedbooks.jsp");

    } catch (Exception e) {
        e.printStackTrace();
        request.getSession().setAttribute("error", "An error occurred while renewing the book.");
        response.sendRedirect("issuedbooks.jsp");
    }
}



}
