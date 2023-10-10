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
                    out.writeObject(studDAO.selectStudent(db.getConnection(), stud));
                    out.flush();
                } else if (stud.isDelete()) {
                    out.writeObject(studDAO.deleteStudent(db.getConnection(), stud));
                    out.flush();
                } else {
                    out.writeObject(studDAO.createStudent(db.getConnection(), stud));
                    out.flush();
                }
            } else if (response instanceof Admin) {
                Admin admin = (Admin) response;
                out.writeObject(adminDAO.selectAdmin(db.getConnection(), admin));
                out.flush();
            } else if (response instanceof Courses) {
                Courses course = (Courses) response;
                out.writeObject(coursesDAO.newCourse(db.getConnection(), course));
                out.flush();

            } else if (response instanceof String) {
                String command = (String) response;
                if (command.equalsIgnoreCase("getCourses")) {
                    out.writeObject(coursesDAO.getCourses(db.getConnection()));
                    out.flush();
                } else if (command.equalsIgnoreCase("getEnrolled")) {
                    out.writeObject(enrolledDAO.getStudRecords(db.getConnection()));
                    out.flush();
                } else if (command.equalsIgnoreCase("allStudents")) {
                    out.writeObject(studDAO.selectStudents(db.getConnection()));
                    out.flush();
                }
            } else if (response instanceof NewEnroll) {
                NewEnroll obj = (NewEnroll) response;
                out.writeObject(enrolledDAO.createNew(db.getConnection(), obj));
                out.flush();
            } else if(response instanceof Enrolled){
                Enrolled obj = (Enrolled) response;
                if(obj.isDelete()){
                    out.writeObject(enrolledDAO.deleteRec(db.getConnection(), obj));
                    out.flush();
                }
            }

        } while (!response.toString().equalsIgnoreCase("Terminate"));
        db.closeAll();
        closeAll();
    }

    public void closeAll() throws IOException {
        server.close();
        in.close();
        out.close();
    }
}
