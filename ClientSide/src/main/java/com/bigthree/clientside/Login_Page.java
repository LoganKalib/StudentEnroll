
package com.bigthree.clientside;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login_Page extends JFrame implements ActionListener{
    private JPanel north, south;
    private JLabel lblName, lblPass;
    private JTextField txtUsername, txtPassword;
    private JButton btnLogin, btnExit;
    
    public Login_Page(){
        this.setLayout(new BorderLayout());
        this.setTitle("Login Page");
        
        north = new JPanel(); south = new JPanel();
        lblName = new JLabel("Username: "); lblPass = new JLabel("Password: ");
        txtUsername = new JTextField(20); txtPassword = new JTextField(20);
        btnLogin = new JButton("Login"); btnExit = new JButton("Exit");
        
        north.setLayout(new GridLayout(2,2));
        south.setLayout(new GridLayout(1,2));
        
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
        
    }
    
    
}
