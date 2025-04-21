<%@ page import="java.util.List" %>
<%@ page import="com.webkorps.librarymanagement.model.Book" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<Book> books = (List<Book>) request.getAttribute("books");
    if (books == null) {
        response.sendRedirect("GetAllBooksForAdmin");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Books</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(to right, #fce4ec, #e1bee7);
                font-family: 'Segoe UI', sans-serif;
                min-height: 100vh;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 1200px;
                padding: 20px;
            }

            h2 {
                color: #7b1fa2;
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
            }

            .book-card.hidden {
                display: none;
            }

            .book-card:hover {
                transform: translateY(-5px);
            }

            .book-image {
                width: 100%;
                height: 300px;
                overflow: hidden;
                border-bottom: 2px solid #f0f0f0;
            }

            .book-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            .book-details {
                padding: 20px;
            }

            .book-title {
                font-size: 1.3rem;
                font-weight: 600;
                color: #333;
                margin-bottom: 15px;
            }

            .book-info {
                color: #666;
                font-size: 1rem;
                margin-bottom: 8px;
            }

            .book-info i {
                width: 25px;
                color: #7b1fa2;
                margin-right: 10px;
            }

            .book-actions {
                display: flex;
                gap: 15px;
                margin-top: 20px;
            }

            .btn-action {
                flex: 1;
                padding: 10px;
                border-radius: 25px;
                font-size: 0.95rem;
                text-align: center;
                text-decoration: none;
                transition: 0.3s ease;
            }

            .btn-update {
                background: #7b1fa2;
                color: white;
            }

            .btn-delete {
                background: #ef5350;
                color: white;
            }

            .btn-action:hover {
                opacity: 0.9;
                color: white;
                transform: translateY(-2px);
            }

            .add-book-btn {
                position: fixed;
                bottom: 40px;
                right: 40px;
                width: 60px;
                height: 60px;
                border-radius: 50%;
                background: #7b1fa2;
                color: white;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 24px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
                text-decoration: none;
                transition: transform 0.3s ease;
            }

            .add-book-btn:hover {
                transform: scale(1.1);
                color: white;
                background: #6a1b9a;
            }
        </style>
    </head>
    <body>
        <%@include file="navbar.jsp" %>
        
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
                            <div class="book-actions">
                                <a href="FindBookToUpdate?bookId=<%= book.getBookId() %>" class="btn-action btn-update">Update</a>
                                <a href="RemoveBook?bookId=<%= book.getBookId() %>" class="btn-action btn-delete" 
                                   onclick="return confirm('Are you sure you want to delete this book?')">Delete</a>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        </div>

        <a href="addbook.jsp" class="add-book-btn" title="Add New Book">
            <i class="fas fa-plus"></i>
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
