<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.webkorps.librarymanagement.model.Student" %>
<%
    Student student = (Student) session.getAttribute("student");
    if (student == null) {
        response.sendRedirect("studentlogin.jsp");
        return;
    }
    
    List<Map<String, Object>> issuedBooks = (List<Map<String, Object>>) request.getAttribute("issuedBooks");
    if (issuedBooks == null) {
        response.sendRedirect("ViewIssuedBooks");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Issued Books</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
        <style>
            :root {
                --primary-color: #9b6b9d;
                --secondary-color: #e5a9e4;
                --accent-color: #d4a5ff;
                --light-lavender: #f2e6ff;
                --dark-purple: #6a4c93;
                --soft-pink: #ffb6c1;
                --light-pink: #ffe4e8;
                --text-dark: #4a4a4a;
                --text-light: #ffffff;
            }

            body {
                background-color: var(--light-lavender);
                font-family: 'Arial', sans-serif;
                min-height: 100vh;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 1200px;
                padding: 20px;
            }

            h2 {
                color: var(--primary-color);
                text-align: center;
                margin: 30px 0;
                font-weight: 700;
                font-size: 2.5rem;
            }

            .book-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
                gap: 30px;
                padding: 20px 0;
            }

            .book-card {
                background: white;
                border-radius: 15px;
                overflow: hidden;
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease;
                position: relative;
            }

            .book-card::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 5px;
                background: linear-gradient(90deg, var(--soft-pink), var(--accent-color));
            }

            .book-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15);
            }

            .book-details {
                padding: 20px;
            }

            .book-title {
                font-size: 1.3rem;
                font-weight: 600;
                color: var(--text-dark);
                margin-bottom: 15px;
            }

            .book-info {
                color: #666;
                font-size: 1rem;
                margin-bottom: 8px;
            }

            .book-info i {
                width: 25px;
                color: var(--primary-color);
                margin-right: 10px;
            }

            .status {
                display: inline-block;
                padding: 5px 10px;
                border-radius: 15px;
                font-size: 0.9rem;
                margin-top: 10px;
            }

            .due-soon {
                background: #fff3e0;
                color: #e65100;
            }

            .overdue {
                background: #ffebee;
                color: #c62828;
            }

            .normal {
                background: #e8f5e9;
                color: #2e7d32;
            }

            .btn-action {
                background: linear-gradient(135deg, var(--primary-color), var(--dark-purple));
                color: white;
                padding: 10px 20px;
                border-radius: 25px;
                text-decoration: none;
                display: inline-block;
                margin-top: 15px;
                transition: all 0.3s ease;
                border: none;
                width: 100%;
                text-align: center;
            }

            .btn-action:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0,0,0,0.2);
                color: white;
            }

            .btn-renew {
                background: linear-gradient(135deg, #4caf50, #2e7d32);
            }

            .btn-return {
                background: linear-gradient(135deg, #f44336, #c62828);
            }

            .empty-state {
                text-align: center;
                padding: 50px 20px;
                color: var(--text-dark);
            }

            .empty-state i {
                font-size: 4rem;
                color: var(--primary-color);
                margin-bottom: 20px;
            }

            .empty-state h3 {
                margin-bottom: 10px;
                color: var(--primary-color);
            }

            .back-btn {
                position: fixed;
                bottom: 40px;
                right: 40px;
                width: 60px;
                height: 60px;
                border-radius: 50%;
                background: linear-gradient(135deg, var(--soft-pink), var(--accent-color));
                color: white;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 24px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
                text-decoration: none;
                transition: transform 0.3s ease;
            }

            .back-btn:hover {
                transform: scale(1.1);
                color: white;
            }
        </style>
          
 
    </head>
    <body>
        <%@include file="studentnav.jsp" %>
        
        <div class="container">
            <h2>My Issued Books</h2>
            <%
    String successMsg = (String) session.getAttribute("success");
    String errorMsg = (String) session.getAttribute("error");
if (successMsg != null) {
%>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <%= successMsg %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
<%
        session.removeAttribute("success");
    }
    if (errorMsg != null) {
%>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <%= errorMsg %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
<%
        session.removeAttribute("error");
    }
%>

            
            <% if (issuedBooks.isEmpty()) { %>
                <div class="empty-state">
                    <i class="fas fa-book-open"></i>
                    <h3>No Books Issued</h3>
                    <p>You haven't issued any books yet.</p>
                    <a href="GetAvailableBooks" class="btn-action">Browse Available Books</a>
                </div>
            <% } else { %>
                <div class="book-grid">
                    <% for (Map<String, Object> book : issuedBooks) { 
                        long daysRemaining = (long) book.get("daysRemaining");
                        boolean isOverdue = (boolean) book.get("isOverdue");
                        String statusClass = isOverdue ? "overdue" : (daysRemaining <= 3 ? "due-soon" : "normal");
                        String statusText = isOverdue ? "Overdue" : (daysRemaining <= 3 ? "Due Soon" : "On Time");
                        int issueId = (Integer) book.get("issueId");
                    %>
                        <div class="book-card">
                            <div class="book-details">
                                <div class="book-title"><%= book.get("bookName") %></div>
                                <div class="book-info">
                                    <i class="fas fa-user"></i> <%= book.get("author") %>
                                </div>
                                <div class="book-info">
                                    <i class="fas fa-layer-group"></i> Edition: <%= book.get("edition") %>
                                </div>
                                <div class="book-info">
                                    <i class="fas fa-calendar-check"></i> Issued: <%= book.get("issueDate") %>
                                </div>
                                <div class="book-info">
                                    <i class="fas fa-calendar-times"></i> Due: <%= book.get("returnDate") %>
                                </div>
                                <div class="status <%= statusClass %>">
                                    <i class="fas fa-<%= isOverdue ? "exclamation-circle" : (daysRemaining <= 3 ? "clock" : "check") %>"></i>
                                    <%= statusText %> (<%= Math.abs(daysRemaining) %> days)
                                </div>
                                <div class="book-actions">
                                                                  <% if (!isOverdue) { %>
                                                                  <a href="#"  
                                           class="btn-action btn-renew"
                                           onclick="return checkRenewDays(<%= book.get("daysRemaining") %>,<%= issueId %>)">
                                            <i class="fas fa-redo"></i> Renew Book
                                        </a>
                                    <% } %>

                                    <a href="ReturnBook?issueId=<%= book.get("issueId") %>" 
                                       class="btn-action btn-return">
                                        <i class="fas fa-undo"></i> Return Book
                                    </a>
                                </div>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } %>
        </div>
        
        <!-- Renew Book Modal -->
        <div class="modal fade" id="renewModal" tabindex="-1" aria-labelledby="renewModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="renewModalLabel">Renew Book</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form id="renewForm" action="RenewBookServlet" method="POST">
                        <div class="modal-body">
                            <input type="hidden" id="issueId" name="issueId">
                            <div class="mb-3">
                                <label for="renewDate" class="form-label">New Return Date</label>
                                <input type="date" class="form-control" id="renewDate" name="returnDate" required>
                            </div>
                            <div class="mb-3">
                                <label for="issueDate" class="form-label">Issue Date</label>
                                <input type="date" class="form-control" id="issueDate" name="issueDate" readonly>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Renew Book</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script>
            function checkRenewDays(daysRemaining, issueId) {
                if (daysRemaining > 1) {
                    alert("You can only renew a book within 1 day of the return date.");
                    return false;
                } else {
                    // Set today's date as the issue date
                    const today = new Date();
                    const issueDateInput = document.getElementById('issueDate');
                    issueDateInput.value = today.toISOString().split('T')[0];
                    
                    // Set minimum return date (tomorrow)
                    const tomorrow = new Date(today);
                    tomorrow.setDate(tomorrow.getDate() + 1);
                    const renewDateInput = document.getElementById('renewDate');
                    renewDateInput.min = tomorrow.toISOString().split('T')[0];
                    
                    // Set maximum return date (30 days from today)
                    const maxDate = new Date(today);
                    maxDate.setDate(maxDate.getDate() + 30);
                    renewDateInput.max = maxDate.toISOString().split('T')[0];
                    
                    // Set the issue ID
                    document.getElementById('issueId').value = issueId;
                    
                    // Show the modal
                    const renewModal = new bootstrap.Modal(document.getElementById('renewModal'));
                    renewModal.show();
                    return false;
                }
            }
        </script>
        <a href="studentdashboard.jsp" class="back-btn" title="Back to Dashboard">
            <i class="fas fa-arrow-left"></i>
        </a>
  
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
       

       <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
<script>
    // Auto-dismiss alerts after 3 seconds
    setTimeout(function () {
        var alerts = document.querySelectorAll('.alert');
        alerts.forEach(function (alert) {
            var bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            bsAlert.close();
        });
    }, 3000);
</script>
    </body>
</html> 