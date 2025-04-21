package com.webkorps.librarymanagement.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"*.jsp"})
public class JspProtectionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String requestURI = httpRequest.getRequestURI();
        
        // Allow access to login pages
        if (requestURI.endsWith("/index.jsp") || 
            requestURI.endsWith("/adminlogin.jsp") || 
            requestURI.endsWith("/userlogin.jsp") || 
            requestURI.endsWith("/register.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Check if user is logged in
        boolean isLoggedIn = (session != null && 
                           (session.getAttribute("admin") != null || 
                            session.getAttribute("user") != null));
        
        if (!isLoggedIn) {
            // If trying to access admin pages
            if (requestURI.contains("/admin/") || 
                requestURI.contains("addbook.jsp") || 
                requestURI.contains("updatebook.jsp") || 
                requestURI.contains("viewbooks.jsp")) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/adminlogin.jsp");
            } else {
                // For other protected pages
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/userlogin.jsp");
            }
            return;
        }
        
        // For admin-specific pages, check if user has admin access
        if ((requestURI.contains("/admin/") || 
             requestURI.contains("addbook.jsp") || 
             requestURI.contains("updatebook.jsp") || 
             requestURI.contains("viewbooks.jsp")) && 
            session.getAttribute("admin") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/adminlogin.jsp");
            return;
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
} 