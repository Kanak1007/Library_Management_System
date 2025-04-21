<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Login | Library System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap & Font Awesome -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">

    <!-- Custom Styles -->
    <style>
        body {
            background: linear-gradient(to right, #fce4ec, #e1bee7);
            font-family: 'Segoe UI', sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .login-box {
            background: #fff;
            border-radius: 20px;
            padding: 40px 35px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 450px;
        }

        .login-box h2 {
            text-align: center;
            font-weight: 700;
            margin-bottom: 30px;
            color: #c2185b;
        }

        .form-control {
            border-radius: 30px;
            padding: 10px 20px;
        }

        .btn-login {
            width: 100%;
            background: linear-gradient(to right, #ec407a, #ab47bc);
            border: none;
            color: white;
            border-radius: 30px;
            font-weight: 600;
            padding: 10px;
            transition: 0.3s ease;
        }

        .btn-login:hover {
            background: linear-gradient(to right, #ab47bc, #ec407a);
        }

        .error-msg {
            color: red;
            text-align: center;
            font-weight: 500;
        }

        .icon-box {
            text-align: center;
            margin-bottom: 20px;
        }

        .icon-box i {
            font-size: 3rem;
            color: #ba68c8;
        }
    </style>
</head>
<body>

    <div class="login-box">
        <div class="icon-box">
            <i class="fas fa-user-shield"></i>
        </div>
        <h2>Admin Login</h2>

        <% if (request.getAttribute("error") != null) { %>
            <div class="error-msg mb-3">
                <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="AdminLogin" method="post">
            <div class="mb-3">
                <label for="membershipId" class="form-label">Membership ID</label>
                <input type="number" name="membershipId" id="membershipId" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" name="password" id="password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-login mb-3">Login</button>
            <div class="text-center">
                <p class="mb-2">Don't have an account?</p>
                <a href="adminregister.jsp" class="btn btn-outline-primary">Register as Admin</a>
            </div>
        </form>
    </div>

</body>
</html>
