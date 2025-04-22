/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.webkorps.librarymanagement.controller.Student;

import com.webkorps.librarymanagement.model.Student;
import com.webkorps.librarymanagement.service.StudentService;
import com.webkorps.librarymanagement.utility.PasswordValidator;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author kanak
 */
@WebServlet(name = "StudentRegister", urlPatterns = {"/StudentRegister"})
public class StudentRegister extends HttpServlet {
    private StudentService studentService;
//doubt waala
//    @Override
//    public void init() throws ServletException {
//     StudentService studentService=new StudentService();
//     
//    }
        @Override
    public void init() throws ServletException {
      studentService=new StudentService();
     
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            RequestDispatcher dispatcher = request.getRequestDispatcher("studentregister.jsp");
            dispatcher.forward(request, response);
            return;
        }
            if (!PasswordValidator.isValidPassword(password)) {
            request.setAttribute("error", PasswordValidator.getPasswordRequirements());
            RequestDispatcher dispatcher = request.getRequestDispatcher("studentregister.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Student student = new Student(name, email, role, password);
        
          try {
            // Call the service to handle the registration logic
            boolean isRegistered = studentService.registerStudent(student); // Assuming your service method is named registerAdmin now

            if (isRegistered) {
                response.sendRedirect("studentlogin.jsp"); // Redirect to login page after registration
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("studentregister.jsp");
                dispatcher.forward(request, response);
            }
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage()); // Directly set the message from the RuntimeException
            RequestDispatcher dispatcher = request.getRequestDispatcher("studentregister.jsp");
            dispatcher.forward(request, response);
        }

        
    }

}
