package com.bigthree.serverside;

import com.bigthree.objects.Admin;
import com.bigthree.objects.Student;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnect {
    private ServerSocket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket client;
    private Object response;

    public ClientConnect() throws IOException, ClassNotFoundException {
        listenForClients();
        getStreams();
        communicate();
    }
    
    private void listenForClients() throws IOException {
        server = new ServerSocket(12345,1);
        client = server.accept();
    }
    
    private void getStreams() throws IOException {
        out = new ObjectOutputStream(client.getOutputStream());
        out.flush();
        in = new ObjectInputStream(client.getInputStream());
    }
    
    public void communicate() throws IOException, ClassNotFoundException {
        do {
            response = in.readObject();
            System.out.println(response.toString());
            
            if(response instanceof Student){
                System.out.println("Student logged in");
                break;
            }else if(response instanceof Admin){
                System.out.println("AdminLoggedin");
                break;
            }
            
            
        } while (!response.toString().equalsIgnoreCase("Terminate"));
        closeAll();
    }
    
    public void closeAll() throws IOException {
        server.close();
        in.close();
        out.close();
    }
}
