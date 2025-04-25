
package com.webkorps.librarymanagement.controller.Issue;

import com.webkorps.librarymanagement.dao.IssuedBookDao;
import com.webkorps.librarymanagement.model.BookRequest;
import com.webkorps.librarymanagement.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/RequestBook")
public class RequestBookServlet extends HttpServlet {

    private IssuedBookDao issuebookdao;

    @Override
    public void init() throws ServletException {
        issuebookdao = new IssuedBookDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentid") == null) {
            response.sendRedirect("studentlogin.jsp");
            return;
        }
        // Set content type to JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
//        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            System.out.println("RequestBookServlet: No student found in session");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            out.print("{\"status\":\"error\",\"message\":\"Please login to request books.\"}");
            return;
        }

        try {
            String bookIdStr = request.getParameter("bookId");
            System.out.println("RequestBookServlet: Received request for bookId: " + bookIdStr);

            if (bookIdStr == null || bookIdStr.isEmpty()) {
                System.out.println("RequestBookServlet: Missing bookId parameter");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                out.print("{\"status\":\"error\",\"message\":\"Book ID is required.\"}");
                return;
            }

            int bookId = Integer.parseInt(bookIdStr);
            System.out.println("RequestBookServlet: Processing request for student: " + student.getMembershipId() + ", book: " + bookId);

            if (issuebookdao.isBookAlreadyIssuedToStudent(bookId, student.getMembershipId())) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.print("{\"status\":\"error\",\"message\":\"You already have this book issued.\"}");
                return;
            }

            if (issuebookdao.hasAlreadyRequested(bookId, student.getMembershipId())) {
                System.out.println("RequestBookServlet: Book already requested by student");
                response.setStatus(HttpServletResponse.SC_CONFLICT); // 409
                out.print("{\"status\":\"error\",\"message\":\"You have already requested this book.\"}");
                return;
            }

            BookRequest bookRequest = new BookRequest();
            bookRequest.setBookId(bookId);
            bookRequest.setStudentId(student.getMembershipId());
            bookRequest.setRequestDate(Date.valueOf(LocalDate.now()));
            bookRequest.setStatus("Pending");

            System.out.println("RequestBookServlet: Created book request object: " + bookRequest.toString());

            boolean success = issuebookdao.saveRequest(bookRequest);
            
            if (success) {
                System.out.println("RequestBookServlet: Successfully saved book request");
                response.setStatus(HttpServletResponse.SC_OK); // 200
                out.print("{\"status\":\"success\",\"message\":\"Book request submitted successfully!\"}");
            } else {
                System.out.println("RequestBookServlet: Failed to save book request");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
                out.print("{\"status\":\"error\",\"message\":\"Failed to submit book request. Please try again.\"}");
            }

        } catch (NumberFormatException e) {
            System.out.println("RequestBookServlet: Invalid book ID format - " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            out.print("{\"status\":\"error\",\"message\":\"Invalid book ID.\"}");
        } catch (Exception e) {
            System.out.println("RequestBookServlet: Unexpected error - " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            out.print("{\"status\":\"error\",\"message\":\"An error occurred while processing your request: " + e.getMessage() + "\"}");
        }
    }
}