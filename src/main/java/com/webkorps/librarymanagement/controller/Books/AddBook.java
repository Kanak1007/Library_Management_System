/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.webkorps.librarymanagement.controller.Books;

import com.webkorps.librarymanagement.model.Book;
import com.webkorps.librarymanagement.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author kanak
 */
@WebServlet(name = "AddBook", urlPatterns = {"/AddBook"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,  // 1MB
    maxFileSize = 1024 * 1024 * 10,   // 10MB
    maxRequestSize = 1024 * 1024 * 50  // 50MB
)
public class AddBook extends HttpServlet {
    private BookService bookservice;
    private static final String UPLOAD_DIRECTORY = "images/books";
    private static final String DEFAULT_IMAGE = "images/books/library-hero.jpg";
    
    @Override
    public void init() throws ServletException {
        this.bookservice = new BookService();
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String bookName = request.getParameter("bookname");
        String bookAuthor = request.getParameter("bookauthor");
        String bookEdition = request.getParameter("bookedition");
        int bookQuantity = Integer.parseInt(request.getParameter("bookquantity"));
        
        // Handle image upload
        String imagePath = DEFAULT_IMAGE;
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
                    request.setAttribute("error", "Failed to upload image. Using default image.");
                }
            }
        }
        
        // Create book with image path
        Book book = new Book(bookName, bookAuthor, bookEdition, bookQuantity, imagePath);
        
        boolean isRegistered = bookservice.AddBook(book);
        if (isRegistered) {
            if (book.getBookId() == 0) {
                request.getSession().setAttribute("success", "Book added successfully!");
            } else {
                request.getSession().setAttribute("success", "Book quantity has been updated successfully!");
            }
            response.sendRedirect(request.getContextPath() + "/AdminDashboardServlet");
        } else {
            request.setAttribute("error", "Failed to process book. Please try again.");
            request.getRequestDispatcher("addbook.jsp").forward(request, response);
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
}
