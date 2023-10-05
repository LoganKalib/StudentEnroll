package DbConnection;

import com.bigthree.objects.Enrolled;
import com.bigthree.objects.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnrolledDAO {
    private String selectStudRecords;
    
    public ArrayList<Enrolled> getStudRecords(Connection c) throws SQLException{
        selectStudRecords = "SELECT * FROM ENROLLED";
        PreparedStatement ps = c.prepareStatement(selectStudRecords);
        
        ResultSet rs = ps.executeQuery();
        
        ArrayList<Enrolled> reArr = new ArrayList();
        
        while(rs.next()){
            Enrolled obj = new Enrolled(rs.getInt("Enroll_StudNum"),rs.getString("Enroll_Code"));
            reArr.add(obj);
        }
        
        if(reArr.isEmpty()){
            return null;
        }else{
            return reArr;
        }
        
    }
}
