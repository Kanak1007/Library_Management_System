package com.webkorps.librarymanagement.model;

import java.sql.Date;

public class IssueBook {
    private int issueId;
    private int bookId;
    private int studentId;
    private Date issueDate;
    private Date returnDate;
    private String status;
//    private Book book;
//    private Student student;
   


    // Default constructor
    public IssueBook() {
    }

    // Constructor for creating new issue with return date
    public IssueBook(int bookId, int studentId, Date issueDate, Date returnDate) {
        this.bookId = bookId;
        this.studentId = studentId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.status = "ISSUED";
    }

    // Getters and Setters
    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return '}' +
              
                "IssueBook{" +
                "issueId=" + issueId +
                ", bookId=" + bookId +
                ", studentId=" + studentId +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'';
    }
} 