package serverConnection;

import com.bigthree.objects.Admin;
import com.bigthree.objects.Courses;
import com.bigthree.objects.Enrolled;
import com.bigthree.objects.NewEnroll;
import com.bigthree.objects.Student;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public final class ServerConnection {

//A: connection variables for server, input and output streams
    private final Socket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;

//A: Constructor method for establishing a conn to the server.
//A: Creates a socket conn to the server with IP and port number
//A: Initializes I/O streams for communication with the server.
    public ServerConnection() throws IOException, ClassNotFoundException {
        server = new Socket("127.0.0.1", 12345);
        getStreams();
    }

//A: Creates the I/O streams for communication with the server 
    private void getStreams() throws IOException {
        out = new ObjectOutputStream(server.getOutputStream());
        out.flush();
        in = new ObjectInputStream(server.getInputStream());
    }

//A: Sends a message of type String to the server
    public void sendData(String myMsg) throws IOException {
        out.writeObject(myMsg);
        out.flush();
    }

//A: Closes the socket connection, I/O streams 
    public void closeAll() throws IOException {
        server.close();
        in.close();
        out.close();
    }

//A: Sends student obj to server for login and recieves student obj from server after authentication
    public Student getStudLogin(Student stud) throws IOException, ClassNotFoundException {
        out.writeObject(stud);
        out.flush();

//A: Performs authentication on the server and returns authenticated student obj
        try {
            Student fromServer = (Student) in.readObject();
            return fromServer;
        } catch (Exception ex) {
            return null;
        }
    }

//A: same as student login    
    public Admin getAdminLogin(Admin admin) throws IOException, ClassNotFoundException {
        out.writeObject(admin);
        out.flush();
        try {
            Admin fromServer = (Admin) in.readObject();
            return fromServer;
        } catch (Exception ex) {
            return null;
        }
    }

//A: Sends request to the server to retrieve a list of courses
//A: Receives an ArrayList of Courses obj in return
    public ArrayList<Courses> getCourses() throws IOException {
        out.writeObject((String) "getCourses");
        out.flush();
        ArrayList<Courses> reArr = new ArrayList();
        try {
            reArr = (ArrayList<Courses>) in.readObject();
            return reArr;
        } catch (Exception ex) {
            return null;
        }
    }

//A: Sends a request to the server to retrieve a list of enrolled courses for a student
//A: Receives and ArrayList of Enrolled obj    
    public ArrayList<Enrolled> getEnrolled() throws IOException, ClassNotFoundException {
        out.writeObject((String) "getEnrolled");
        out.flush();
        ArrayList<Enrolled> reArr = new ArrayList();
        try {
            reArr = (ArrayList<Enrolled>) in.readObject();
            return reArr;
        } catch (Exception ex) {
            return null;
        }
    }

//A: Sends a NewEnroll obj to the server to enroll a student in a course and receives a success string as a response
    public String newEnroll(NewEnroll obj) throws IOException, ClassNotFoundException {
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();

    }

//A: Sends a student obj to server to register a new student and recieves a success string in response
    public String newStudent(Student obj) throws IOException, ClassNotFoundException {
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();
    }

//A: Sends a course obj to the server to create a new course and receives success string    
    public String newCourse(Courses obj) throws IOException, ClassNotFoundException {
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();
    }

//A: sends request to the server to retrieve a list of students
//A: receives an ArrayList of Student obj
    public ArrayList<Student> listStuds(String fetch) throws IOException, ClassNotFoundException {
        out.writeObject(fetch);
        out.flush();
        return (ArrayList<Student>) in.readObject();
    }

//A:  sends request to unenroll a student record on the server, success string  
    public String deleteStud(Student stud) throws IOException, ClassNotFoundException {
        stud.setDelete(true);
        out.writeObject(stud);
        out.flush();
        return (String) in.readObject();
    }

//A: sends request to unenroll a student from a course on the server and recieves a success string
    public String deleteEnroll(Enrolled obj) throws IOException, ClassNotFoundException {
        obj.setDelete(true);
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();
    }
}
