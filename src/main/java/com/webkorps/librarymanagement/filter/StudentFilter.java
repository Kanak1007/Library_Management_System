/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webkorps.librarymanagement.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {
    "/studentdashboard.jsp",
    "/requestbooks.jsp",
    "/returnbooks.jsp",
    "/viewbooksforstudent.jsp",
    "/issuebook.jsp",
    "/issuedbooks.jsp",
    "/studentnav.jsp",
    "/viewbooksforstudent"
        
})
public class StudentFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
  
              // Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies
        
        if (session == null || !"student".equals(session.getAttribute("userrole"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        chain.doFilter(request, response);
    }


}