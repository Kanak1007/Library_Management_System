<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            :root {
                --primary-color: #9b6b9d;
                --secondary-color: #e5a9e4;
                --accent-color: #d4a5ff;
                --light-lavender: #f2e6ff;
                --dark-purple: #6a4c93;
                --soft-pink: #ffb6c1;
                --light-pink: #ffe4e8;
                --text-dark: #4a4a4a;
                --text-light: #ffffff;
            }

            body {
                background-color: var(--light-lavender);
                font-family: 'Arial', sans-serif;
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                margin: 0;
                padding: 20px;
            }

            .login-container {
                background: white;
                border-radius: 20px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                overflow: hidden;
                width: 100%;
                max-width: 400px;
                position: relative;
            }

            .login-container::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 5px;
                background: linear-gradient(90deg, var(--soft-pink), var(--accent-color));
            }

            .login-header {
                background: linear-gradient(135deg, var(--primary-color), var(--dark-purple));
                color: white;
                padding: 30px;
                text-align: center;
            }

            .login-header h2 {
                margin: 0;
                font-size: 1.8rem;
                font-weight: 600;
            }

            .login-form {
                padding: 30px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-control {
                border: 2px solid var(--light-lavender);
                border-radius: 10px;
                padding: 12px 15px;
                font-size: 1rem;
                transition: all 0.3s ease;
            }

            .form-control:focus {
                border-color: var(--accent-color);
                box-shadow: 0 0 0 0.2rem rgba(212, 165, 255, 0.25);
            }

            .btn-login {
                background: linear-gradient(135deg, var(--primary-color), var(--dark-purple));
                color: white;
                border: none;
                border-radius: 10px;
                padding: 12px;
                width: 100%;
                font-size: 1rem;
                font-weight: 600;
                transition: all 0.3s ease;
                margin-top: 10px;
            }

            .btn-login:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            }

            .register-link {
                text-align: center;
                margin-top: 20px;
                color: var(--text-dark);
            }

            .register-link a {
                color: var(--primary-color);
                text-decoration: none;
                font-weight: 600;
                transition: all 0.3s ease;
            }

            .register-link a:hover {
                color: var(--dark-purple);
            }

            .error-message {
                background: var(--light-pink);
                color: #c62828;
                padding: 10px;
                border-radius: 10px;
                margin-bottom: 20px;
                text-align: center;
                font-size: 0.9rem;
            }

            .input-group {
                position: relative;
            }

            .input-group i {
                position: absolute;
                left: 15px;
                top: 50%;
                transform: translateY(-50%);
                color: var(--primary-color);
            }

            .input-group .form-control {
                padding-left: 40px;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <div class="login-header">
                <h2>Student Login</h2>
            </div>
            <div class="login-form">
                <% if (request.getAttribute("error") != null) { %>
                    <div class="error-message">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                
                <form action="StudentLogin" method="post">
                    <div class="form-group">
                        <div class="input-group">
                            <i class="fas fa-user"></i>
                            <input type="text" class="form-control" name="membershipId" placeholder="Student ID" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <i class="fas fa-lock"></i>
                            <input type="password" class="form-control" name="password" placeholder="Password" required>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-login">Login</button>
                </form>
                
                <div class="register-link">
                    Don't have an account? <a href="studentregister.jsp">Register here</a>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html> 