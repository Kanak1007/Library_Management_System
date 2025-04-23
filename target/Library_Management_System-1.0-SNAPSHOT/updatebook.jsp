<%@page import="com.webkorps.librarymanagement.model.Book"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Book | Library System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Bootstrap & Font Awesome -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    
    <style>
        body {
            background: linear-gradient(to right, #fce4ec, #e1bee7);
            font-family: 'Segoe UI', sans-serif;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .book-box {
            background: #fff;
            border-radius: 20px;
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 420px;
            overflow: hidden;
        }

        .book-header {
            background: linear-gradient(to right, #7b1fa2, #e91e63);
            padding: 20px;
            text-align: center;
        }

        .book-header h2 {
            margin: 0;
            color: white;
            font-weight: 700;
        }

        .book-body {
            padding: 30px 25px;
        }

        .form-group {
            margin-bottom: 20px;
            position: relative;
        }

        .form-group i {
            position: absolute;
            top: 50%;
            left: 15px;
            transform: translateY(-50%);
            color: #ba68c8;
        }

        .form-control {
            border-radius: 30px;
            padding: 10px 20px 10px 40px;
            font-size: 0.95rem;
        }

        .btn-update {
            width: 100%;
            background: linear-gradient(to right, #ec407a, #ab47bc);
            border: none;
            color: white;
            border-radius: 30px;
            font-weight: 600;
            padding: 12px;
            font-size: 1rem;
            transition: 0.3s ease;
            margin-bottom: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .btn-update:hover {
            background: linear-gradient(to right, #ab47bc, #ec407a);
        }

        .btn-back {
            width: 100%;
            background: #6c757d;
            border: none;
            color: white;
            border-radius: 30px;
            font-weight: 600;
            padding: 12px;
            font-size: 1rem;
            transition: 0.3s ease;
        }

        .btn-back:hover {
            background: #5a6268;
        }

        .error-msg {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            text-align: center;
        }

        .success-msg {
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            text-align: center;
        }

        .image-preview {
            width: 100%;
            height: 200px;
            border-radius: 10px;
            border: 2px dashed #ba68c8;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
            position: relative;
        }

        .image-preview img {
            max-width: 100%;
            max-height: 100%;
            object-fit: contain;
        }

        .current-image-label {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            background: rgba(0, 0, 0, 0.7);
            color: white;
            text-align: center;
            padding: 5px;
            font-size: 0.8rem;
        }

        .file-input-wrapper {
            position: relative;
            margin-bottom: 15px;
        }

        .file-input-wrapper i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            z-index: 2;
            color: #ba68c8;
        }

        input[type="file"] {
            color: #495057;
            padding-left: 40px;
        }

        input[type="file"]::-webkit-file-upload-button {
            visibility: hidden;
            position: absolute;
            left: 0;
            top: 0;
        }

        input[type="file"]::before {
            content: 'Choose New Image';
            display: inline-block;
            background: linear-gradient(to right, #ec407a, #ab47bc);
            color: white;
            border: none;
            border-radius: 30px;
            padding: 8px 20px;
            margin-right: 15px;
            cursor: pointer;
        }

        input[type="file"]:hover::before {
            background: linear-gradient(to right, #ab47bc, #ec407a);
        }
    </style>
</head>
<body>
    <%
        Book book = (Book) request.getAttribute("book");
        if (book == null) {
            response.sendRedirect("enter_book_id.jsp");
            return;
        }
    %>
    
    <div class="book-box">
        <div class="book-header">
            <h2>Update Book</h2>
        </div>
        <div class="book-body">
            <% if (request.getAttribute("error") != null) { %>
                <div class="error-msg">
                    <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
                </div>
            <% } %>
            <% if (request.getAttribute("success") != null) { %>
                <div class="success-msg">
                    <i class="fas fa-check-circle"></i> <%= request.getAttribute("success") %>
                </div>
            <% } %>
            
            <form action="UpdateBook" method="post" enctype="multipart/form-data">
                <input type="hidden" name="book_id" value="<%= book.getBookId() %>">
                <input type="hidden" name="current_image" value="<%= book.getImagePath() %>">
                
                <div class="image-preview" id="imagePreview">
                    <img src="${pageContext.request.contextPath}/<%= book.getImagePath() %>" 
                         alt="<%= book.getBookName() %>" 
                         onerror="this.src='${pageContext.request.contextPath}/images/books/library-hero.jpg'">
                    <div class="current-image-label">Current Image</div>
                </div>
                
                <div class="file-input-wrapper">
                    <i class="fas fa-image"></i>
                    <input type="file" name="bookImage" class="form-control" accept="image/*" onchange="previewImage(this)">
                </div>

                <div class="form-group">
                    <i class="fas fa-book"></i>
                    <input type="text" class="form-control" name="name" value="<%= book.getBookName() %>" required>
                </div>

                <div class="form-group">
                    <i class="fas fa-user"></i>
                    <input type="text" class="form-control" name="author" value="<%= book.getBookAuthor() %>" required>
                </div>

                <div class="form-group">
                    <i class="fas fa-layer-group"></i>
                    <input type="text" class="form-control" name="edition" value="<%= book.getBookEdition() %>" required>
                </div>

                <div class="form-group">
                    <i class="fas fa-sort-numeric-up"></i>
                    <input type="number" class="form-control" name="quantity" value="<%= book.getBookQuantity() %>" min="0" required>
                </div>
                
                <button type="submit" class="btn btn-update">Update Book</button>
                <a href="GetAllBooksForAdmin" class="btn btn-back">Back to List</a>
            </form>
        </div>
    </div>

    <script>
        function previewImage(input) {
            const preview = document.getElementById('imagePreview');
            const currentLabel = preview.querySelector('.current-image-label');
            
            if (input.files && input.files[0]) {
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    const img = preview.querySelector('img');
                    img.src = e.target.result;
                    if (currentLabel) {
                        currentLabel.textContent = 'New Image Selected';
                    }
                }
                
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</body>
</html>
