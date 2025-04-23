<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
    List<Map<String, Object>> returnedBooks = (List<Map<String, Object>>) request.getAttribute("returnedBooks");
    if (returnedBooks == null) {
        response.sendRedirect("GetReturnBooks");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Returned Books</title>
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

            .table {
                background: white;
                border-radius: 15px;
                overflow: hidden;
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
            }

            .table thead th {
                background: linear-gradient(135deg, var(--soft-pink), var(--accent-color));
                color: white;
                border: none;
                padding: 15px;
            }

            .table tbody tr {
                transition: all 0.3s ease;
            }

            .table tbody tr:hover {
                background-color: var(--light-pink);
            }

            .table td {
                padding: 15px;
                vertical-align: middle;
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

            .empty-state {
                text-align: center;
                padding: 50px 20px;
                background: white;
                border-radius: 15px;
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
            }

            .empty-state i {
                font-size: 4rem;
                color: var(--primary-color);
                margin-bottom: 20px;
            }

            .empty-state h3 {
                color: var(--text-dark);
                margin-bottom: 10px;
            }

            .empty-state p {
                color: #666;
            }
        </style>
    </head>
    <body>
        <%@include file="studentnav.jsp" %>
        
        <div class="container">
            <h2>Returned Books History</h2>
            
            <% if (returnedBooks.isEmpty()) { %>
                <div class="empty-state">
                    <i class="fas fa-book-open"></i>
                    <h3>No Returned Books</h3>
                    <p>You haven't returned any books yet.</p>
                </div>
            <% } else { %>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Book Name</th>
                                <th>Author</th>
                                <th>Edition</th>
                                <th>Issue Date</th>
                                <th>Due Date</th>
                                <th>Actual Return Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Map<String, Object> book : returnedBooks) { %>
                                <tr>
                                    <td><%= book.get("bookName") %></td>
                                    <td><%= book.get("author") %></td>
                                    <td><%= book.get("edition") %></td>
                                    <td><%= book.get("issueDate") %></td>
                                    <td><%= book.get("returnDate") %></td>
                                    <td><%= book.get("actualReturnDate") %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } %>
        </div>

        <a href="studentdashboard.jsp" class="back-btn" title="Back to Dashboard">
            <i class="fas fa-arrow-left"></i>
        </a>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html> 