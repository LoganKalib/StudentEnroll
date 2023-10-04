package com.bigthree.serverside;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSide {

    public static void main(String[] args) {
        try {
            ClientConnect obj = new ClientConnect();
        } catch (IOException |ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
