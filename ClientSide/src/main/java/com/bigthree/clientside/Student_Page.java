package com.bigthree.clientside;

import com.bigthree.objects.Courses;
import com.bigthree.objects.Enrolled;
import com.bigthree.objects.Student;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import serverConnection.ServerConnection;

public class Student_Page extends JFrame {

    private final JTabbedPane main;
    private final JPanel left;
    private final JPanel right;
    private final JList allCourses;
    private final JList allEnrolled;
    private final DefaultListModel dmlCourses;
    private final DefaultListModel dmlEnrolled;
    private final JButton btnRegister;
    private final JButton btnExit;

    private final Student loggedin;
    private ServerConnection con;

    public Student_Page(Student stud, ServerConnection con) throws IOException, ClassNotFoundException {

        this.con = con;
        loggedin = stud;
        System.out.println(loggedin.toString());
        this.setTitle("Student Page");
        this.setLayout(new BorderLayout());

        main = new JTabbedPane();
        left = new JPanel();
        right = new JPanel();

        dmlCourses = new DefaultListModel();
        dmlEnrolled = new DefaultListModel();

        allCourses = new JList(dmlCourses);
        allEnrolled = new JList(dmlEnrolled);
        allEnrolled.setEnabled(false);

        btnRegister = new JButton("Register");
        btnExit = new JButton("Exit");

        left.setLayout(new BorderLayout());
        right.setLayout(new BorderLayout());

        left.add(allCourses, BorderLayout.CENTER);
        left.add(btnRegister, BorderLayout.SOUTH);

        right.add(allEnrolled, BorderLayout.CENTER);
        right.add(btnExit, BorderLayout.SOUTH);

        main.add("All Courses", left);
        main.add("Enrolled Courses", right);

        left.setBorder(new EmptyBorder(20, 20, 20, 20));
        right.setBorder(new EmptyBorder(20, 20, 20, 20));
        main.setBorder(new EmptyBorder(30, 30, 30, 30));

        this.add(main, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(500, 350);
        this.setLocationRelativeTo(null);

        ArrayList<Courses> display = con.getCourses();
        if(display == null){
            JOptionPane.showMessageDialog(null, "Currently no Courses.");
        }else{
            for(var i:display){
                dmlCourses.addElement(i.toString());
            }
        }
        
        ArrayList<Enrolled> displayEn = con.getEnrolled();
        if(displayEn == null){
            JOptionPane.showMessageDialog(null, "No enrolled Courses.");
        }else{
            for(var i:displayEn){
                dmlEnrolled.addElement(i.toString());
            }
        }

    }

}
