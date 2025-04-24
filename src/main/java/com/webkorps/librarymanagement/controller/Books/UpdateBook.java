/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.webkorps.librarymanagement.controller.Books;

import com.webkorps.librarymanagement.dao.BookDao;
import com.webkorps.librarymanagement.model.Book;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 *
 * @author kanak
 */
@WebServlet(name = "UpdateBook", urlPatterns = {"/UpdateBook"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,  // 1MB
    maxFileSize = 1024 * 1024 * 10,   // 10MB
    maxRequestSize = 1024 * 1024 * 50  // 50MB
)
public class UpdateBook extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "images/books";
    private static final String DEFAULT_IMAGE = "images/books/library-hero.jpg";
    private BookDao bookDao;

    @Override
    public void init() throws ServletException {
        this.bookDao = new BookDao();
        // Create upload directory if it doesn't exist
        try {
            String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
                System.out.println("Created upload directory: " + uploadDir);
            }
        } catch (IOException e) {
            System.err.println("Error creating upload directory: " + e.getMessage());
        }
    }

    private String getSubmittedFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bookId = Integer.parseInt(request.getParameter("book_id"));
            String name = request.getParameter("name");
            String author = request.getParameter("author");
            String edition = request.getParameter("edition");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // Handle image upload
            String imagePath = request.getParameter("current_image"); // Default to current image
            Part filePart = request.getPart("bookImage");
            
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getSubmittedFileName(filePart);
                if (fileName != null && !fileName.isEmpty()) {
                    try {
                        // Generate unique filename to avoid conflicts
                        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                        
                        // Save to source directory
                        String projectRoot = new File(getServletContext().getRealPath("")).getParentFile().getParentFile().getPath();
                        String sourceUploadPath = projectRoot + "/src/main/webapp/" + UPLOAD_DIRECTORY;
                        Path sourceFilePath = Paths.get(sourceUploadPath, uniqueFileName);
                        
                        // Ensure source directory exists
                        Files.createDirectories(sourceFilePath.getParent());
                        
                        // Save to source directory
                        try (InputStream input = filePart.getInputStream()) {
                            Files.copy(input, sourceFilePath, StandardCopyOption.REPLACE_EXISTING);
                            // Store the relative path in the database
                            imagePath = UPLOAD_DIRECTORY + "/" + uniqueFileName;
                            System.out.println("Image saved to source: " + sourceFilePath);
                            
                            // Also save to target directory for immediate use
                            String targetUploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
                            Path targetFilePath = Paths.get(targetUploadPath, uniqueFileName);
                            Files.createDirectories(targetFilePath.getParent());
                            Files.copy(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Image copied to target: " + targetFilePath);
                        }
                    } catch (IOException e) {
                        System.err.println("Error uploading image: " + e.getMessage());
                        e.printStackTrace();
                        request.setAttribute("error", "Failed to upload image. Using current image.");
                    }
                }
            }
            
            // Create book object with all parameters including image path
            Book book = new Book(name, author, edition, quantity, imagePath);
            
            boolean updateBook = bookDao.UpdateBook(book, bookId);
            
            if (updateBook) {
                request.setAttribute("success", "Book updated successfully!");
                response.sendRedirect(request.getContextPath() + "/GetAllBooksForAdmin");
            } else {
                request.setAttribute("error", "Failed to update book. Please try again.");
                request.getRequestDispatcher("updatebook.jsp").forward(request, response);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid quantity format");
            request.getRequestDispatcher("updatebook.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error updating book: " + e.getMessage());
            request.getRequestDispatcher("updatebook.jsp").forward(request, response);
        }
    }
}
        