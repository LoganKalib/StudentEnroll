package com.bigthree.clientside;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Student_Page extends JFrame {

    private JTabbedPane main;
    private JPanel left, right;
    private JList allCourses, allEnrolled;
    private DefaultListModel dmlCourses, dmlEnrolled;
    private JButton btnRegister, btnExit;

    public Student_Page() {
        this.setTitle("Student Page");
        this.setLayout(new BorderLayout());

        main = new JTabbedPane();
        left = new JPanel();
        right = new JPanel();

        allCourses = new JList();
        allEnrolled = new JList();
        allEnrolled.setEnabled(false);

        dmlCourses = new DefaultListModel();
        dmlEnrolled = new DefaultListModel();

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

    }
}
