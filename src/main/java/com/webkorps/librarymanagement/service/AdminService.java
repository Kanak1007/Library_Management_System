/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webkorps.librarymanagement.service;

import com.webkorps.librarymanagement.dao.AdminDao;
import com.webkorps.librarymanagement.model.Admin;
import com.webkorps.librarymanagement.utility.DBconnection;

public class AdminService {
    private AdminDao adminDao;
    
    public AdminService() {
        this.adminDao = new AdminDao();
    }
    
    public Admin login(int membershipId, String password) {
        return adminDao. getAdminByMembershipIdAndPassword(membershipId, password);
    }
    
    public boolean register(Admin admin) {
        return adminDao.registerAdmin(admin);
    }
}
