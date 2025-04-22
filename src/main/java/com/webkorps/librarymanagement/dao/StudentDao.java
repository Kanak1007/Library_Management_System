package com.webkorps.librarymanagement.dao;

import com.webkorps.librarymanagement.model.Admin;
import com.webkorps.librarymanagement.model.Student;
import com.webkorps.librarymanagement.utility.DBconnection;
import com.webkorps.librarymanagement.utility.SendEmail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDao {

    public boolean registerStudent(Student student) {
        //Connection con=DBconnection.getConnection() ;
        String str = "INSERT INTO STUDENT(name,email,role,password) VALUES(?,?,?,?)";
        try (Connection con = DBconnection.getConnection(); PreparedStatement ps = con.prepareStatement(str, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getRole());
            ps.setString(4, student.getPassword());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet generatedKeys = (ResultSet) ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        student.setMembershipId(generatedId);
                        String subject = "Library Membership Registration Successful";
                        String content = "Hi " + student.getName() + ",\n\n"
                                + "Your registration is successful. Your Membership ID is: " + generatedId + "\n\n"
                                + "Regards,\nLibrary Management System";

                        try {
                            SendEmail.sendEmail(student.getEmail(), subject, content);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        return true;
                    }
                }

            }
        } catch (SQLException e) {
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

    public Student loginStudentbyIdandPass(int id, String password) {

        String query = "SELECT * FROM STUDENT WHERE membership_id=? and password=?";

        try (Connection conn = DBconnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Student stu = new Student();
                stu.setName(rs.getString("name"));
                stu.setEmail(rs.getString("email"));
                stu.setMembershipId(rs.getInt("membership_id"));
                stu.setRole(rs.getString("role"));
                stu.setPassword(rs.getString("password"));
                System.err.println("Student Login Check: " + stu.getEmail());
                return stu;
            }

        } catch (SQLException e) {
            System.out.println("there is some error here..");
        }
        return null;
    }

//    private final Connection con;
//
//    public StudentDao(Connection con) {
//        this.con = con;
//    }
//
//    // Register new student
//    public boolean registerStudent(Student student) {
//        String sql = "INSERT INTO student (name, email, role, password) VALUES (?, ?, ?, ?)";
//        
//        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            ps.setString(1, student.getName());
//            ps.setString(2, student.getEmail());
//            ps.setString(3, student.getRole());
//            ps.setString(4, student.getPassword());
//
//            int rows = ps.executeUpdate();
//            System.out.println("Rows affected: " + rows);
//
//            if (rows > 0) {
//                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        int generatedId = generatedKeys.getInt(1);
//                        student.setMembershipId(generatedId);
//                        
//                        // Send confirmation email after registration
//                        String subject = "Library Membership Registration Successful";
//                        String content = "Hi " + student.getName() + ",\n\n" +
//                                         "Your registration is successful. Your Membership ID is: " + generatedId + "\n\n" +
//                                         "Regards,\nLibrary Management System";
//
//                        // SendEmail.sendEmail(student.getEmail(), subject, content);  // Uncomment to enable email
//                        System.out.println("Email sent successfully.");
//                        return true;
//                    }
//                }
//            } else {
//                System.out.println("No rows affected during student registration.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // Get student by membership_id and password
//    public Student getStudentByMembershipIdAndPassword(int membershipId, String password) {
//        Student student = null;
//        String sql = "SELECT * FROM student WHERE membership_id = ? AND password = ?";
//
//        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, membershipId);
//            ps.setString(2, password);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    student = new Student();
//                    student.setMembershipId(rs.getInt("membership_id"));
//                    student.setName(rs.getString("name"));
//                    student.setEmail(rs.getString("email"));
//                    student.setRole(rs.getString("role"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return student;
//    }
//
//    // Get all students
//    public List<Student> getAllStudents() {
//        List<Student> students = new ArrayList<>();
//        String sql = "SELECT * FROM student";
//
//        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                Student student = new Student();
//                student.setMembershipId(rs.getInt("membership_id"));
//                student.setName(rs.getString("name"));
//                student.setEmail(rs.getString("email"));
//                student.setRole(rs.getString("role"));
//                students.add(student);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return students;
//    }
//
//    // Update student information
//    public boolean updateStudent(Student student) {
//        String sql = "UPDATE student SET name = ?, email = ?, role = ?, password = ? WHERE membership_id = ?";
//
//        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setString(1, student.getName());
//            ps.setString(2, student.getEmail());
//            ps.setString(3, student.getRole());
//            ps.setString(4, student.getPassword());
//            ps.setInt(5, student.getMembershipId());
//
//            int rowsUpdated = ps.executeUpdate();
//            return rowsUpdated > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

////
//        return false;
//    }
//
//    // Delete student by membership_id
//    public boolean deleteStudent(int membershipId) {
//        String sql = "DELETE FROM student WHERE membership_id = ?";
//
//        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, membershipId);
//            int rowsDeleted = ps.executeUpdate();
//            return rowsDeleted > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
}
