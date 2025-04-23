//package com.webkorps.librarymanagement.model;
//
//import java.sql.Date;
//import java.time.LocalDate;
//
//public class IssueBook {
//    private int issueId;
//    private int bookId;
//    private int userId;
//    private Date issueDate;
//    private Date returnDate;
//    private Date dueDate;
//    private String status;
//    private Book book;
//    private Student student;
//   
//
//
//    // Default constructor
//    public IssueBook() {
//    }
//
//    // Constructor for creating new issue with return date
//    public IssueBook(int bookId, int userId, Date issueDate, Date dueDate) {
//        this.bookId = bookId;
//        this.userId = userId;
//        this.issueDate = issueDate;
//        this.dueDate = dueDate;
//        this.status = "ISSUED";
//    }
//
//    // Getters and Setters
//    public int getIssueId() {
//        return issueId;
//    }
//
//    public void setIssueId(int issueId) {
//        this.issueId = issueId;
//    }
//
//    public int getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(int bookId) {
//        this.bookId = bookId;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public Date getIssueDate() {
//        return issueDate;
//    }
//
//    public void setIssueDate(Date issueDate) {
//        this.issueDate = issueDate;
//    }
//
//    public Date getReturnDate() {
//        return returnDate;
//    }
//
//    public void setReturnDate(Date returnDate) {
//        this.returnDate = returnDate;
//    }
//
//    public Date getDueDate() {
//        return dueDate;
//    }
//
//    public void setDueDate(Date dueDate) {
//        this.dueDate = dueDate;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public Book getBook() {
//        return book;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//    }
//
//    public Student getUser() {
//        return student;
//    }
//
//    public void setUser(Student student) {
//        this.student = student;
//    }
//
//    @Override
//    public String toString() {
//        return "IssuedBook{" +
//                "issueId=" + issueId +
//                ", bookId=" + bookId +
//                ", userId=" + userId +
//                ", issueDate=" + issueDate +
//                ", returnDate=" + returnDate +
//                ", dueDate=" + dueDate +
//                ", status='" + status + '\'' +
//                '}';
//    }
//} 