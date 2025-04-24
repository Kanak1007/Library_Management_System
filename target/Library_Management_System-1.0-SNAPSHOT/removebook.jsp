<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Remove Book | Library</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #fce4ec, #e1bee7);
            font-family: 'Segoe UI', sans-serif;
            padding: 40px;
        }

        .container {
            background: #fff;
            border-radius: 20px;
            padding: 30px;
            max-width: 600px;
            margin: auto;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #d32f2f;
            text-align: center;
            margin-bottom: 25px;
        }

        .btn-red {
            background-color: #d32f2f;
            color: white;
        }

        .btn-red:hover {
            background-color: #b71c1c;
        }
    </style>
</head>
<body>
   <%--<%@include file="navbar.jsp" %>--%>
<div class="container">
    <h2>Remove Book</h2>
     <% if (request.getAttribute("success") != null) { %>
            <div class="success-msg mb-3">
                <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("success") %>
            </div>
        <% } %>
             <% if (request.getAttribute("error") != null) { %>
            <div class="success-msg mb-3">
                <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
            </div>
        <% } %>
   <!--<form action="${pageContext.request.contextPath}/RemoveBook" method="post">-->
    <form action="RemoveBook" method="post">
        <div class="mb-3">
            <label class="form-label">Book ID</label>
            <input type="number" class="form-control" name="bookId" placeholder="Enter Book ID to remove" min="1" required>
        </div>

        <div class="d-grid gap-2">
            <button type="submit" class="btn btn-red">Remove Book</button>
            <a href="viewbooks.jsp" class="btn btn-secondary">Back to List</a>
        </div>
    </form>
</div>

</body>
</html>
