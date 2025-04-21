/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webkorps.librarymanagement.service;

import com.webkorps.librarymanagement.dao.BookDao;
import com.webkorps.librarymanagement.model.Book;
import java.util.List;



public class BookService {
 private BookDao bookdao;
    
    public BookService(){
       this. bookdao=new  BookDao();
   
}
    public boolean AddBook(Book book){
    return bookdao .AddBook(book);
    }
    public boolean DeleteBook(int bookId){
        return bookdao.DeleteBook(bookId);
    }
    public boolean UpdateBook(Book book,int bookId){
        return bookdao.UpdateBook(book,bookId);
    }
    public List<Book> getAllBooks(){
        return bookdao.getAllBooks();
    }
   public Book getBookById(int bookId) {
        return bookdao.getBookById(bookId);
    }
    public List<Book> searchBooks(String searchTerm) {
        return bookdao.searchBooks(searchTerm);
    }
}