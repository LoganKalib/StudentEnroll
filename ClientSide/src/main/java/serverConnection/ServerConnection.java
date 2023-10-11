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

//A: makes connection to server via port number 12345 and calls getStreams() method
//A: check clientside page LOADS and login PAGE CONNECTS
    public ServerConnection() throws IOException, ClassNotFoundException {
        server = new Socket("127.0.0.1", 12345);
        getStreams();
    }

//A: getStreams() method creates steams between client and server 
    private void getStreams() throws IOException {
        out = new ObjectOutputStream(server.getOutputStream());
        out.flush();
        in = new ObjectInputStream(server.getInputStream());
    }

//A: sentData() method uses to send terminate to server check action performed
    public void sendData(String myMsg) throws IOException {
        out.writeObject(myMsg);
        out.flush();
    }

    public void closeAll() throws IOException {
        server.close();
        in.close();
        out.close();
    }

//A: getStudLogin() method sends stud obj and recieves stud from server
    public Student getStudLogin(Student stud) throws IOException, ClassNotFoundException {
        out.writeObject(stud);
        out.flush();

//A: check if obj is student from server else null
        try {
            Student fromServer = (Student) in.readObject();
            return fromServer;
        } catch (Exception ex) {
            return null;
        }
    }

//A: same as student    
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

//A: getCourses() sends string request to server to get course and recieves array of courses    
    public ArrayList<Courses> getCourses() throws IOException {
        out.writeObject((String) "getCourses");
        out.flush();

//A: creates array of courses
        ArrayList<Courses> reArr = new ArrayList();
        try {
            reArr = (ArrayList<Courses>) in.readObject();
            return reArr;
        } catch (Exception ex) {
            return null;
        }
    }

//A: sends request to get enrolled courses in array check populateStud() in student page
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

//A: sends new enrollments obj to server recieve a string from server
    public String newEnroll(NewEnroll obj) throws IOException, ClassNotFoundException {
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();

    }

//A: send new student obj to server recieves success string 
    public String newStudent(Student obj) throws IOException, ClassNotFoundException {
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();
    }

//A: sends new course obj recieves success string    
    public String newCourse(Courses obj) throws IOException, ClassNotFoundException {
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();
    }

//A: writes string to database and return arraylist of students    
    public ArrayList<Student> listStuds(String fetch) throws IOException, ClassNotFoundException {
        out.writeObject(fetch);
        out.flush();
        return (ArrayList<Student>) in.readObject();
    }

//A:  sends stud obj sets delete to true returns success string  
    public String deleteStud(Student stud) throws IOException, ClassNotFoundException {
        stud.setDelete(true);
        out.writeObject(stud);
        out.flush();
        return (String) in.readObject();
    }

//A: sends enrolled obj sets delete to true returns success string
    public String deleteEnroll(Enrolled obj) throws IOException, ClassNotFoundException {
        obj.setDelete(true);
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();
    }
}
