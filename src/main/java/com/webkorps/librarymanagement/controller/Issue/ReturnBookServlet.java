//
//package com.webkorps.librarymanagement.controller.Issue;
//
//import com.webkorps.librarymanagement.dao.IssuedBookDao;
//import com.webkorps.librarymanagement.dao.ReturnBookDao;
//import com.webkorps.librarymanagement.model.BookRequest;
//import com.webkorps.librarymanagement.model.ReturnBook;
//import com.webkorps.librarymanagement.model.Student;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import java.sql.Date;
//import java.util.Map;
//
//@WebServlet("/ReturnBook")
//public class ReturnBookServlet extends HttpServlet {
//
//    private ReturnBookDao returnBookDao;
//    private IssuedBookDao issuedBookDao;
//
//    public void init() {
//        returnBookDao = new ReturnBookDao();
//        issuedBookDao = new IssuedBookDao();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession(false);
//        Student student = (Student) session.getAttribute("student");
//
//        if (student == null) {
//            response.sendRedirect("studentlogin.jsp");
//            return;
//        }
//
//        String issueIdStr = request.getParameter("issueId");
//        if (issueIdStr == null || issueIdStr.isEmpty()) {
//            request.setAttribute("error", "Invalid request. Issue ID is missing.");
//            request.getRequestDispatcher("error.jsp").forward(request, response);
//            return;
//        }
//
//        try {
//            int issueId = Integer.parseInt(issueIdStr);
//
//            // Get issue details
//            Map<String, Object> issueDetails = returnBookDao.getIssueDetails(issueId);
//            if (issueDetails == null) {
//                request.setAttribute("error", "Issue record not found.");
//                request.getRequestDispatcher("error.jsp").forward(request, response);
//                return;
//            }
//
//            // Verify the book belongs to the current student
//            int studentId = (int) issueDetails.get("studentId");
//            if (studentId != student.getMembershipId()) {
//                request.setAttribute("error", "You are not authorized to return this book.");
//                request.getRequestDispatcher("error.jsp").forward(request, response);
//                return;
//            }
//
//            // Create ReturnBook object
//            ReturnBook returnBook = new ReturnBook(
//                    issueId,
//                    (int) issueDetails.get("bookId"),
//                    studentId,
//                    (Date) issueDetails.get("issueDate"),
//                    (Date) issueDetails.get("returnDate"),
//                    new Date(System.currentTimeMillis()),
//                    "RETURNED"
//            );
////         if(returnBook!=null){
////             request.setAttribute("error", returnBook.toString());
////            request.getRequestDispatcher("error.jsp").forward(request, response);
////            return;
////         }
//            // Start transaction (you might need more robust transaction management depending on your DAOs)
//            boolean success = false;
//
//            // Save return record
//            if (returnBookDao.saveReturnBook(returnBook)) {
//                // Update book quantity
//                if (returnBookDao.updateBookQuantity((int) issueDetails.get("bookId"))) {
//                    // Update issued book status
//                    if (issuedBookDao.returnBook(issueId)) {
//                        success = true;
//                    }
//                }
//            }
//
//            if (success) {
//                BookRequest bookrequest = issuedBookDao.getFirstPendingRequest((int) issueDetails.get("bookId"));
//                if (bookrequest != null) {
//
//                    boolean issued = issuedBookDao.autoIssueBookToRequester(bookrequest);
//
//                    // Mark the request as fulfilled
//                    if (issued) {
//                        issuedBookDao.markRequestAsFulfilled(bookrequest.getRequestId());
//                    }
//                }
//
//            }
//
//            if (success) {
//                request.setAttribute("success", "Book returned successfully!");
//                response.sendRedirect("success.jsp");
//                return; // Important: Exit the method after successful redirect
//            } else {
//                request.setAttribute("error", "Failed to return book. Please try again.");
//                request.getRequestDispatcher("error.jsp").forward(request, response);
//                return; // Important: Exit the method after forwarding error
//            }
//            // The following line should not be reached in a successful or handled failure scenario
//            // response.sendRedirect("error.jsp");
//
//        } catch (NumberFormatException e) {
//            request.setAttribute("error", "Invalid issue ID format.");
//            request.getRequestDispatcher("error.jsp").forward(request, response);
//        } catch (Exception e) {
//            request.setAttribute("error", "An error occurred while processing your request: " + e.getMessage());
//            request.getRequestDispatcher("error.jsp").forward(request, response);
//        }
//    }
//}
package com.webkorps.librarymanagement.controller.Issue;

import com.webkorps.librarymanagement.dao.IssuedBookDao;
import com.webkorps.librarymanagement.dao.ReturnBookDao;
import com.webkorps.librarymanagement.model.BookRequest;
import com.webkorps.librarymanagement.model.ReturnBook;
import com.webkorps.librarymanagement.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Map;

@WebServlet("/ReturnBook")
public class ReturnBookServlet extends HttpServlet {

    private ReturnBookDao returnBookDao;
    private IssuedBookDao issuedBookDao;

    public void init() {
        returnBookDao = new ReturnBookDao();
        issuedBookDao = new IssuedBookDao();
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

        String issueIdStr = request.getParameter("issueId");
        if (issueIdStr == null || issueIdStr.isEmpty()) {
            request.setAttribute("error", "Invalid request. Issue ID is missing.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int issueId = Integer.parseInt(issueIdStr);
            Map<String, Object> issueDetails = returnBookDao.getIssueDetails(issueId);
            
            if (issueDetails == null) {
                request.setAttribute("error", "Issue record not found.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            // Verify student authorization
            int studentId = (int) issueDetails.get("studentId");
            if (studentId != student.getMembershipId()) {
                request.setAttribute("error", "You are not authorized to return this book.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            // Create ReturnBook object
            ReturnBook returnBook = new ReturnBook(
                issueId,
                (int) issueDetails.get("bookId"),
                studentId,
                (Date) issueDetails.get("issueDate"),
                (Date) issueDetails.get("returnDate"),
                new Date(System.currentTimeMillis()),
                "RETURNED"
            );

            // Start transaction
            boolean transactionSuccess = false;
            
            try {
                // 1. Save return record
                boolean saveSuccess = returnBookDao.saveReturnBook(returnBook);
                
                // 2. Update book quantity
                boolean updateSuccess = returnBookDao.updateBookQuantity((int) issueDetails.get("bookId"));
                
                // 3. Update issued book status
                boolean returnSuccess = issuedBookDao.returnBook(issueId);
                
                transactionSuccess = saveSuccess && updateSuccess && returnSuccess;
                
                if (transactionSuccess) {
                    // Check for pending requests AFTER successful return
                    BookRequest pendingRequest = issuedBookDao.getFirstPendingRequest((int) issueDetails.get("bookId"));
                    
                    if (pendingRequest != null) {
                        // Attempt to auto-issue the book
                        boolean issued = issuedBookDao.autoIssueBookToRequester(pendingRequest);
                        
                        if (issued) {
                            // Mark request as fulfilled
                            issuedBookDao.markRequestAsFulfilled(pendingRequest.getRequestId());
                        }
                    }
                    
                    request.setAttribute("success", "Book returned successfully!");
                    response.sendRedirect("success.jsp");
                    return;
                }
            } catch (Exception e) {
                // Transaction failed
                request.setAttribute("error", "Transaction failed: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            // If we get here, the transaction failed
            request.setAttribute("error", "Failed to complete return transaction.");
            request.getRequestDispatcher("error.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid issue ID format: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "System error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}