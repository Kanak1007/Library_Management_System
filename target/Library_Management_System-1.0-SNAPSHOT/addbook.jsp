    <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Book | Library System</title>
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

        .form-label {
            font-weight: 600;
            color: #555;
        }

        .form-group {
            margin-bottom: 15px;
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

        .form-control.file-input {
            padding-left: 40px;
            height: auto;
            cursor: pointer;
        }

        /* Custom file input styling */
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
        }

        input[type="file"]::-webkit-file-upload-button {
            visibility: hidden;
            position: absolute;
            left: 0;
            top: 0;
        }

        input[type="file"]::before {
            content: 'Choose Image';
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
        }

        .image-preview img {
            max-width: 100%;
            max-height: 100%;
            object-fit: contain;
        }

        .image-preview.no-image {
            background: #f8f9fa;
        }

        .image-preview.no-image::after {
            content: 'Book Cover Preview';
            color: #6c757d;
            font-size: 1rem;
        }

        .btn-add-book {
            width: 100%;
            background: linear-gradient(to right, #ec407a, #ab47bc);
            border: none;
            color: white;
            border-radius: 30px;
            font-weight: 600;
            padding: 12px;
            font-size: 1rem;
            transition: 0.3s ease;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .btn-add-book:hover {
            background: linear-gradient(to right, #ab47bc, #ec407a);
        }

        .icon-box {
            text-align: center;
            margin-top: -25px;
        }

        .icon-box i {
            background: #fff;
            padding: 10px;
            border-radius: 50%;
            font-size: 2.5rem;
            color: #ab47bc;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
   <% if (request.getAttribute("error") != null) { %>
            <div class="error-msg mb-3">
                <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
    <div class="book-box">
        <div class="book-header">
            <h2>Add New Book</h2>
        </div>
        <% if (request.getAttribute("success") != null) { %>
            <div class="success-msg mb-3" style="background-color: #d4edda; color: #155724; padding: 10px; margin: 10px; border-radius: 5px; text-align: center;">
                <i class="fas fa-check-circle"></i> <%= request.getAttribute("success") %>
            </div>
        <% } %>
        <div class="icon-box">
            <i class="fas fa-book"></i>
        </div>
        <div class="book-body">
            <form action="AddBook" method="post" enctype="multipart/form-data">
                <div class="image-preview no-image" id="imagePreview">
                </div>
                <div class="file-input-wrapper">
                    <i class="fas fa-image"></i>
                    <input type="file" name="bookImage" class="form-control file-input" accept="image/*" onchange="previewImage(this)">
                </div>
                <div class="form-group">
                    <i class="fas fa-book-open"></i>
                    <input type="text" name="bookname" class="form-control" placeholder="Book Name" required>
                </div>
                <div class="form-group">
                    <i class="fas fa-user-edit"></i>
                    <input type="text" name="bookauthor" class="form-control" placeholder="Author Name" required>
                </div>
                <div class="form-group">
                    <i class="fas fa-layer-group"></i>
                    <input type="text" name="bookedition" class="form-control" placeholder="Edition" required>
                </div>
                <div class="form-group">
                    <i class="fas fa-sort-numeric-up"></i>
                    <input type="number" name="bookquantity" class="form-control" placeholder="Quantity" min="1" required>
                </div>
                <button type="submit" class="btn btn-add-book">Add Book</button>
            </form>
        </div>
    </div>

    <script>
        function previewImage(input) {
            const preview = document.getElementById('imagePreview');
            preview.innerHTML = '';
            preview.classList.remove('no-image');

            if (input.files && input.files[0]) {
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    preview.appendChild(img);
                }
                
                reader.readAsDataURL(input.files[0]);
            } else {
                preview.classList.add('no-image');
            }
        }
    </script>
</body>
</html>
