package DbConnection;

import com.bigthree.objects.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {

    String selectStud, insertStud;

    public Student selectStudent(Connection c, Student stud) throws SQLException {
        selectStud = "SELECT * FROM STUDENT WHERE StudentNumber=? AND Password=?";
        PreparedStatement ps = c.prepareStatement(selectStud);
        ps.setInt(1, stud.getNumber());
        ps.setString(2, stud.getPassword());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Student studLogin = new Student(rs.getString("Name"), rs.getString("Surname"), rs.getString("Password"), rs.getInt("StudentNumber"));
            ps.close();
            return studLogin;
        }
        return null;
    }
    public ArrayList<Student> selectStudents(Connection c) throws SQLException {
        selectStud = "SELECT * FROM STUDENT";
        PreparedStatement ps = c.prepareStatement(selectStud);
        ArrayList<Student> arr = new ArrayList();
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Student studLogin = new Student(rs.getString("Name"), rs.getString("Surname"), rs.getString("Password"), rs.getInt("StudentNumber"));
            arr.add(studLogin);
        }
        ps.close();
        rs.close();
        return arr;
    }

    public String createStudent(Connection c, Student stud) throws SQLException {
        selectStud = "SELECT * FROM STUDENT WHERE StudentNumber=?";
        PreparedStatement ps1 = c.prepareStatement(selectStud);
        ps1.setInt(1, stud.getNumber());
        ResultSet rs = ps1.executeQuery();
        if (rs.next()) {
            ps1.close();
            return "This User Already Exist!";
        } else {
            insertStud = "INSERT INTO STUDENT values(?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(insertStud);
            ps.setString(1, stud.getName());
            ps.setString(2, stud.getSurname());
            ps.setInt(3, stud.getNumber());
            ps.setString(4, stud.getPassword());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                ps.close();
                ps1.close();
                return "Unable to add Student...";
            } else {
                ps.close();
                ps1.close();
                return "Student added successfully.";
            }

        }
    }
}
