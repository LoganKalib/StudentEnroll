package DbConnection;

import com.bigthree.objects.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {
    
    String selectStud, insertStud;
    
    public Student selectStudent(Connection c, Student stud) throws SQLException{
        selectStud = "SELECT * FROM STUDENT WHERE StudentNumber=? AND Password=?";
        PreparedStatement ps = c.prepareStatement(selectStud);
        ps.setInt(1, stud.getNumber());
        ps.setString(2, stud.getPassword());
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            Student studLogin = new Student(rs.getString("Name"), rs.getString("Surname"),rs.getString("Password"),rs.getInt("StudentNumber"));
            return studLogin;
        }    
        return null;
    }
    
    public String createStudent(Connection c, Student stud) throws SQLException{
        insertStud = "INSERT INTO STUDENT values(?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(insertStud);
        ps.setString(1, stud.getName());
        ps.setString(2, stud.getSurname());
        ps.setInt(3, stud.getNumber());
        ps.setString(4, stud.getPassword());
        
        if(selectStudent(c,stud) == null){
            int rows = ps.executeUpdate();
            if(rows==0){
                return "Unable to add Student...";
            }else{
                return "Student added successfully.";
            }
        }else{
            return "This User Already Exist!";
        }
        
        
    }
}
