<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Registration | Library System</title>
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

        .register-box {
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 380px;
            overflow: hidden;
        }

        .register-header {
            background: linear-gradient(to right, #ec407a, #ab47bc);
            padding: 15px;
            text-align: center;
        }

        .register-header h2 {
            margin: 0;
            color: white;
            font-weight: 600;
            font-size: 1.5rem;
        }

        .register-body {
            padding: 20px 15px;
        }

        .form-label {
            font-weight: 500;
            color: #555;
            font-size: 0.9rem;
            margin-bottom: 5px;
        }

        .form-group {
            margin-bottom: 12px;
        }

        .input-group {
            position: relative;
        }

        .input-group i {
            position: absolute;
            left: 12px;
            top: 50%;
            transform: translateY(-50%);
            color: #ba68c8;
            font-size: 0.9rem;
        }

        .form-control {
            border-radius: 20px;
            padding: 8px 15px 8px 35px;
            font-size: 0.9rem;
            border: 1px solid #e1bee7;
            height: 38px;
        }

        .form-control:focus {
            border-color: #ab47bc;
            box-shadow: 0 0 0 0.2rem rgba(171, 71, 188, 0.25);
        }

        .btn-register {
            width: 100%;
            background: linear-gradient(to right, #ec407a, #ab47bc);
            border: none;
            color: white;
            border-radius: 20px;
            font-weight: 600;
            padding: 10px;
            font-size: 0.9rem;
            transition: 0.3s ease;
            margin-top: 5px;
            height: 38px;
        }

        .btn-login-redirect {
            width: 100%;
            background: transparent;
            border: 2px solid #ec407a;
            color: #ec407a;
            border-radius: 20px;
            font-weight: 600;
            padding: 8px;
            font-size: 0.9rem;
            transition: 0.3s ease;
            margin-top: 8px;
            height: 38px;
        }

        .icon-box {
            text-align: center;
            margin-top: -20px;
        }

        .icon-box i {
            background: #fff;
            padding: 8px;
            border-radius: 50%;
            font-size: 2rem;
            color: #ab47bc;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>

    <div class="register-box">
        <div class="register-header">
            <h2>Admin Registration</h2>
        </div>
        <div class="icon-box">
            <i class="fas fa-user-plus"></i>
        </div>
        <div class="register-body">
            <form action="registerAdminServlet" method="post">
                <div class="form-group">
                    <label class="form-label">Admin Name</label>
                    <div class="input-group">
                        <i class="fas fa-user"></i>
                        <input type="text" class="form-control" name="adminName" placeholder="Enter admin name" required>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label">Library Name</label>
                    <div class="input-group">
                        <i class="fas fa-book"></i>
                        <input type="text" class="form-control" name="libraryName" placeholder="Enter library name" required>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label">Address</label>
                    <div class="input-group">
                        <i class="fas fa-map-marker-alt"></i>
                        <input type="text" class="form-control" name="address" placeholder="Enter library address" required>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label">Email</label>
                    <div class="input-group">
                        <i class="fas fa-envelope"></i>
                        <input type="email" class="form-control" name="email" placeholder="Enter your email" required>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label">Role</label>
                    <div class="input-group">
                        <i class="fas fa-user-tag"></i>
                        <input type="text" class="form-control" name="role" value="Admin" readonly>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label">Password</label>
                    <div class="input-group">
                        <i class="fas fa-lock"></i>
                        <input type="password" class="form-control" name="password" placeholder="Create a password" required>
                    </div>
                </div>
                <button type="submit" class="btn btn-register">Register</button>
                <div class="text-center mt-3">
                    <p class="mb-2">Already have an account?</p>
                    <a href="adminlogin.jsp" class="btn btn-login-redirect">Login</a>
                </div>
            </form>
        </div>
    </div>

</body>
</html>
