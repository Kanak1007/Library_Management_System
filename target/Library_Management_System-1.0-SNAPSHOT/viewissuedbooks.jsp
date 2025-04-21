<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.webkorps.librarymanagement.model.IssuedBook"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Issued Books</title>
        <link rel="stylesheet" href="css/style.css">
        <style>
            .issued-books {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
                gap: 20px;
                padding: 20px;
            }
            .issued-book-card {
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
            .no-books {
                text-align: center;
                padding: 20px;
                color: #666;
            }
        </style>
    </head>
    <body>
        <%@include file="navbar.jsp" %>
        
        <div class="container">
            <h1>My Issued Books</h1>
            
            <div class="issued-books">
                <% List<IssuedBook> issuedBooks = (List<IssuedBook>) request.getAttribute("issuedBooks");
                   if (issuedBooks != null && !issuedBooks.isEmpty()) {
                       for (IssuedBook issuedBook : issuedBooks) { %>
                        <div class="issued-book-card">
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
                                <form action="ReturnBook" method="POST">
                                    <input type="hidden" name="issueId" value="<%= issuedBook.getIssueId() %>">
                                    <input type="hidden" name="bookId" value="<%= issuedBook.getBookId() %>">
                                    <button type="submit" class="return-btn">Return Book</button>
                                </form>
                            </div>
                        </div>
                <%   }
                   } else { %>
                    <div class="no-books">
                        <p>You haven't issued any books yet.</p>
                        <a href="IssueBook" class="btn">Issue a Book</a>
                    </div>
                <% } %>
            </div>
        </div>
    </body>
</html> 