<%@ page import="java.util.List" %>
<%@ page import="com.webkorps.librarymanagement.model.Book" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<Book> books = (List<Book>) request.getAttribute("books");
    if (books == null) {
        System.out.println("No books found in request attribute");
        response.sendRedirect("GetAvailableBooks");
        return;
    }
    System.out.println("Number of books found: " + books.size());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Available Books</title>
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
                margin: 2rem auto;
                max-width: 600px;
            }

            .search-container input {
                padding: 1rem;
                border-radius: 30px;
                border: 2px solid #e9ecef;
                transition: border-color 0.2s ease;
            }

            .search-container input:focus {
                border-color: #6f42c1;
                box-shadow: 0 0 0 0.2rem rgba(111, 66, 193, 0.25);
            }

            .book-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
                gap: 2rem;
                padding: 2rem;
                will-change: transform;
            }

            .book-card {
                background: white;
                border-radius: 12px;
                box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                transition: transform 0.2s ease-out;
                overflow: hidden;
                contain: content;
            }

            .book-card:hover {
                transform: translateY(-5px);
            }

            .book-image {
                height: 200px;
                overflow: hidden;
                position: relative;
                background: #f5f5f5;
            }

            .book-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                transform: scale(1);
                transition: transform 0.3s ease;
            }

            .book-card:hover .book-image img {
                transform: scale(1.05);
            }

            .book-details {
                padding: 1.5rem;
            }

            .book-title {
                font-size: 1.25rem;
                font-weight: 600;
                margin-bottom: 1rem;
                color: #333;
            }

            .book-info {
                display: flex;
                align-items: center;
                gap: 0.5rem;
                margin-bottom: 0.5rem;
                color: #666;
            }

            .book-info i {
                width: 25px;
                color: #7b1fa2;
                margin-right: 10px;
            }

            .quantity-badge {
                display: inline-block;
                padding: 0.25rem 0.75rem;
                background: #e8f5e9;
                color: #2e7d32;
                border: 1px solid #a5d6a7;
                border-radius: 20px;
                margin: 0.5rem 0;
                font-size: 0.875rem;
                font-weight: 500;
            }

            .quantity-badge i {
                color: #2e7d32;
                margin-right: 5px;
            }

            .btn-issue {
                width: 100%;
                padding: 0.75rem;
                border: none;
                border-radius: 6px;
                background: linear-gradient(135deg, #6f42c1, #e83e8c);
                color: white;
                font-weight: 500;
                cursor: pointer;
                transition: opacity 0.2s ease;
            }

            .btn-issue:hover {
                opacity: 0.9;
            }

            .btn-issue i {
                margin-right: 8px;
            }

            /* Modal Styles */
            .modal-content {
                border-radius: 15px;
                border: none;
            }

            .modal-header {
                background: #7b1fa2;
                color: white;
                border-radius: 15px 15px 0 0;
                padding: 20px;
            }

            .modal-title {
                font-weight: 600;
            }

            .modal-body {
                padding: 25px;
            }

            .form-label {
                color: #333;
                font-weight: 500;
                margin-bottom: 8px;
            }

            .form-control {
                border-radius: 10px;
                padding: 12px;
                border: 1px solid #ddd;
                margin-bottom: 15px;
            }

            .form-control:focus {
                border-color: #7b1fa2;
                box-shadow: 0 0 0 0.2rem rgba(123, 31, 162, 0.25);
            }

            .modal-footer {
                border-top: none;
                padding: 20px;
            }

            .btn-submit {
                background: #7b1fa2;
                color: white;
                padding: 12px 25px;
                border-radius: 25px;
                border: none;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .btn-submit:hover {
                background: #6a1b9a;
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(123, 31, 162, 0.2);
            }

            .btn-cancel {
                background: #e0e0e0;
                color: #333;
                padding: 12px 25px;
                border-radius: 25px;
                border: none;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .btn-cancel:hover {
                background: #d5d5d5;
            }

            .hidden {
                display: none;
            }

            @media (max-width: 768px) {
                .book-grid {
                    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
                    padding: 1rem;
                }
            }
        </style>
    </head>
    <body>
        <%@include file="studentnav.jsp" %>
        
        <div class="container">
            <h2>Available Books</h2>
            
            <!-- Search Form -->
            <div class="search-container">
                <input type="text" id="searchInput" class="form-control" 
                       placeholder="Search by book name, author, or edition"
                       oninput="filterBooks(this.value)">
            </div>

            <!-- Book Display Section -->
            <div class="book-grid">
                <% if (books.isEmpty()) { %>
                    <div class="alert alert-info w-100" role="alert">
                        No books are currently available.
                    </div>
                <% } else {
                    for(Book book : books) { %>
                        <div class="book-card" 
                             data-title="<%= book.getBookName().toLowerCase() %>"
                             data-author="<%= book.getBookAuthor().toLowerCase() %>"
                             data-edition="<%= book.getBookEdition().toLowerCase() %>">
                            <div class="book-image">
                                <img src="${pageContext.request.contextPath}/<%= book.getImagePath() %>" 
                                     alt="<%= book.getBookName() %>" 
                                     loading="lazy"
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
                                <div class="quantity-badge">
                                    <i class="fas fa-books"></i> Available: <%= book.getBookQuantity() %>
                                </div>
                                <button type="button" class="btn-issue" 
                                        data-bs-toggle="modal" 
                                        data-bs-target="#issueBookModal"
                                        data-book-id="<%= book.getBookId() %>"
                                        data-book-name="<%= book.getBookName() %>">
                                    <i class="fas fa-book-reader"></i>Issue Book
                                </button>
                            </div>
                        </div>
                    <% }
                } %>
            </div>
        </div>

        <!-- Issue Book Modal -->
        <div class="modal fade" id="issueBookModal" tabindex="-1" aria-labelledby="issueBookModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="issueBookModalLabel">Issue Book</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="IssueBook" method="POST" id="issueBookForm">
                        <div class="modal-body">
                            <input type="hidden" id="bookId" name="bookId">
                            <div class="mb-3">
                                <label class="form-label">Book Name</label>
                                <input type="text" class="form-control" id="bookName" name="bookName" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="issueDate" class="form-label">Issue Date</label>
                                <input type="date" class="form-control" id="issueDate" name="issueDate" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="returnDate" class="form-label">Return Date (within 10 days from issue date)</label>
                                <input type="date" class="form-control" id="returnDate" name="returnDate" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn-cancel" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn-submit">Confirm Issue</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Add Bootstrap JS and its dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

        <script>
            document.addEventListener('DOMContentLoaded', function() {
                // Initialize Bootstrap modal
                const modal = document.getElementById('issueBookModal');
                const issueBookModal = new bootstrap.Modal(modal);

                // Handle modal opening
                modal.addEventListener('show.bs.modal', function(event) {
                    const button = event.relatedTarget;
                    const bookId = button.getAttribute('data-book-id');
                    const bookName = button.getAttribute('data-book-name');
                    
                    // Set book details
                    document.getElementById('bookId').value = bookId;
                    document.getElementById('bookName').value = bookName;
                    
                    // Set today as issue date
                    const today = new Date();
                    const issueInput = document.getElementById('issueDate');
                    issueInput.value = today.toISOString().split('T')[0];
                    
                    // Configure return date constraints
                    const returnInput = document.getElementById('returnDate');
                    const tomorrow = new Date(today);
                    tomorrow.setDate(tomorrow.getDate() + 1);
                    const maxDate = new Date(today);
                    maxDate.setDate(maxDate.getDate() + 10);
                    
                    returnInput.min = tomorrow.toISOString().split('T')[0];
                    returnInput.max = maxDate.toISOString().split('T')[0];
                    returnInput.value = tomorrow.toISOString().split('T')[0];
                });

                // Validate return date on change
                document.getElementById('returnDate').addEventListener('change', function() {
                    const issueDate = new Date(document.getElementById('issueDate').value);
                    const returnDate = new Date(this.value);
                    const maxDate = new Date(issueDate);
                    maxDate.setDate(maxDate.getDate() + 10);

                    if (returnDate <= issueDate) {
                        alert('Return date must be after the issue date');
                        this.value = '';
                    } else if (returnDate > maxDate) {
                        alert('Return date cannot be more than 10 days from the issue date');
                        this.value = '';
                    }
                });

                // Cache DOM elements for search functionality
                const searchInput = document.getElementById('searchInput');
                const bookCards = document.querySelectorAll('.book-card');
                
                // Debounce function for search
                function debounce(func, wait) {
                    let timeout;
                    return function executedFunction(...args) {
                        const later = () => {
                            clearTimeout(timeout);
                            func(...args);
                        };
                        clearTimeout(timeout);
                        timeout = setTimeout(later, wait);
                    };
                }
                
                // Filter books function
                const filterBooks = debounce(function(searchTerm) {
                    searchTerm = searchTerm.toLowerCase().trim();
                    let visibleCount = 0;
                    
                    bookCards.forEach(card => {
                        const title = card.getAttribute('data-title').toLowerCase();
                        const author = card.getAttribute('data-author').toLowerCase();
                        const edition = card.getAttribute('data-edition').toLowerCase();
                        
                        if (title.includes(searchTerm) || 
                            author.includes(searchTerm) || 
                            edition.includes(searchTerm)) {
                            card.classList.remove('hidden');
                            visibleCount++;
                        } else {
                            card.classList.add('hidden');
                        }
                    });
                    
                    const noResultsMessage = document.querySelector('.alert-info');
                    if (noResultsMessage) {
                        noResultsMessage.style.display = visibleCount === 0 ? 'block' : 'none';
                    }
                }, 300);
                
                // Add search event listener
                searchInput.addEventListener('input', (e) => filterBooks(e.target.value));
                
                // Handle image loading errors
                document.querySelectorAll('.book-image img').forEach(img => {
                    img.addEventListener('error', function() {
                        this.src = '${pageContext.request.contextPath}/images/books/library-hero.jpg';
                    });
                });
            });
        </script>
    </body>
</html> 