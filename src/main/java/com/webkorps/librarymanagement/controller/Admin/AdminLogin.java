package com.webkorps.librarymanagement.controller.Admin;

import com.webkorps.librarymanagement.dao.AdminDao;
import com.webkorps.librarymanagement.model.Admin;
import com.webkorps.librarymanagement.service.AdminService;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author kanak
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {

    private AdminService adminService;

    @Override
    public void init() throws ServletException {
      
        this.adminService = new AdminService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int membershipId = Integer.parseInt(request.getParameter("membershipId"));
        String password = request.getParameter("password");

        Admin admin = adminService.login(membershipId, password);
        
        if (admin != null) {
            HttpSession session = request.getSession();
            session.setAttribute("adminname", admin.getAdminName()); // Store admin name in session
            session.setAttribute("adminId", admin.getMembershipId()); // Store admin ID in session
           session.setAttribute("admin", admin);  // This line is crucial

            response.sendRedirect("AdminDashboardServlet");
        } else {
            request.setAttribute("error", "Invalid Membership ID or Password");
            request.getRequestDispatcher("adminlogin.jsp").forward(request, response);
        }
    }

}
