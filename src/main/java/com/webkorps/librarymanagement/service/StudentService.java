/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webkorps.librarymanagement.service;

import com.webkorps.librarymanagement.model.Student;
import com.webkorps.librarymanagement.dao.StudentDao;
import java.sql.SQLException;

public class StudentService {
    private StudentDao studentdao;
    
     public StudentService() {
        this.studentdao = new StudentDao();
    }
    public boolean registerStudent(Student student) {
      return  studentdao.registerStudent(student);  
    }
    public Student loginStudent(int id,String password){
        
        
        return studentdao.loginStudentbyIdandPass(id,password);
        
    }
}
