<%@ page import="com.webkorps.librarymanagement.model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="com.webkorps.librarymanagement.model.Student" %>
<%
    Student student = (Student) session.getAttribute("student");
    if (student == null) {
        response.sendRedirect("studentlogin.jsp");
        return;
    }
    
    List<Book> books = (List<Book>) request.getAttribute("zeroQuantityBooks");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Request Books</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2e6ff;
            margin: 0;
            padding: 20px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .back-button {
            text-decoration: none;
            color: #4a4a4a;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .books-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }

        .book-card {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            overflow: hidden;
            transition: transform 0.2s;
        }

        .book-card:hover {
            transform: translateY(-5px);
        }

        .book-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .book-details {
            padding: 15px;
        }

        .book-title {
            font-size: 1.2em;
            margin: 0 0 10px 0;
            color: #333;
        }

        .book-author, .book-edition {
            color: #666;
            font-size: 0.9em;
            margin: 5px 0;
        }

        .request-btn {
            width: 100%;
            padding: 10px;
            background-color: #9b6b9d;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
            transition: background-color 0.2s;
        }

        .request-btn:hover {
            background-color: #7d557f;
        }

        .alert {
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 15px 25px;
            border-radius: 4px;
            background-color: #4CAF50;
            color: white;
            display: none;
            z-index: 1000;
        }

        .no-books {
            text-align: center;
            padding: 50px;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="header">
        <a href="studentdashboard.jsp" class="back-button">
            <i class="fas fa-arrow-left"></i>
            Back to Dashboard
        </a>
        <h1>Request Books</h1>
    </div>

    <div id="successAlert" class="alert">
        Book requested successfully!
    </div>

    <div class="books-grid">
        <% if (books != null && !books.isEmpty()) { %>
            <% for (Book book : books) { %>
                <div class="book-card">
                    <img src="<%= book.getImagePath() != null ? book.getImagePath() : "images/default-book.jpg" %>" 
                         alt="<%= book.getBookName() %>" class="book-image">
                    <div class="book-details">
                        <h3 class="book-title"><%= book.getBookName() %></h3>
                        <p class="book-author">By <%= book.getBookAuthor() %></p>
                        <p class="book-edition">Edition: <%= book.getBookEdition() %></p>
                        <button class="request-btn" onclick="requestBook(<%= book.getBookId() %>)">
                            Request Book
                        </button>
                    </div>
                </div>
            <% } %>
        <% } else { %>
            <div class="no-books">
                <i class="fas fa-book-open" style="font-size: 3em; margin-bottom: 20px;"></i>
                <p>No books available for request at the moment.All Books are Available</p>
            </div>
        <% } %>
    </div>
    
<!--    <script>
        function requestBook(bookId) {
            if (!confirm('Are you sure you want to request this book?')) return;

            fetch('RequestBook?bookId=' + bookId, {
                method: 'POST'
            }).then(async resp => {
                const data  = await resp.json(); 
                return data;
            }).then(async response => {
                console.log(response);
                const text = await response.text();
                const alert = document.getElementById('successAlert');
                alert.textContent = text;
                alert.style.display = 'block';

                if (response.ok===200) { // This checks for status codes 200-299
                    // Book requested successfully
                    const bookCard = document.querySelector(`button[onclick="requestBook(${bookId})"]`).closest('.book-card');
                    const requestBtn = bookCard.querySelector('.request-btn');
                    requestBtn.textContent = 'Requested';
                    requestBtn.disabled = true;
                    requestBtn.style.backgroundColor = '#6c757d';
                    alert.style.backgroundColor = '#4CAF50'; // green
                } else {
                    // Handle different error cases
                    switch (response.status) {
                        case 409:
                            // Already requested
                            alert.style.backgroundColor = '#ffc107'; // yellow
                            break;
                        case 400:
                            // Bad request
                            alert.style.backgroundColor = '#dc3545'; // red
                            break;
                        case 401:
                            // Unauthorized
                            alert.style.backgroundColor = '#dc3545'; // red
                            break;
                        default:
                            // Other errors
                            alert.style.backgroundColor = '#dc3545'; // red
                            break;
                    }
                }

                setTimeout(() => {
                    alert.style.display = 'none';
                }, 3000);
            })
            .catch(error => {
                console.error('Error:', error);
                const alert = document.getElementById('successAlert');
                alert.textContent = 'An error occurred while processing your request. Please try again.';
                alert.style.backgroundColor = '#dc3545';
                alert.style.display = 'block';
                console.log("__________________________________________________________"+error);
                setTimeout(() => {
                    alert.style.display = 'none';
                }, 3000);
            });
        }
    </script>-->
<script>
function requestBook(bookId) {
    if (!confirm('Are you sure you want to request this book?')) return;

    fetch('RequestBook?bookId=' + bookId, {
        method: 'POST',
        headers: {
            'Accept': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw err; });
        }
        return response.json();
    })
    .then(data => {
        const alert = document.getElementById('successAlert');
        alert.textContent = data.message;
        alert.style.display = 'block';
        
        if (data.status === "success") {
            const btn = document.querySelector(`button[onclick="requestBook(${bookId})"]`);
            if (btn) {
                btn.textContent = 'Requested';
                btn.disabled = true;
                btn.style.backgroundColor = '#6c757d';
            }
            alert.style.backgroundColor = '#4CAF50';
        } else {
            alert.style.backgroundColor = '#dc3545';
        }
        
        setTimeout(() => alert.style.display = 'none', 3000);
    })
    .catch(error => {
        console.error('Error:', error);
        const alert = document.getElementById('successAlert');
        alert.textContent = error.message || 'Request failed. Please try again.';
        alert.style.backgroundColor = '#dc3545';
        alert.style.display = 'block';
        setTimeout(() => alert.style.display = 'none', 3000);
    });
}

</script>


</body>
</html> 