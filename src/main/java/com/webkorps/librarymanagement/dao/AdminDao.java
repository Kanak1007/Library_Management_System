/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webkorps.librarymanagement.dao;

import com.webkorps.librarymanagement.model.Admin;
import com.webkorps.librarymanagement.utility.DBconnection;
import com.webkorps.librarymanagement.utility.SendEmail;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminDao {



//    public boolean registerAdmin(Admin admin) {
//        String query = "INSERT INTO admin(name, library_name, address, email, role, password) VALUES(?,?,?,?,?,?)";
//        try (Connection con = DBconnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            
//            ps.setString(1, admin.getAdminName());
//            ps.setString(2, admin.getAdminLibraryName());
//            ps.setString(3, admin.getAdminAddress());
//            ps.setString(4, admin.getAdminEmail());
//            ps.setString(5, admin.getAdminRole());
//            ps.setString(6, admin.getAdminPassword());
//            
//            int rows = ps.executeUpdate();
//              
//            if (rows > 0) {
//                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        
//                        int generatedId = generatedKeys.getInt(1);
//                        admin.setMembershipId(generatedId);
//                            String subject = "Library Membership Registration Successful";
//                        String content = "Hi " +  admin.getAdminName() + ",\n\n"
//                                + "Your registration is successful. Your Membership ID is: " + generatedId + "\n\n"
//                                + "Regards,\nLibrary Management System";
//
//                        try {
//                            SendEmail.sendEmail(admin.getAdminEmail(), subject, content);
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//                return true;
//            }
//        } catch (SQLException e) {
//            System.err.println("SQL Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return false;
//    }
    public boolean registerAdmin(Admin admin) {
     String query = "INSERT INTO admin(name, library_name, address, email, role, password) VALUES(?,?,?,?,?,?)";
     try (Connection con = DBconnection.getConnection();
          PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

         ps.setString(1, admin.getAdminName());
         ps.setString(2, admin.getAdminLibraryName());
         ps.setString(3, admin.getAdminAddress());
         ps.setString(4, admin.getAdminEmail());
         ps.setString(5, admin.getAdminRole());
         ps.setString(6, admin.getAdminPassword());

         int rows = ps.executeUpdate();

         if (rows > 0) {
             try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                 if (generatedKeys.next()) {
                     int generatedId = generatedKeys.getInt(1);
                     admin.setMembershipId(generatedId);
                     String subject = "Library Membership Registration Successful";
                     String content = "Hi " +  admin.getAdminName() + ",\n\n"
                             + "Your registration is successful. Your Membership ID is: " + generatedId + "\n\n"
                             + "Regards,\nLibrary Management System";

                     try {
                         SendEmail.sendEmail(admin.getAdminEmail(), subject, content);
                     } catch (Exception e) {
                         throw new RuntimeException(e);
                     }
                 }
             }
             return true;
         }
     } 
     catch (SQLException e) {
         System.err.println("SQL Error: " + e.getMessage());
         e.printStackTrace();
         // Check if the exception is due to a unique constraint violation (email)
         if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key") && e.getMessage().contains("email")) {
             throw new RuntimeException("Email already exists");
         } else {
             throw new RuntimeException("Database error during registration: " + e.getMessage());
         }
     }
     return false;
 }

    public Admin getAdminByMembershipIdAndPassword(int membershipId, String password) {
        Admin admin = null;
        String sql = "SELECT * FROM admin WHERE membership_id = ? AND password = ?";

        try (Connection connn = DBconnection.getConnection();
             PreparedStatement ps = connn.prepareStatement(sql)) {
            ps.setInt(1, membershipId);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    admin = new Admin();
                    admin.setMembershipId(rs.getInt("membership_id"));
                    admin.setAdminName(rs.getString("name"));
                    admin.setAdminLibraryName(rs.getString("library_name"));
                    admin.setAdminAddress(rs.getString("address"));
                    admin.setAdminEmail(rs.getString("email"));
                    admin.setAdminRole(rs.getString("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admin;
    }

}
