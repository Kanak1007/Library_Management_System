/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webkorps.librarymanagement.utility;



public class EmailValidator {

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // Basic email format validation using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                          "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        
        return email.matches(emailRegex);
    }

    public static String getEmailRequirements() {
        return "Email must be in a valid format (e.g., user@example.com) and contain:"
             + "\n- Local part (before @) with alphanumeric characters and certain special chars"
             + "\n- @ symbol"
             + "\n- Domain part (after @) with at least one dot"
             + "\n- Top-level domain (2-7 characters)";
    }
}