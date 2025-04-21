package com.webkorps.librarymanagement.controller.Admin;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import com.webkorps.librarymanagement.service.AdminService;
import com.webkorps.librarymanagement.dao.AdminDao;
import com.webkorps.librarymanagement.utility.DBconnection;
import com.webkorps.librarymanagement.utility.PasswordValidator;
import com.webkorps.librarymanagement.model.*;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/registerAdminServlet")
public class AdminRegister extends HttpServlet {
    private AdminService adminService;

    @Override
    public void init() throws ServletException {
     
            adminService = new AdminService();
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminName = request.getParameter("adminName");
        String libraryName = request.getParameter("libraryName");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String password = request.getParameter("password");

        // Validate password using utility class
        if (!PasswordValidator.isValidPassword(password)) {
            request.setAttribute("error", PasswordValidator.getPasswordRequirements());
            RequestDispatcher dispatcher = request.getRequestDispatcher("adminregister.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Admin admin = new Admin(adminName, libraryName, address, email, role, password);

        // Call the service to handle the registration logic
        boolean isRegistered = adminService.register(admin);

        if (isRegistered) {
            response.sendRedirect("adminlogin.jsp"); // Redirect to login page after registration
        } else {
            // If registration fails, it could be due to duplicate email
            request.setAttribute("error", "Email already exists. Please login with your credentials.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("adminlogin.jsp");
            dispatcher.forward(request, response);
        }
    }
}