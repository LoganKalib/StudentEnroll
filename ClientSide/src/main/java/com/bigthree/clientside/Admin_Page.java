package com.bigthree.clientside;

import com.bigthree.objects.Admin;
import com.bigthree.objects.Courses;
import com.bigthree.objects.Student;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import serverConnection.ServerConnection;

public class Admin_Page extends JFrame implements ActionListener {

    private JTabbedPane options;
    private JPanel pnlStud, pnlCourse, pnlDeleteStud;
    private JLabel lblStudName, lblStudSurname, lblStudNumber, lblStudPassword, lblCourseCode, lblCourseName, lblCoursePrice;
    private JTextField txtStudName, txtStudSurname, txtStudNumber, txtStudPassword, txtCourseCode, txtCourseName, txtCoursePrice;
    private JButton btnRegStud, btnRegCourse, btnExit, btnExitStud, btnDelete;
    private JList lstStuds;
    private DefaultListModel dmlStud;

    private Admin loggedin;
    private ServerConnection con;
    ArrayList<Student> studList;

    public Admin_Page(Admin admin, ServerConnection con) {

        loggedin = admin;
        this.con = con;

        this.setLayout(new BorderLayout());
        this.setTitle("Admin Page");

        options = new JTabbedPane();
        pnlStud = new JPanel();
        pnlCourse = new JPanel();
        pnlDeleteStud = new JPanel();
        
        lstStuds = new JList();
        
        try {
            populateStuds();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Admin_Page.class.getName()).log(Level.SEVERE, null, ex);
        }

        lblStudName = new JLabel("Name: ");
        lblStudSurname = new JLabel("Surname: ");
        lblStudNumber = new JLabel("Student Number: ");
        lblStudPassword = new JLabel("Password: ");
        lblCourseCode = new JLabel("Course Code: ");
        lblCourseName = new JLabel("Course Name: ");
        lblCoursePrice = new JLabel("Course Price: ");

        txtStudName = new JTextField(20);
        txtStudSurname = new JTextField(20);
        txtStudNumber = new JTextField(20);
        txtStudPassword = new JTextField(20);
        txtCourseCode = new JTextField(20);
        txtCourseName = new JTextField(20);
        txtCoursePrice = new JTextField(20);

        btnRegStud = new JButton("Register student");
        btnRegCourse = new JButton("Register Course");
        btnDelete = new JButton("Delete Student");
        btnExit = new JButton("Exit");
        btnExitStud = new JButton("Exit");

        options.add("Create Student", pnlStud);
        options.add("Create Course", pnlCourse);
        options.add("Delete Student", pnlDeleteStud);

        pnlStud.setLayout(new GridLayout(5, 2));

        pnlCourse.setLayout(new GridLayout(4, 2));
        
        pnlDeleteStud.setLayout(new BorderLayout());

        pnlStud.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnlCourse.setBorder(new EmptyBorder(20, 20, 20, 20));
        pnlDeleteStud.setBorder(new EmptyBorder(20, 20, 20, 20));
        options.setBorder(new EmptyBorder(30, 30, 30, 30));

        pnlStud.add(lblStudName);
        pnlStud.add(txtStudName);
        pnlStud.add(lblStudSurname);
        pnlStud.add(txtStudSurname);
        pnlStud.add(lblStudNumber);
        pnlStud.add(txtStudNumber);
        pnlStud.add(lblStudPassword);
        pnlStud.add(txtStudPassword);
        pnlStud.add(btnRegStud);
        pnlStud.add(btnExitStud);

        pnlCourse.add(lblCourseCode);
        pnlCourse.add(txtCourseCode);
        pnlCourse.add(lblCourseName);
        pnlCourse.add(txtCourseName);
        pnlCourse.add(lblCoursePrice);
        pnlCourse.add(txtCoursePrice);
        pnlCourse.add(btnRegCourse);
        pnlCourse.add(btnExit);
        
        pnlDeleteStud.add(lstStuds, BorderLayout.CENTER);
        pnlDeleteStud.add(btnDelete, BorderLayout.SOUTH);

        this.add(options, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(500, 350);
        this.setLocationRelativeTo(null);

        btnRegStud.addActionListener(this);
        btnRegCourse.addActionListener(this);
        btnExit.addActionListener(this);
        btnExitStud.addActionListener(this);
        btnDelete.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

//A: checks if the necessary information for a new student is provided
//A: if any fields are null, an error message is returned
//A: all information provided, allows Student obj and sends to server for registration.
        if (e.getSource() == btnRegStud) {
            if (txtStudName.getText().isBlank() || txtStudSurname.getText().isBlank() || txtStudNumber.getText().isBlank() || txtStudPassword.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Please make sure all values are added");
            } else {
                try {
                    int num = Integer.parseInt(txtStudNumber.getText());
                    Student stud = new Student(txtStudName.getText(), txtStudSurname.getText(), txtStudPassword.getText(), num);
                    JOptionPane.showMessageDialog(null, con.newStudent(stud));
                    txtStudName.setText("");
                    txtStudSurname.setText("");
                    txtStudNumber.setText("");
                    txtStudPassword.setText("");
                    populateStuds();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Not a valid Student ID");
                }
            }
//A: checks if the necessary information for a new course is provided
//A: if any fields are null, an error message is returned
//A: all information provided, allows Courses obj and sends to server for course registration.
        } else if (e.getSource() == btnRegCourse) {
            if (txtCourseCode.getText().isBlank() || txtCourseName.getText().isBlank() || txtCoursePrice.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Please make sure all values are added");
            } else {
                try {
                    int num = Integer.parseInt(txtCoursePrice.getText());
                    Courses co = new Courses(txtCourseCode.getText(), txtCourseName.getText(), num);
                    JOptionPane.showMessageDialog(null, con.newCourse(co));
                    txtCourseCode.setText("");
                    txtCourseName.setText("");
                    txtCoursePrice.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Not a valid Price");

                }
            }
            
//A: sends "Terminate" signal to the server, closes the connection and exits progrma
   
        } else if (e.getSource() == btnExit || e.getSource() == btnExitStud) {
            try {
                con.sendData("Terminate");
                con.closeAll();
                this.dispose();
            } catch (IOException ex) {
                Logger.getLogger(Login_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//A: gets the selected student from the list of students, sends a request to delete the student record to the server, and updates the list of students. 
//The server's response is displayed.
        } else if(e.getSource() == btnDelete){
            int i = lstStuds.getSelectedIndex();
            try {
                JOptionPane.showMessageDialog(null, con.deleteStud(studList.get(i)));
                populateStuds();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Admin_Page.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
//A: populates list of students 
    public void populateStuds() throws IOException, ClassNotFoundException{
        studList = con.listStuds("allStudents");
        dmlStud = new DefaultListModel();
        for(var i: studList){
            dmlStud.addElement(i);
        }
        lstStuds.setModel(dmlStud);
    }
}
