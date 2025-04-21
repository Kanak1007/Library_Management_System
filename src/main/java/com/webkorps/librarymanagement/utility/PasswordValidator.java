package com.webkorps.librarymanagement.utility;

public class PasswordValidator {
    
    public static boolean isValidPassword(String password) {
        // Check if password is at least 8 characters long
        if (password.length() < 8) return false;

        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) return false;

        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*].*")) return false;

        return true;
    }

    public static String getPasswordRequirements() {
        return "Password must contain at least one uppercase letter, one special character (!@#$%^&*), and be at least 8 characters long.";
    }
} 