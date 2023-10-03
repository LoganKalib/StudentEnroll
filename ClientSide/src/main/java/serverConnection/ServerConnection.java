package serverConnection;

import com.bigthree.objects.Admin;
import com.bigthree.objects.Student;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public final class ServerConnection {

    private final Socket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Object response;

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

    public void sendData(Student myMsg) throws IOException {
        out.writeObject(myMsg);
        out.flush();
    }

    public void communicate() throws IOException, ClassNotFoundException {
        do {
            response = in.readObject();
            System.out.println(response.toString());
        } while (!response.toString().equalsIgnoreCase("Terminate"));
        closeAll();
    }

    public void closeAll() throws IOException {
        server.close();
        in.close();
        out.close();
    }

    public Student getStudLogin(Student stud) throws IOException, ClassNotFoundException {
        out.writeObject(stud);
        out.flush();
        Student fromServer = (Student) in.readObject();
        if (fromServer == null) {
            return null;
        } else {
            return fromServer;
        }
    }

    public Admin getAdminLogin(Admin admin) throws IOException, ClassNotFoundException {
        out.writeObject(admin);
        out.flush();
        Admin fromServer = (Admin) in.readObject();
        if (fromServer == null) {
            return null;
        } else {
            return fromServer;
        }
    }

}
