package com.bigthree.serverside;

import DbConnection.AdminDAO;
import DbConnection.DbConnect;
import DbConnection.StudentDAO;
import com.bigthree.objects.Admin;
import com.bigthree.objects.Student;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ClientConnect {

    private ServerSocket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket client;
    private Object response;
    private StudentDAO studDAO = new StudentDAO();
    private AdminDAO adminDAO = new AdminDAO();

    public ClientConnect() throws IOException, ClassNotFoundException, SQLException {
        listenForClients();
        getStreams();
        communicate();
    }

    private void listenForClients() throws IOException {
        server = new ServerSocket(12345, 1);
        client = server.accept();
    }

    private void getStreams() throws IOException {
        out = new ObjectOutputStream(client.getOutputStream());
        out.flush();
        in = new ObjectInputStream(client.getInputStream());
    }

    public void communicate() throws IOException, ClassNotFoundException, SQLException {
        do {
            response = in.readObject();
            System.out.println(response.toString());

            if (response instanceof Student) {
                Student stud = (Student) response;
                if (stud.getName() == null && stud.getSurname() == null) {
                    DbConnect db = DbConnect.getInstance();
                    Student studData;
                    studData = studDAO.selectStudent(db.getConnection(), stud);
                    out.writeObject(studData);
                    out.flush();
                } else {
                    DbConnect db = DbConnect.getInstance();
                    out.writeObject(studDAO.createStudent(db.getConnection(), stud));
                    out.flush();
                }
            } else if (response instanceof Admin) {
                Admin admin = (Admin) response;
                DbConnect db = DbConnect.getInstance();
                out.writeObject(adminDAO.selectAdmin(db.getConnection(), admin));
                out.flush();
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
