<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="AdminDashboardServlet">Library Management System</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="AdminDashboardServlet">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="addbook.jsp">Add Book</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="GetAllBooksForAdmin">View Books</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="removebook.jsp">Remove Book</a>
                </li>
            </ul>
            <div class="d-flex align-items-center">
                <span class="text-light me-3">
                    <i class="fas fa-user"></i> ${sessionScope.adminname} (ID: ${sessionScope.adminId})
                </span>
                <a href="Logout" class="btn btn-outline-light">
                    <i class="fas fa-sign-out-alt"></i> Logout
                </a>
            </div>
        </div>
    </div>
</nav> 