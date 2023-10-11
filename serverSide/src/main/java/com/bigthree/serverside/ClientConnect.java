package com.bigthree.serverside;

import DbConnection.AdminDAO;
import DbConnection.CoursesDAO;
import DbConnection.DbConnect;
import DbConnection.EnrolledDAO;
import DbConnection.StudentDAO;
import com.bigthree.objects.Admin;
import com.bigthree.objects.Courses;
import com.bigthree.objects.Enrolled;
import com.bigthree.objects.NewEnroll;
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
    private CoursesDAO coursesDAO = new CoursesDAO();
    private EnrolledDAO enrolledDAO = new EnrolledDAO();
    DbConnect db = DbConnect.getInstance();
    
    // Call the required methods in the constructor
    public ClientConnect() throws IOException, ClassNotFoundException, SQLException {
        listenForClients();
        getStreams();
        communicate();
    }
    
    // Create a server socket with the port number and max queued connections as the parameter
    private void listenForClients() throws IOException {
        server = new ServerSocket(12345, 1);
        client = server.accept();
    }
    
    // Create input and out streams for communication
    private void getStreams() throws IOException {
        out = new ObjectOutputStream(client.getOutputStream());
        out.flush();
        in = new ObjectInputStream(client.getInputStream());
    }

    public void communicate() throws IOException, ClassNotFoundException, SQLException {
        do {
            // String object that stores information sent from the client
            response = in.readObject();
            System.out.println(response.toString());

            if (response instanceof Student) {
                Student stud = (Student) response;
                // Sign user in as a student
                if (stud.getName() == null && stud.getSurname() == null) {
                    out.writeObject(studDAO.selectStudent(db.getConnection(), stud));
                    out.flush();
                } 
                // Delete an existing student
                else if (stud.isDelete()) {
                    out.writeObject(studDAO.deleteStudent(db.getConnection(), stud));
                    out.flush();
                } 
                // Create a new student
                else {
                    out.writeObject(studDAO.createStudent(db.getConnection(), stud));
                    out.flush();
                }
            } 
            // Sign the user in as an admin
            else if (response instanceof Admin) {
                Admin admin = (Admin) response;
                out.writeObject(adminDAO.selectAdmin(db.getConnection(), admin));
                out.flush();
            } 
            // Checks if the course already exists before creating a new course
            // Allows the admin to create a new course
            else if (response instanceof Courses) {
                Courses course = (Courses) response;
                out.writeObject(coursesDAO.newCourse(db.getConnection(), course));
                out.flush();
                
            // if the received type is of type string, the client is making a fetch request  
            } else if (response instanceof String) {
                String command = (String) response;
                // The server will return an array list of all existing courses of type courses
                if (command.equalsIgnoreCase("getCourses")) {
                    out.writeObject(coursesDAO.getCourses(db.getConnection()));
                    out.flush();
                } 
                /* Recieves a string from th client and then the server will 
                return an array list of all existing enrollments of type enroll */
                else if (command.equalsIgnoreCase("getEnrolled")) {
                    out.writeObject(enrolledDAO.getStudRecords(db.getConnection()));
                    out.flush();
                } 
                // Receives string from client and returns an array list of all students
                else if (command.equalsIgnoreCase("allStudents")) {
                    out.writeObject(studDAO.selectStudents(db.getConnection()));
                    out.flush();
                }
            } 
            // Student chooses a course to enroll in
            // Program checks if the course exists and outputs an appropriate message
            else if (response instanceof NewEnroll) {
                NewEnroll obj = (NewEnroll) response;
                out.writeObject(enrolledDAO.createNew(db.getConnection(), obj));
                out.flush();
            } 
            // if delete is true then it will delete the student enrolled course
            else if(response instanceof Enrolled){
                Enrolled obj = (Enrolled) response;
                if(obj.isDelete()){
                    out.writeObject(enrolledDAO.deleteRec(db.getConnection(), obj));
                    out.flush();
                }
            }
        // Once the server receives the string "Terminate" from the client, it will close the application
        } while (!response.toString().equalsIgnoreCase("Terminate"));
        db.closeAll();
        closeAll();
    }
    
    // closeAll() method is called to close all connections
    public void closeAll() throws IOException {
        server.close();
        in.close();
        out.close();
    }
}
