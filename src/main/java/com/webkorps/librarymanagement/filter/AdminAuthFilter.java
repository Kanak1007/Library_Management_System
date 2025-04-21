//package com.webkorps.librarymanagement.filter;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter("/*")
//public class AdminAuthFilter implements Filter {
//    
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        HttpSession session = httpRequest.getSession(false);
//        
//        String path = httpRequest.getRequestURI();
//        System.out.println("Filter triggered for path: " + path);
//        
//        // Allow access to public resources and login-related paths
//        if (isPublicResource(path)) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // Check for admin-specific resources
//        if (isAdminResource(path)) {
//            if (session == null || session.getAttribute("admin") == null) {
//                httpResponse.sendRedirect(httpRequest.getContextPath() + "/adminlogin.jsp");
//                return;
//            }
//        }
//        
//        // For all other resources, require user login
//        if (path.endsWith(".jsp") && (session == null || (session.getAttribute("admin") == null && session.getAttribute("user") == null))) {
//            httpResponse.sendRedirect(httpRequest.getContextPath() + "/userlogin.jsp");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    private boolean isPublicResource(String path) {
//        return path.endsWith("index.jsp") ||
//               path.endsWith("adminlogin.jsp") ||
//               path.endsWith("userlogin.jsp") ||
//               path.endsWith("register.jsp") ||
//               path.endsWith("adminregister.jsp") ||
//               path.endsWith("error.jsp") ||
//               path.endsWith("AdminLogin") ||
//               path.endsWith("AdminRegister") ||
//               path.endsWith("UserLogin") ||
//               path.endsWith("UserRegister") ||
//               path.contains("/css/") || 
//               path.contains("/js/") || 
//               path.contains("/images/");
//    }
//
//    private boolean isAdminResource(String path) {
//        return path.contains("/admin/") ||
//               path.endsWith("addbook.jsp") ||
//               path.endsWith("updatebook.jsp") ||
//               path.endsWith("viewbooks.jsp") ||
//               path.endsWith("AddBook") ||
//               path.endsWith("UpdateBook") ||
//               path.endsWith("RemoveBook") ||
//               path.endsWith("GetAllBooks") ||
//               path.endsWith("SearchBooks") ||
//               path.endsWith("FindBookToUpdate") ||
//               path.endsWith("AdminDashboardServlet");
//    }
//
//    @Override
//    public void destroy() {
//    }
//} 
