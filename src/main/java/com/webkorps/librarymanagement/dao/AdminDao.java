/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webkorps.librarymanagement.dao;

import com.webkorps.librarymanagement.model.Admin;
import com.webkorps.librarymanagement.utility.DBconnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

public class AdminDao {

    private Connection con;

    public AdminDao(Connection con) {
        this.con = con;
    }

  

    public boolean registerAdmin(Admin admin) {
        String query = "INSERT INTO admin(membership_id, admin_name, password, admin_role) VALUES(?,?,?,?)";
            try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query) ){
            ps.setInt(1, admin.getMembershipId());
            ps.setString(2, admin.getAdminName());
            ps.setString(3, admin.getAdminPassword());
            ps.setString(4, admin.getAdminRole());
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Admin getAdminByMembershipIdAndPassword(int membershipId, String password) {
        Admin admin = null;
        String sql = "SELECT * FROM admin WHERE membership_id = ? AND password = ?";

        try (Connection connn = DBconnection.getConnection();
             PreparedStatement ps = connn.prepareStatement(sql) ){
            ps.setInt(1, membershipId);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    admin = new Admin();
                    admin.setMembershipId(rs.getInt("membership_id"));
                    admin.setAdminName(rs.getString("name"));
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
