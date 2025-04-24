/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.webkorps.librarymanagement.controller.Student;

import com.webkorps.librarymanagement.model.Student;
import com.webkorps.librarymanagement.service.AdminService;
import com.webkorps.librarymanagement.service.StudentService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "StudentLogin", urlPatterns = {"/StudentLogin"})
public class StudentLogin extends HttpServlet {
    private StudentService studentservice;
   
    @Override
    public void init() throws ServletException {
      this.studentservice=new  StudentService();
    }

    
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int Id=Integer.parseInt(request.getParameter("membershipId"));
        String Password=request.getParameter("password");
        Student stu=studentservice.loginStudent(Id,Password);
 
        if(stu==null){
            request.setAttribute("error","Invalid membershipid and password");
            RequestDispatcher rd=request.getRequestDispatcher("studentlogin.jsp");
            rd.forward(request, response);
        }
        else{
            HttpSession session= request.getSession();  
            session.setAttribute("name",stu.getName());
            session.setAttribute("role",stu.getRole());
            session.setAttribute("email",stu.getEmail());
            session.setAttribute("password",stu.getPassword());
            session.setAttribute("studentid", stu.getMembershipId());
                session.setAttribute("userrole", "student");
            session.setAttribute("student",stu);
            response.sendRedirect("StudentDashboardData");
        }
        
        
    }


}
