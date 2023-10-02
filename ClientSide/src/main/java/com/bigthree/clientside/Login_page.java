
package com.bigthree.clientside;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Login_page extends JFrame implements ActionListener{
    private JPanel north, south;
    private JLabel lblName, lblPass;
    private JTextField txtUsername, txtPassword;
    private JButton btnLogin, btnExit;
    
    public Login_page(){
        this.setLayout(new GridLayout(3,2));
        
        north = new JPanel(); south = new JPanel();
        lblName = new JLabel("Username: "); lblPass = new JLabel("Password: ");
        txtUsername = new JTextField(30); txtPassword = new JTextField(30);
        btnLogin = new JButton("Login"); btnExit = new JButton("Exit");
        
        this.add(lblName);
        this.add(txtUsername);
        this.add(lblPass);
        this.add(txtPassword);
        this.add(btnLogin);
        this.add(btnExit);
        
        btnLogin.addActionListener(this);
        btnExit.addActionListener(this);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    
}
