package com.bigthree.clientside;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSide {

    public static void main(String[] args) {
        try {
            Login_Page obj = new Login_Page();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
        } 
      
    }
}
