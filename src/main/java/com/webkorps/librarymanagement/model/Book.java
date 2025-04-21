/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webkorps.librarymanagement.model;

/**
 *
 * @author kanak
 */
public class Book {
    private int bookId;
    private String bookName;
    private String bookAuthor;
    private String bookEdition;
    private int bookQuantity;
    private String imagePath;

    public Book() {
        this.imagePath = "images/books/library-hero.jpg"; // Default image path
    }

    public Book(String bookName, String bookAuthor, String bookEdition, int bookQuantity, String imagePath) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookEdition = bookEdition;
        this.bookQuantity = bookQuantity;
        this.imagePath = imagePath != null ? imagePath : "images/books/library-hero.jpg";
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath != null ? imagePath : "images/books/library-hero.jpg";
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookEdition='" + bookEdition + '\'' +
                ", bookQuantity=" + bookQuantity +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
