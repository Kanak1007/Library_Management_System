package com.webkorps.librarymanagement.model;

public class Admin {

    private String adminName;
    private String adminLibraryName;
    private String adminAddress;
    private String adminEmail;
    private String adminRole;
    private String adminPassword;
     private int membershipId;

       // Default constructor (no-arg)
    public Admin() {
    }

    // Constructor without membershipId (for registration use)
    public Admin(String adminName, String adminLibraryName, String adminAddress,
                 String adminEmail, String adminRole, String adminPassword) {
        this.adminName = adminName;
        this.adminLibraryName = adminLibraryName;
        this.adminAddress = adminAddress;
        this.adminEmail = adminEmail;
        this.adminRole = adminRole;
        this.adminPassword = adminPassword;
    }
    // Setters
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setAdminLibraryName(String adminLibraryName) {
        this.adminLibraryName = adminLibraryName;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
       public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    // Getters
    public String getAdminName() {
        return adminName;
    }

    public String getAdminLibraryName() {
        return adminLibraryName;
    }

    public String getAdminAddress() {
        return adminAddress;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getAdminRole() {
        return adminRole;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
    public int getMembershipId() {
        return membershipId;
    }



    @Override
    public String toString() {
        return "Admin Name: " + adminName + ", " +
               "Library Name: " + adminLibraryName + ", " +
               "Address: " + adminAddress + ", " +
               "Email: " + adminEmail + ", " +
               "Role: " + adminRole;
    }
}
