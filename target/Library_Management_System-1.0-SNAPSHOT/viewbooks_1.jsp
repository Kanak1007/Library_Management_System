<%@ page import="java.util.List" %>
<%@ page import="com.webkorps.librarymanagement.model.Book" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<Book> books = (List<Book>) request.getAttribute("books");
    if (books == null) {
        response.sendRedirect(request.getContextPath() + "/GetAllBooks");
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
            /* Your existing styles */
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/admin/navbar.jsp" />
        
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
                            <img src="<%= request.getContextPath() %>/<%= book.getImagePath() %>" 
                                 alt="<%= book.getBookName() %>" 
                                 onerror="this.src='<%= request.getContextPath() %>/images/library-hero.jpg'">
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
                                <a href="<%= request.getContextPath() %>/FindBookToUpdate?bookId=<%= book.getBookId() %>" 
                                   class="btn-action btn-update">Update</a>
                                <a href="<%= request.getContextPath() %>/RemoveBook?bookId=<%= book.getBookId() %>" 
                                   class="btn-action btn-delete" 
                                   onclick="return confirm('Are you sure you want to delete this book?')">Delete</a>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        </div>

        <a href="<%= request.getContextPath() %>/WEB-INF/views/admin/addbook.jsp" class="add-book-btn" title="Add New Book">
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