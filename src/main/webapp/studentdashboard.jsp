<%@ page import="com.webkorps.librarymanagement.model.Student" %>
<%
    Student student = (Student) session.getAttribute("student");
    if (student == null) {
        response.sendRedirect("studentlogin.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
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

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Arial', sans-serif;
        }

        body {
            background-color: var(--light-lavender);
        }

        .dashboard {
            display: flex;
            min-height: 100vh;
        }

        .sidebar {
            width: 250px;
            background: linear-gradient(135deg, var(--primary-color), var(--dark-purple));
            color: white;
            padding: 20px 0;
        }

        .sidebar-header {
            padding: 20px;
            text-align: center;
            border-bottom: 1px solid var(--accent-color);
            background: rgba(255, 255, 255, 0.1);
        }

        .student-info {
            padding: 20px;
            text-align: center;
            margin-bottom: 20px;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 10px;
        }

        .student-info img {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            margin-bottom: 10px;
            border: 3px solid var(--accent-color);
        }

        .nav-menu {
            list-style: none;
        }

        .nav-item {
            margin-bottom: 5px;
        }

        .nav-link {
            display: flex;
            align-items: center;
            padding: 12px 20px;
            color: var(--text-light);
            text-decoration: none;
            transition: all 0.3s ease;
            border-radius: 0 25px 25px 0;
            margin-right: 15px;
        }

        .nav-link:hover {
            background: var(--accent-color);
            color: var(--text-dark);
            transform: translateX(10px);
        }

        .nav-link i {
            margin-right: 10px;
            width: 20px;
            text-align: center;
        }

        .main-content {
            flex: 1;
            padding: 20px;
            background-color: var(--light-lavender);
        }

        .header {
            background: linear-gradient(135deg, var(--soft-pink), var(--accent-color));
            padding: 20px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            color: var(--text-dark);
        }

        .search-box {
            display: flex;
            align-items: center;
            background: rgba(255, 255, 255, 0.9);
            border-radius: 25px;
            padding: 5px 15px;
            border: none;
            width: 300px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .search-box input {
            border: none;
            background: none;
            padding: 8px;
            width: 100%;
            outline: none;
            color: var(--text-dark);
        }

        .search-box i {
            color: var(--primary-color);
        }

        .dashboard-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .card {
            background: white;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 5px;
            background: linear-gradient(90deg, var(--soft-pink), var(--accent-color));
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        }

        .card i {
            font-size: 2.5em;
            margin-bottom: 15px;
            background: linear-gradient(135deg, var(--primary-color), var(--accent-color));
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .card h3 {
            margin-bottom: 10px;
            color: var(--text-dark);
        }

        .card p {
            color: #6c757d;
        }

        .card .count {
            font-size: 28px;
            font-weight: bold;
            color: var(--primary-color);
            margin: 10px 0;
        }

        .active {
            background: var(--accent-color);
            color: var(--text-dark) !important;
        }

        .logout-btn {
            margin: 20px;
            padding: 12px 20px;
            background: linear-gradient(135deg, var(--soft-pink), #ff8fa3);
            color: var(--text-light);
            text-decoration: none;
            text-align: center;
            transition: 0.3s;
            border-radius: 25px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }

        .logout-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(0,0,0,0.15);
        }

        .recent-activity {
            background: white;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            margin-top: 20px;
        }

        .recent-activity h2 {
            margin-bottom: 20px;
            color: var(--primary-color);
            font-size: 1.5em;
        }

        .activity-item {
            display: flex;
            align-items: center;
            padding: 15px;
            border-bottom: 1px solid var(--light-lavender);
            transition: all 0.3s ease;
        }

        .activity-item:hover {
            background: var(--light-pink);
            border-radius: 10px;
        }

        .activity-item:last-child {
            border-bottom: none;
        }

        .activity-icon {
            width: 45px;
            height: 45px;
            border-radius: 50%;
            background: var(--light-lavender);
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 15px;
            transition: all 0.3s ease;
        }

        .activity-item:hover .activity-icon {
            background: white;
            transform: scale(1.1);
        }

        .activity-icon i {
            color: var(--primary-color);
            font-size: 1.2em;
        }

        .activity-details {
            flex: 1;
        }

        .activity-details h4 {
            margin: 0;
            color: var(--text-dark);
            font-size: 1.1em;
        }

        .activity-details p {
            margin: 5px 0 0;
            color: var(--primary-color);
            font-size: 0.9em;
        }

        @keyframes gradient {
            0% {
                background-position: 0% 50%;
            }
            50% {
                background-position: 100% 50%;
            }
            100% {
                background-position: 0% 50%;
            }
        }
    </style>
</head>
<body>
    <div class="dashboard">
        <div class="sidebar">
            <div class="sidebar-header">
                <h2>Library System</h2>
            </div>
            <div class="student-info">
                          <h3><%= student.getName() %></h3>
                <h3>Student ID: <%= student.getMembershipId() %></h3>
            </div>
            <ul class="nav-menu">
                <li class="nav-item">
                    <a href="#" class="nav-link active">
                        <i class="fas fa-home"></i> Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a href="GetAllBooks" class="nav-link">
                        <i class="fas fa-book"></i> View All Books
                    </a>
                </li>
                <li class="nav-item">
                    <a href="GetAvailableBooks" class="nav-link">
                        <i class="fas fa-book-reader"></i> Issue Book
                    </a>
                </li>
                <li class="nav-item">
                    <a href="ViewIssuedBooks" class="nav-link">
                        <i class="fas fa-list"></i> My Issued Books
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link">
                        <i class="fas fa-redo"></i> Renew Book
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link">
                        <i class="fas fa-undo"></i> Return Book
                    </a>
                </li>
            </ul>
            <a href="Logout" class="nav-link logout-btn">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
        </div>

        <div class="main-content">
            <div class="header">
                <h1>Welcome, <%= student.getName() %>!</h1>

            </div>

            <div class="dashboard-cards">
                <div class="card">
                    <i class="fas fa-book"></i>
                    <h3>Available Books</h3>
                    <div class="count">150</div>
                    <p>Browse our collection</p>
                </div>
                <div class="card">
                    <i class="fas fa-book-reader"></i>
                    <h3>Books Issued</h3>
                    <div class="count">3</div>
                    <p>Currently borrowed books</p>
                </div>
                <div class="card">
                    <i class="fas fa-clock"></i>
                    <h3>Due Soon</h3>
                    <div class="count">1</div>
                    <p>Books due within 3 days</p>
                </div>
                <div class="card">
                    <i class="fas fa-exclamation-circle"></i>
                    <h3>Overdue</h3>
                    <div class="count">0</div>
                    <p>Books past due date</p>
                </div>
            </div>

            <div class="recent-activity">
                <h2>Recent Activity</h2>
                <div class="activity-item">
                    <div class="activity-icon">
                        <i class="fas fa-book-reader"></i>
                    </div>
                    <div class="activity-details">
                        <h4>Issued: The Great Gatsby</h4>
                        <p>2 days ago • Due in 12 days</p>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon">
                        <i class="fas fa-undo"></i>
                    </div>
                    <div class="activity-details">
                        <h4>Returned: To Kill a Mockingbird</h4>
                        <p>5 days ago</p>
                    </div>
                </div>
                <div class="activity-item">
                    <div class="activity-icon">
                        <i class="fas fa-redo"></i>
                    </div>
                    <div class="activity-details">
                        <h4>Renewed: 1984</h4>
                        <p>1 week agoNew due date in 2 weeks</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html> 