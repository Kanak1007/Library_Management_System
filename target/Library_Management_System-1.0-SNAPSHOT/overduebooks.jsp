<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.webkorps.librarymanagement.model.IssuedBook"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Overdue Books</title>
        <link rel="stylesheet" href="css/style.css">
        <style>
            .overdue-books {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
                gap: 20px;
                padding: 20px;
            }
            .overdue-book-card {
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 15px;
                background-color: #fff;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .book-header {
                display: flex;
                align-items: center;
                margin-bottom: 15px;
            }
            .book-image {
                width: 100px;
                height: 150px;
                object-fit: cover;
                border-radius: 4px;
                margin-right: 15px;
            }
            .book-details {
                flex: 1;
            }
            .book-title {
                margin: 0;
                font-size: 1.2em;
            }
            .book-info {
                margin: 5px 0;
                color: #666;
            }
            .issue-info {
                margin-top: 15px;
                padding-top: 15px;
                border-top: 1px solid #eee;
            }
            .return-btn {
                background-color: #f44336;
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
                margin-top: 10px;
            }
            .return-btn:hover {
                background-color: #d32f2f;
            }
            .overdue-warning {
                background-color: #fff3cd;
                color: #856404;
                padding: 15px;
                border-radius: 4px;
                margin-bottom: 20px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <%@include file="navbar.jsp" %>
        
        <div class="container">
            <h1>Overdue Books</h1>
            
            <div class="overdue-warning">
                <h3>You have overdue books!</h3>
                <p>Please return these books before issuing new ones.</p>
            </div>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="message error">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <div class="overdue-books">
                <% List<IssuedBook> overdueBooks = (List<IssuedBook>) request.getAttribute("overdueBooks");
                   if (overdueBooks != null && !overdueBooks.isEmpty()) {
                       for (IssuedBook issuedBook : overdueBooks) { %>
                        <div class="overdue-book-card">
                            <div class="book-header">
                                <img src="<%= issuedBook.getBook().getImagePath() %>" 
                                     alt="<%= issuedBook.getBook().getBookName() %>" 
                                     class="book-image"
                                     onerror="this.src='images/books/library-hero.jpg'">
                                <div class="book-details">
                                    <h3 class="book-title"><%= issuedBook.getBook().getBookName() %></h3>
                                    <p class="book-info"><strong>Author:</strong> <%= issuedBook.getBook().getBookAuthor() %></p>
                                    <p class="book-info"><strong>Edition:</strong> <%= issuedBook.getBook().getBookEdition() %></p>
                                </div>
                            </div>
                            <div class="issue-info">
                                <p><strong>Issued Date:</strong> <%= issuedBook.getIssueDate() %></p>
                                <p><strong>Due Date:</strong> <%= issuedBook.getReturnDate() %></p>
                                <p style="color: #dc3545;"><strong>Status: Overdue</strong></p>
                                <form action="ReturnBook" method="POST">
                                    <input type="hidden" name="issueId" value="<%= issuedBook.getIssueId() %>">
                                    <input type="hidden" name="bookId" value="<%= issuedBook.getBookId() %>">
                                    <button type="submit" class="return-btn">Return Book</button>
                                </form>
                            </div>
                        </div>
                <%   }
                   } %>
            </div>
        </div>
    </body>
</html> 