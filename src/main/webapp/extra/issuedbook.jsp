<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Issued Books</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .book-card {
            transition: transform 0.3s ease-in-out;
            height: 100%;
        }
        .book-card:hover {
            transform: translateY(-5px);
        }
        .book-image {
            height: 200px;
            object-fit: cover;
        }
        .days-remaining {
            font-size: 0.9rem;
            padding: 0.25rem 0.5rem;
            border-radius: 15px;
        }
        .days-remaining.overdue {
            background-color: #ffe0e0;
            color: #dc3545;
        }
        .days-remaining.warning {
            background-color: #fff3cd;
            color: #856404;
        }
        .days-remaining.good {
            background-color: #d4edda;
            color: #155724;
        }
        .book-status {
            position: absolute;
            top: 10px;
            right: 10px;
            padding: 0.25rem 0.5rem;
            border-radius: 15px;
            font-size: 0.8rem;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row mb-4">
            <div class="col">
                <h2 class="mb-0">My Issued Books</h2>
                <p class="text-muted">Total Books: ${totalBooks} 
                    <c:if test="${overdueCount > 0}">
                        <span class="text-danger">(${overdueCount} overdue)</span>
                    </c:if>
                </p>
            </div>
        </div>

        <c:if test="${empty issuedBooks}">
            <div class="alert alert-info" role="alert">
                You haven't issued any books yet.
            </div>
        </c:if>

        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <c:forEach var="book" items="${issuedBooks}">
                <div class="col">
                    <div class="card book-card shadow-sm">
                        <c:if test="${not empty book.imagePath}">
                            <img src="${book.imagePath}" class="card-img-top book-image" alt="${book.bookName}">
                        </c:if>
                        <div class="card-body">
                            <h5 class="card-title">${book.bookName}</h5>
                            <p class="card-text">
                                <small class="text-muted">By ${book.author}</small><br>
                                <small class="text-muted">Edition: ${book.edition}</small>
                            </p>
                            
                            <div class="mt-3">
                                <p class="mb-1"><strong>Issued:</strong> 
                                    <fmt:formatDate value="${book.issueDate}" pattern="MMM dd, yyyy"/>
                                </p>
                                <p class="mb-1"><strong>Due:</strong> 
                                    <fmt:formatDate value="${book.returnDate}" pattern="MMM dd, yyyy"/>
                                </p>
                                
                                <c:choose>
                                    <c:when test="${book.isOverdue}">
                                        <span class="days-remaining overdue">
                                            ${-book.daysRemaining} days overdue
                                        </span>
                                    </c:when>
                                    <c:when test="${book.daysRemaining <= 3}">
                                        <span class="days-remaining warning">
                                            ${book.daysRemaining} days remaining
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="days-remaining good">
                                            ${book.daysRemaining} days remaining
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            
                            <div class="mt-3">
                                <form action="ReturnBook" method="post" style="display: inline;">
                                    <input type="hidden" name="issueId" value="${book.issueId}">
                                    <button type="submit" class="btn btn-primary btn-sm">Return Book</button>
                                </form>
                                <c:if test="${not book.isOverdue}">
                                    <form action="RenewBook" method="post" style="display: inline;">
                                        <input type="hidden" name="issueId" value="${book.issueId}">
                                        <button type="submit" class="btn btn-outline-primary btn-sm ms-2">Renew</button>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>