
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
        session.setAttribute("error", "Invalid request. Issue ID is missing.");
        response.sendRedirect("issuedbooks.jsp");
        return;
    }

    try {
        int issueId = Integer.parseInt(issueIdStr);
        Map<String, Object> issueDetails = returnBookDao.getIssueDetails(issueId);

        if (issueDetails == null) {
            session.setAttribute("error", "Issue record not found.");
            response.sendRedirect("issuedbooks.jsp");
            return;
        }

        int studentId = (int) issueDetails.get("studentId");
        if (studentId != student.getMembershipId()) {
            session.setAttribute("error", "You are not authorized to return this book.");
            response.sendRedirect("issuedbooks.jsp");
            return;
        }

        ReturnBook returnBook = new ReturnBook(
            issueId,
            (int) issueDetails.get("bookId"),
            studentId,
            (Date) issueDetails.get("issueDate"),
            (Date) issueDetails.get("returnDate"),
            new Date(System.currentTimeMillis()),
            "RETURNED"
        );

        boolean transactionSuccess = false;

        try {
            boolean saveSuccess = returnBookDao.saveReturnBook(returnBook);
            boolean updateSuccess = returnBookDao.updateBookQuantity((int) issueDetails.get("bookId"));
            boolean returnSuccess = issuedBookDao.returnBook(issueId);

            transactionSuccess = saveSuccess && updateSuccess && returnSuccess;

            if (transactionSuccess) {
                BookRequest pendingRequest = issuedBookDao.getFirstPendingRequest((int) issueDetails.get("bookId"));
                if (pendingRequest != null) {
                    boolean issued = issuedBookDao.autoIssueBookToRequester(pendingRequest);
                    if (issued) {
                        issuedBookDao.markRequestAsFulfilled(pendingRequest.getRequestId());
                    }
                }

                session.setAttribute("success", "Book returned successfully!");
                response.sendRedirect("issuedbooks.jsp");
                return;
            }
        } catch (Exception e) {
            session.setAttribute("error", "Transaction failed: " + e.getMessage());
            response.sendRedirect("issuedbooks.jsp");
            return;
        }

        session.setAttribute("error", "Failed to complete return transaction.");
        response.sendRedirect("issuedbooks.jsp");

    } catch (NumberFormatException e) {
        session.setAttribute("error", "Invalid issue ID format: " + e.getMessage());
        response.sendRedirect("issuedbooks.jsp");
    } catch (Exception e) {
        session.setAttribute("error", "System error: " + e.getMessage());
        response.sendRedirect("issuedbooks.jsp");
    }
}

}