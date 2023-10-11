package com.bigthree.clientside;

import com.bigthree.objects.Courses;
import com.bigthree.objects.Enrolled;
import com.bigthree.objects.NewEnroll;
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

public class Student_Page extends JFrame implements ActionListener {

    private final JTabbedPane main;
    private final JPanel left;
    private final JPanel right;
    private final JPanel buttons;
    private final JList allCourses;
    private final JList allEnrolled;
    private final DefaultListModel dmlCourses;
    private final DefaultListModel dmlEnrolled;
    private final JButton btnRegister;
    private final JButton btnExit;
    private final JButton btnDelete;

    private final Student loggedin;
    private ServerConnection con;
    private ArrayList<Courses> display;
    private ArrayList<Enrolled> displayEn;

//A: when loaded student object and server connection parsed to student page
    public Student_Page(Student stud, ServerConnection con) throws IOException, ClassNotFoundException {

        this.con = con;
        loggedin = stud;

        this.setTitle("Student Page");
        this.setLayout(new BorderLayout());

        main = new JTabbedPane();
        left = new JPanel();
        right = new JPanel();
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));

        dmlCourses = new DefaultListModel();
        dmlEnrolled = new DefaultListModel();

        allCourses = new JList(dmlCourses);
        allEnrolled = new JList(dmlEnrolled);

        btnRegister = new JButton("Register");
        btnExit = new JButton("Exit");
        btnDelete = new JButton("Delete");

        btnRegister.addActionListener(this);
        btnExit.addActionListener(this);
        btnDelete.addActionListener(this);

        left.setLayout(new BorderLayout());
        right.setLayout(new BorderLayout());

        left.add(allCourses, BorderLayout.CENTER);
        left.add(btnRegister, BorderLayout.SOUTH);

        right.add(allEnrolled, BorderLayout.CENTER);
        buttons.add(btnDelete);
        buttons.add(btnExit);
        right.add(buttons, BorderLayout.SOUTH);

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

//A: defines arraylist of courses
        display = con.getCourses();

//A: method called 
        populateStud();

    }

//A: this populates courses list and enrolled list
    public void populateStud() throws IOException, ClassNotFoundException {
//A: if courses are null =
        if (display == null) {
            dmlCourses.clear();
            dmlCourses.addElement("No Courses.");
//A: else displays array list of courses
        } else {
            dmlCourses.clear();
            for (var i : display) {
                dmlCourses.addElement(i.toString());
            }
        }

        displayEn = con.getEnrolled();
//A: if courses are null =
        if (displayEn == null) {
            dmlEnrolled.clear();
            dmlEnrolled.addElement("No enrollments.");
            allEnrolled.setEnabled(false);

//A: else displays array list of courses
        } else {
            dmlEnrolled.clear();
            for (var i : displayEn) {
                if (i.getStudNum() == loggedin.getNumber()) {
                    dmlEnrolled.addElement(i.toString());
                }
            }
        }
    }

//A: newenrolled object is created parse login student, gets arraylist of courses and gets user selected course
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            NewEnroll obj = new NewEnroll(loggedin, display.get(allCourses.getSelectedIndex()));
            //A: adds newly enrolled course to enrolled table and returns string, repopulates display list
            try {
                String msg = con.newEnroll(obj);
                JOptionPane.showMessageDialog(null, msg);
                if (msg.equalsIgnoreCase("Record added Successfully.")) {
                    populateStud();
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Student_Page.class.getName()).log(Level.SEVERE, null, ex);
            }

//A: exits programs 
        } else if (e.getSource() == btnExit) {
            try {
                con.sendData("Terminate");
                con.closeAll();
                this.dispose();
            } catch (IOException ex) {
                Logger.getLogger(Login_Page.class.getName()).log(Level.SEVERE, null, ex);
            }

//A: gets selected errolled course and deletes record and then repopulate
        } else if (e.getSource() == btnDelete) {
            int i = allEnrolled.getSelectedIndex();
            try {
                JOptionPane.showMessageDialog(null, con.deleteEnroll(displayEn.get(i)));
                populateStud();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Student_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
