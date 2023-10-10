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

    private final Socket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    public ServerConnection() throws IOException, ClassNotFoundException {
        server = new Socket("127.0.0.1", 12345);
        getStreams();
    }

    private void getStreams() throws IOException {
        out = new ObjectOutputStream(server.getOutputStream());
        out.flush();
        in = new ObjectInputStream(server.getInputStream());
    }

    public void sendData(String myMsg) throws IOException {
        out.writeObject(myMsg);
        out.flush();
    }

    public void closeAll() throws IOException {
        server.close();
        in.close();
        out.close();
    }

    public Student getStudLogin(Student stud) throws IOException, ClassNotFoundException {
        out.writeObject(stud);
        out.flush();
        try {
            Student fromServer = (Student) in.readObject();
            return fromServer;
        } catch (Exception ex) {
            return null;
        }
    }

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
    
    public ArrayList<Courses> getCourses() throws IOException{
        out.writeObject((String) "getCourses");
        out.flush();
        ArrayList<Courses> reArr = new ArrayList();
        try{
            reArr = (ArrayList<Courses>) in.readObject();
            return reArr;
        }catch(Exception ex){
           return null;
        }
    }
    
    public ArrayList<Enrolled> getEnrolled() throws IOException, ClassNotFoundException{
        out.writeObject((String) "getEnrolled");
        out.flush();
        ArrayList<Enrolled> reArr = new ArrayList();
        try{
            reArr = (ArrayList<Enrolled>) in.readObject();
            return reArr;
        }catch(Exception ex){
            return null;
        }
    }
    
    public String newEnroll(NewEnroll obj) throws IOException, ClassNotFoundException{
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();
       
    }
    
    public String newStudent(Student obj) throws IOException, ClassNotFoundException{
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();
    }
    
    public String newCourse(Courses obj) throws IOException, ClassNotFoundException{
        out.writeObject(obj);
        out.flush();
        return (String) in.readObject();
    }
    
    public ArrayList<Student> listStuds(String fetch) throws IOException, ClassNotFoundException{
        out.writeObject(fetch);
        out.flush();
        return (ArrayList<Student>) in.readObject();
    }
}

