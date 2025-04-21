



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Management System</title>

    <!-- Bootstrap & Fonts -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;500;700&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

    <style>
        body {
            font-family: 'Outfit', sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: #fff;
        }

        .navbar {
            background: transparent;
            border-bottom: 1px solid rgba(255, 255, 255, 0.2);
        }

        .navbar-brand {
            font-weight: bold;
            font-size: 1.8rem;
            color: #fff !important;
        }

        .jumbotron {
            padding: 6rem 1rem 3rem;
            text-align: center;
        }

        .jumbotron h1 {
            font-size: 3rem;
            font-weight: 700;
        }

        .jumbotron p {
            font-size: 1.25rem;
            margin-top: 1rem;
            color: #e2e8f0;
        }

        .role-selection {
            margin-top: 3rem;
        }

        .card.glass-card {
            background: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
            border-radius: 20px;
            backdrop-filter: blur(10px);
            box-shadow: 0 20px 50px rgba(0,0,0,0.1);
            padding: 2rem;
            color: #fff;
            transition: transform 0.4s ease, box-shadow 0.4s ease;
        }

        .card.glass-card:hover {
            transform: scale(1.03);
            box-shadow: 0 30px 60px rgba(0,0,0,0.2);
        }

        .btn-glow {
            background: linear-gradient(135deg, #ff9a9e, #fad0c4);
            color: #111;
            font-weight: 600;
            border: none;
            border-radius: 30px;
            padding: 0.7rem 2rem;
            font-size: 1.1rem;
            transition: all 0.3s ease;
        }

        .btn-glow:hover {
            background: linear-gradient(135deg, #fbc2eb, #a18cd1);
            box-shadow: 0 0 20px rgba(255, 255, 255, 0.4);
        }

        .icon {
            font-size: 3rem;
            margin-bottom: 1rem;
            color: #fff;
        }

        footer {
            text-align: center;
            padding: 2rem;
            color: #e2e8f0;
            font-size: 0.9rem;
        }
    </style>
</head>

<body>

    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand" href="#">📚 Library Management</a>
        </div>
    </nav>

    <div class="jumbotron">
        <h1>Welcome to Our Library!</h1>
        <p>Manage your books, explore knowledge, and simplify your reading journey.</p>
    </div>

    <div class="container role-selection">
        <div class="row justify-content-center g-4">
            <div class="col-md-5">
                <div class="card glass-card text-center">
                    <i class="fas fa-user-graduate icon"></i>
                    <h3>Join as Student</h3>
                    <p>Access, borrow, and enjoy books curated just for students.</p>
                    <a href="studentregister.jsp" class="btn btn-glow mt-3">Join as Student</a>
                </div>
            </div>
            <div class="col-md-5">
                <div class="card glass-card text-center">
                    <i class="fas fa-user-shield icon"></i>
                    <h3>Join as Admin</h3>
                    <p>Manage book inventory, student accounts, and lending activities.</p>
                    <a href="adminregister.jsp" class="btn btn-glow mt-3">Join as Admin</a>
                </div>
            </div>
        </div>
    </div>

    <footer class="mt-5">
        <p>&copy; 2025 Library Management System. Made with ❤️</p>
    </footer>

</body>
</html>

