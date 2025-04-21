<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Error - Library Management System</title>

        <!-- Bootstrap & Font Awesome -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">

        <style>
            body {
                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                font-family: 'Segoe UI', sans-serif;
            }

            .error-container {
                background: white;
                border-radius: 20px;
                padding: 2rem;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                max-width: 500px;
                width: 90%;
                text-align: center;
            }

            .error-icon {
                font-size: 4rem;
                color: #dc3545;
                margin-bottom: 1rem;
                animation: pulse 2s infinite;
            }

            .error-code {
                font-size: 1.5rem;
                color: #6c757d;
                margin-bottom: 0.5rem;
            }

            .error-message {
                font-size: 1.2rem;
                color: #343a40;
                margin-bottom: 2rem;
            }

            .error-details {
                background: #f8f9fa;
                padding: 1rem;
                border-radius: 10px;
                margin-bottom: 2rem;
                text-align: left;
            }

            .btn-home {
                background: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
                color: white;
                border: none;
                padding: 0.8rem 2rem;
                border-radius: 30px;
                font-weight: 600;
                transition: all 0.3s ease;
            }

            .btn-home:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
                color: white;
            }

            .btn-back {
                background: transparent;
                color: #4facfe;
                border: 2px solid #4facfe;
                padding: 0.8rem 2rem;
                border-radius: 30px;
                font-weight: 600;
                transition: all 0.3s ease;
                margin-left: 1rem;
            }

            .btn-back:hover {
                background: #4facfe;
                color: white;
            }

            @keyframes pulse {
                0% {
                    transform: scale(1);
                }
                50% {
                    transform: scale(1.1);
                }
                100% {
                    transform: scale(1);
                }
            }
        </style>
    </head>
    <body>
        <div class="error-container">
            <i class="fas fa-exclamation-circle error-icon"></i>

            <div class="error-code">
                Error ${pageContext.errorData.statusCode}
            </div>
            <% if (session.getAttribute("error") != null) {%>
            <div class="error-details">
                <small class="text-muted">Session Error:</small><br>
                <%= session.getAttribute("error")%>
            </div>
            <% } %>

            <div class="error-message">
                <% if (request.getAttribute("error") != null) {%>
                <%= request.getAttribute("error")%>
                <% } else { %>
                Oops! Something went wrong.
                <% } %>
            </div>

            <% if (exception != null) {%>
            <div class="error-details">
                <small class="text-muted">Error Details:</small><br>
                <%= exception.getMessage()%>
            </div>
            <% }%>

            <div class="mt-4">
                <a href="index.jsp" class="btn btn-home">
                    <i class="fas fa-home me-2"></i>Home
                </a>
                <button onclick="history.back()" class="btn btn-back">
                    <i class="fas fa-arrow-left me-2"></i>Go Back
                </button>
            </div>
        </div>
    </body>
</html>
