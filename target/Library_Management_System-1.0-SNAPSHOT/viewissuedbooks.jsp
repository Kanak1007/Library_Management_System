<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
    List<Map<String, Object>> issuedBooks = (List<Map<String, Object>>) request.getAttribute("issuedBooks");
    if (issuedBooks == null) {
        response.sendRedirect("GetAllIssuedBooks");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Issued Books</title>
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
        <%@include file="navbar.jsp" %>
        
        <div class="container">
            <h2>Issued Books</h2>
            
            <% if (issuedBooks.isEmpty()) { %>
                <div class="text-center">
                    <i class="fas fa-book-open fa-4x mb-3" style="color: var(--primary-color);"></i>
                    <h3>No Books Issued</h3>
                    <p>There are currently no books issued to students.</p>
                </div>
            <% } else { %>
                <div class="book-grid">
                    <% for (Map<String, Object> book : issuedBooks) { 
                        long daysRemaining = (long) book.get("daysRemaining");
                        boolean isOverdue = (boolean) book.get("isOverdue");
                        String statusClass = isOverdue ? "overdue" : (daysRemaining <= 3 ? "due-soon" : "normal");
                        String statusText = isOverdue ? "Overdue" : (daysRemaining <= 3 ? "Due Soon" : "On Time");
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
                                    <i class="fas fa-user-graduate"></i> Student ID: <%= book.get("studentId") %>
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
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } %>
        </div>

        <a href="admindashboard.jsp" class="back-btn" title="Back to Dashboard">
            <i class="fas fa-arrow-left"></i>
        </a>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html> 