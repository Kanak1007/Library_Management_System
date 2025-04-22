<%@ page import="java.util.List" %>
<%@ page import="com.webkorps.librarymanagement.model.Book" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<Book> books = (List<Book>) request.getAttribute("books");
    if (books == null) {
        response.sendRedirect("GetAllBooks");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Books - Student</title>
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

            .search-container {
                margin: 30px auto;
                max-width: 800px;
            }

            .search-container input {
                border-radius: 25px;
                padding: 12px 20px;
                border: none;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                width: 100%;
                background: rgba(255, 255, 255, 0.9);
            }

            .book-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
                gap: 30px;
                padding: 20px 0;
            }

            .book-card {
                background: white;
                border-radius: 15px;
                overflow: hidden;
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease, opacity 0.3s ease;
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

            .book-card.hidden {
                display: none;
            }

            .book-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15);
            }

            .book-image {
                width: 100%;
                height: 300px;
                overflow: hidden;
                border-bottom: 2px solid var(--light-lavender);
            }

            .book-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                transition: transform 0.3s ease;
            }

            .book-card:hover .book-image img {
                transform: scale(1.05);
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

            .availability {
                display: inline-block;
                padding: 5px 10px;
                border-radius: 15px;
                font-size: 0.9rem;
                margin-top: 10px;
            }

            .available {
                background: #e8f5e9;
                color: #2e7d32;
            }

            .unavailable {
                background: #ffebee;
                color: #c62828;
            }

            .btn-issue {
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

            .btn-issue:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0,0,0,0.2);
                color: white;
            }

            .btn-issue.disabled {
                background: #e0e0e0;
                color: #9e9e9e;
                cursor: not-allowed;
                pointer-events: none;
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
            <h2>Library Books Collection</h2>
            
            <!-- Search Form -->
            <div class="search-container">
                <input type="text" id="searchInput" class="form-control" 
                       placeholder="Search by book name, author, or edition"
                       oninput="filterBooks(this.value)">
            </div>

            <!-- Book Display Section -->
            <div class="book-grid">
                <% for(Book book : books) { %>
                    <div class="book-card" 
                         data-title="<%= book.getBookName().toLowerCase() %>"
                         data-author="<%= book.getBookAuthor().toLowerCase() %>"
                         data-edition="<%= book.getBookEdition().toLowerCase() %>">
                        <div class="book-image">
                            <img src="${pageContext.request.contextPath}/<%= book.getImagePath() %>" 
                                 alt="<%= book.getBookName() %>" 
                                 onerror="this.src='${pageContext.request.contextPath}/images/books/library-hero.jpg'">
                        </div>
                        <div class="book-details">
                            <div class="book-title"><%= book.getBookName() %></div>
                            <div class="book-info">
                                <i class="fas fa-user"></i> <%= book.getBookAuthor() %>
                            </div>
                            <div class="book-info">
                                <i class="fas fa-layer-group"></i> Edition: <%= book.getBookEdition() %>
                            </div>
                            <div class="book-info">
                                <i class="fas fa-book"></i> Quantity: <%= book.getBookQuantity() %>
                            </div>
                          
                            
                        </div>
                    </div>
                <% } %>
            </div>
        </div>

        <a href="studentdashboard.jsp" class="back-btn" title="Back to Dashboard">
            <i class="fas fa-arrow-left"></i>
        </a>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        
        <script>
            function filterBooks(searchTerm) {
                searchTerm = searchTerm.toLowerCase().trim();
                const bookCards = document.querySelectorAll('.book-card');
                
                bookCards.forEach(card => {
                    const title = card.dataset.title;
                    const author = card.dataset.author;
                    const edition = card.dataset.edition;
                    
                    if (searchTerm === '' || 
                        title.includes(searchTerm) || 
                        author.includes(searchTerm) || 
                        edition.includes(searchTerm)) {
                        card.classList.remove('hidden');
                    } else {
                        card.classList.add('hidden');
                    }
                });
            }
        </script>
    </body>
</html> 