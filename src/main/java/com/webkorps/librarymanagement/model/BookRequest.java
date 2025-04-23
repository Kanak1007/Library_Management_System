/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webkorps.librarymanagement.model;

import java.sql.Date;

/**
 *
 * @author kanak
 */
public class BookRequest {

    private int bookId;
    private int studentId;
    private Date requestDate;
    private String status;
    private int requestId;

    // Constructors
    public BookRequest() {}

    public BookRequest(int bookId, int studentId, Date requestDate, String status) {
        this.bookId = bookId;
        this.studentId = studentId;
        this.requestDate = requestDate;
        this.status = status;
    }
  public int getRequestId(){
      return  requestId;
  }
  public void setRequestId(int RequestId){
      this.requestId=RequestId;
  }
    // Getters and Setters
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

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString (optional)
    @Override
    public String toString() {
        return "BookRequest{" +
                "bookId=" + bookId +
                ", studentId=" + studentId +
                ", requestDate=" + requestDate +
                ", status='" + status + '\'' +
                '}';
    }
}

