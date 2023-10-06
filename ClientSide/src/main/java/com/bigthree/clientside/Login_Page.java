package com.bigthree.clientside;

import com.bigthree.objects.Admin;
import com.bigthree.objects.Student;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import serverConnection.ServerConnection;

public class Login_Page extends JFrame implements ActionListener {

    private JPanel north, south;
    private JLabel lblName, lblPass;
    private JTextField txtUsername, txtPassword;
    private JButton btnLogin, btnExit;
    private ServerConnection con = new ServerConnection();

    public Login_Page() throws IOException, ClassNotFoundException {
        this.setLayout(new BorderLayout());
        this.setTitle("Login Page");

        north = new JPanel();
        south = new JPanel();
        lblName = new JLabel("Username: ");
        lblPass = new JLabel("Password: ");
        txtUsername = new JTextField(20);
        txtPassword = new JTextField(20);
        btnLogin = new JButton("Login");
        btnExit = new JButton("Exit");

        north.setLayout(new GridLayout(2, 2));
        south.setLayout(new GridLayout(1, 2));

        north.add(lblName);
        north.add(txtUsername);
        north.add(lblPass);
        north.add(txtPassword);
        south.add(btnLogin);
        south.add(btnExit);

        north.setBorder(new EmptyBorder(10, 10, 10, 10));
        south.setBorder(new EmptyBorder(10, 10, 10, 10));

        btnLogin.addActionListener(this);
        btnExit.addActionListener(this);

        this.add(north, BorderLayout.NORTH);
        this.add(south, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            if (txtUsername.getText().isBlank() || txtPassword.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Please make sure that your login details are entered");
            } else {
                boolean x;
                try {
                    Integer.parseInt(txtUsername.getText());
                    x = true;
                } catch (NumberFormatException ex) {
                    x = false;
                }

                if (x) {
                    Student login = new Student(txtPassword.getText(), Integer.parseInt(txtUsername.getText()));
                    try {
                        Student fromServer = con.getStudLogin(login);
                        if(fromServer == null){
                            JOptionPane.showMessageDialog(null, "this user does not Exist.");
                        }else{
                            JOptionPane.showMessageDialog(null, "Welcome " + fromServer.toString());
                            Student_Page Stud = new Student_Page(fromServer, con);
                            this.dispose();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Login_Page.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Admin login = new Admin(txtPassword.getText(), txtUsername.getText());
                    try {
                        Admin fromServer = con.getAdminLogin(login);
                        if(fromServer == null){
                            JOptionPane.showMessageDialog(null, "this user does not Exist.");
                        }else{
                            JOptionPane.showMessageDialog(null, "Welcome " + fromServer.toString());
                            Admin_Page admin = new Admin_Page(fromServer, con);
                            this.dispose();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Login_Page.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } else if(e.getSource() == btnExit){
            try {
                con.sendData("Terminate");
                con.closeAll();
                this.dispose();
            } catch (IOException ex) {
                Logger.getLogger(Login_Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
