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
        String Student_name = request.getParameter("studentName");
        String Student_email = request.getParameter("studentEmail");
        String Student_role = request.getParameter("studentRole");
        String Student_password = request.getParameter("studentPassword");
          if (!PasswordValidator.isValidPassword(Student_password)) {
            request.setAttribute("error", PasswordValidator.getPasswordRequirements());
            RequestDispatcher dispatcher = request.getRequestDispatcher("studentregister.jsp");
            dispatcher.forward(request, response);
            return;
        }
        Student stu=new Student(Student_name,Student_email,Student_role,Student_password);
// Student stu=new Student("kanak","kanakagrawal437@gmail.com","student","kanak");
         boolean isRegistered=studentService.registerStudent(stu);
           if (isRegistered) {
            response.sendRedirect("studentlogin.jsp"); // Redirect to success page after registration
        } else {
            response.sendRedirect("error.jsp"); // Redirect to error page if registration fails
        }
    }

}
