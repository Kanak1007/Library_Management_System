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
    "/admindashboard.jsp",
    "/addbook.jsp",
    "/removebook.jsp",
    "/updatebook.jsp",
    "/viewbooks.jsp",
    "/enter_book_id.jsp",
    "/navbar.jsp",
    "/viewissuedbooks.jsp"

})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if (session == null || !"admin".equals(session.getAttribute("userrole"))) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        
        chain.doFilter(request, response);
    }

   
}