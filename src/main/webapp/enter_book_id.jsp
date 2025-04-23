<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Enter Book ID | Library System</title>
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

        .btn-search {
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

        .btn-search:hover {
            background: linear-gradient(to right, #ab47bc, #ec407a);
        }

        .error-msg {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            margin: 10px;
            border-radius: 5px;
            text-align: center;
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
       <%--<%@include file="navbar.jsp" %>--%>
    <div class="book-box">
        <div class="book-header">
            <h2>Enter Book ID</h2>
        </div>
        <div class="icon-box">
            <i class="fas fa-search"></i>
        </div>
        <div class="book-body">
            <% if (request.getAttribute("error") != null) { %>
                <div class="error-msg">
                    <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
                </div>
            <% } %>
            <form action="FindBookToUpdate" method="post">
                <div class="form-group">
                    <i class="fas fa-hashtag"></i>
                    <input type="number" name="bookId" class="form-control" placeholder="Enter Book ID" required min="1">
                </div>
                <button type="submit" class="btn btn-search">Find Book</button>
            </form>
        </div>
    </div>
</body>
</html> 