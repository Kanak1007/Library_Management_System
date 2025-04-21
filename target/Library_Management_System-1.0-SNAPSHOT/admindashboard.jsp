<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Library Admin Dashboard</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>

  <style>
    body {
  font-family: 'Segoe UI', sans-serif;
  background: linear-gradient(to bottom right, #ffe4f0, #e6e6fa);
  padding-top: 80px;
}

.navbar-custom {
  background: linear-gradient(to right, #d8bfd8, #ffb6c1);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.navbar-custom .navbar-brand,
.navbar-custom .nav-link {
  color: #000 !important;
  font-weight: 500;
}

.navbar-custom .nav-link:hover {
  background-color: rgba(255, 255, 255, 0.15);
  border-radius: 5px;
}

.card,
.stats-box {
  background: #fff0f5;
  border-radius: 15px;
  text-align: center;
  padding: 20px;
  height: 100%;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.card:hover {
  transform: translateY(-4px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.08);
}

.card-body i {
  font-size: 1.8rem;
  margin-bottom: 10px;
  color: #d87093;
}

.card-body h5 {
  font-size: 1.1rem;
  margin-bottom: 5px;
}

.card-body p {
  font-size: 0.9rem;
  margin-bottom: 12px;
}

.btn-custom {
  background: linear-gradient(to right, #ffb6c1, #dda0dd);
  color: #fff;
  border: none;
  padding: 6px 20px;
  border-radius: 30px;
  font-weight: 500;
  font-size: 0.9rem;
}

.btn-custom:hover {
  background: linear-gradient(to right, #dda0dd, #ffb6c1);
}

.stats-box h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: #ba55d3;
}

.stats-box p {
  font-size: 2rem;
  color: #d87093;
  margin: 0;
}

  </style>
</head>

<body>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg navbar-custom fixed-top">
    <div class="container-fluid">
      <a class="navbar-brand" href="#"> Library Admin</a>
      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item"><a class="nav-link" href="#"><i class="fas fa-chart-line"></i> Dashboard</a></li>
          <li class="nav-item"><a class="nav-link" href="addbook.jsp"><i class="fas fa-plus-circle"></i> Add Books</a></li>
          <li class="nav-item"><a class="nav-link" href="viewbooks.jsp"><i class="fas fa-eye"></i> View All Books</a></li>
          <li class="nav-item"><a class="nav-link" href="removebook.jsp"><i class="fas fa-trash-alt"></i> Delete Books</a></li>
          <li class="nav-item"><a class="nav-link" href="updatebook.jsp"><i class="fas fa-edit"></i> Update Books</a></li>
          <li class="nav-item"><a class="nav-link" href="index.jsp"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Dashboard -->
  <div class="container mt-5">
    <h2 class="text-center text-dark mb-5"> Welcome to Admin Panel</h2>

    <!-- Action Cards -->
    <div class="row g-4">
      <div class="col-md-4">
        <div class="card h-100">
          <div class="card-body">
            <i class="fas fa-book-medical"></i>
            <h5 class="mt-3">Add Book</h5>
            <p>Add new books to the library.</p>
            <a href="addbook.jsp" class="btn btn-custom">Add</a>
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card h-100">
          <div class="card-body">
            <i class="fas fa-eye"></i>
            <h5 class="mt-3">View All Books</h5>
            <p>See the entire book collection.</p>
            <a href="viewbooks.jsp" class="btn btn-custom">View</a>
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card h-100">
          <div class="card-body">
            <i class="fas fa-trash-alt"></i>
            <h5 class="mt-3">Delete Book</h5>
            <p>Remove books that are no longer needed.</p>
            <a href="removebook.jsp" class="btn btn-custom">Delete</a>
          </div>
        </div>
      </div>

      <div class="col-md-4">
        <div class="card h-100">
          <div class="card-body">
            <i class="fas fa-edit"></i>
            <h5 class="mt-3">Update Book</h5>
            <p>Modify details of existing books.</p>
            <a href="updatebook.jsp" class="btn btn-custom">Update</a>
          </div>
        </div>
      </div>

      <div class="col-md-4">
        <div class="stats-box">
          <h3>Total Books</h3>
          <p>${totalBooks}</p>
        </div>
      </div>

      <div class="col-md-4">
        <div class="stats-box">
          <h3>Books Issued</h3>
          <p>${issuedBooks}</p>
        </div>
      </div>
    </div>
  </div>

  <!-- Scripts -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
