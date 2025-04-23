package com.webkorps.librarymanagement.dao;

import com.webkorps.librarymanagement.model.Book;
import com.webkorps.librarymanagement.utility.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kanak
 */
public class BookDao {

    private Book findDuplicateBook(Book book) {
        String query = "SELECT * FROM book WHERE name=? AND author=? AND edition=?";
        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, book.getBookName());
            ps.setString(2, book.getBookAuthor());
            ps.setString(3, book.getBookEdition());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book existingBook = new Book(
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("edition"),
                        rs.getInt("quantity"),
                        rs.getString("image_path")
                    );
                    existingBook.setBookId(rs.getInt("book_id"));
                    return existingBook;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean AddBook(Book book) {
        // First check if a duplicate book exists
        Book existingBook = findDuplicateBook(book);
        if (existingBook != null) {
            // Update the quantity of the existing book
            String updateQuery = "UPDATE book SET quantity = quantity + ? WHERE book_id = ?";
            try (Connection con = DBconnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(updateQuery)) {
                ps.setInt(1, book.getBookQuantity());
                ps.setInt(2, existingBook.getBookId());
                
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        // If no duplicate exists, proceed with inserting new book
        String Query = "INSERT INTO book(name,author,edition,quantity,image_path) VALUES(?,?,?,?,?)";
        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getBookName());
            ps.setString(2, book.getBookAuthor());
            ps.setString(3, book.getBookEdition());
            ps.setInt(4, book.getBookQuantity());
            ps.setString(5, book.getImagePath());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        book.setBookId(generatedId);
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean DeleteBook(int book_id) {

        String Query = "DELETE FROM  book where book_id=?";
        try (Connection con = DBconnection.getConnection(); PreparedStatement ps = con.prepareStatement(Query)) {
            ps.setInt(1, book_id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean UpdateBook(Book book, int bookId) {
        String query = "UPDATE book SET name=?, author=?, edition=?, quantity=?, image_path=? WHERE book_id=?";
        try (Connection connection = DBconnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, book.getBookName());
            statement.setString(2, book.getBookAuthor());
            statement.setString(3, book.getBookEdition());
            statement.setInt(4, book.getBookQuantity());
            statement.setString(5, book.getImagePath());
            statement.setInt(6, bookId);

            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book";
        try (Connection connection = DBconnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                String name = set.getString("name");
                String author = set.getString("author");
                String edition = set.getString("edition");
                int quantity = set.getInt("quantity");
                int id = set.getInt("book_id");
                String imagePath = set.getString("image_path");
                Book book=new Book(name,author,edition,quantity,imagePath);
                book.setBookId(id);
                books.add(book);
            }
            return books;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book getBookById(int bookId) {
        String query = "SELECT * FROM book WHERE book_id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, bookId);
            
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    String name = set.getString("name");
                    String author = set.getString("author");
                    String edition = set.getString("edition");
                    int quantity = set.getInt("quantity");
                    String imagePath = set.getString("image_path");
                    
                    Book book = new Book(name, author, edition, quantity,imagePath);
                    book.setBookId(bookId);
                    return book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getTotalBooks() {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM book";
        System.out.println("Executing SQL query: " + sql); // Debug log

        try (Connection conn = DBconnection.getConnection()) {
            System.out.println("Database connection successful: " + (conn != null)); // Debug log
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        total = rs.getInt(1);
                        System.out.println("Total books from database: " + total); // Debug log
                    } else {
                        System.out.println("No results returned from query"); // Debug log
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in getTotalBooks: " + e.getMessage()); // Debug log
            e.printStackTrace();
        }
        return total;
    }

    public int getIssuedBooks() {
        int issued = 0;
        String sql = "SELECT COUNT(*) FROM issued_books WHERE return_date IS NULL";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                issued = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issued;
    }

    public List<Book> searchBooks(String searchTerm) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book WHERE name LIKE ? OR author LIKE ? OR edition LIKE ?";
        
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    String name = set.getString("name");
                    String author = set.getString("author");
                    String edition = set.getString("edition");
                    int quantity = set.getInt("quantity");
                    int id = set.getInt("book_id");
                    String imagePath = set.getString("image_path");
                    
                    Book book = new Book(name, author, edition, quantity,imagePath);
                    book.setBookId(id);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getAvailableBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book WHERE quantity > 0";
        
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                String name = set.getString("name");
                String author = set.getString("author");
                String edition = set.getString("edition");
                int quantity = set.getInt("quantity");
                int id = set.getInt("book_id");
                String imagePath = set.getString("image_path");
                
                Book book = new Book(name, author, edition, quantity, imagePath);
                book.setBookId(id);
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public int getAvailableBookCount() {
        String sql = "SELECT COUNT(*) FROM book WHERE quantity > 0";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.out.println("BookDao: Error getting available book count - " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }

    public List<Book> getZeroQuantityBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book WHERE quantity = 0";
        
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                String name = set.getString("name");
                String author = set.getString("author");
                String edition = set.getString("edition");
                int quantity = set.getInt("quantity");
                int id = set.getInt("book_id");
                String imagePath = set.getString("image_path");
                
                Book book = new Book(name, author, edition, quantity, imagePath);
                book.setBookId(id);
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("BookDao: Error getting zero quantity books - " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

}
