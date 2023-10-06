package DbConnection;

import com.bigthree.objects.Courses;
import com.bigthree.objects.Enrolled;
import com.bigthree.objects.NewEnroll;
import com.bigthree.objects.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnrolledDAO {

    private String selectStudRecords, newEnroll;

    public ArrayList<Enrolled> getStudRecords(Connection c) throws SQLException {
        selectStudRecords = "SELECT * FROM ENROLLED";
        PreparedStatement ps = c.prepareStatement(selectStudRecords);

        ResultSet rs = ps.executeQuery();

        ArrayList<Enrolled> reArr = new ArrayList();

        while (rs.next()) {
            Enrolled obj = new Enrolled(rs.getInt("Enroll_StudNum"), rs.getString("Enroll_Code"));
            reArr.add(obj);
        }

        if (reArr.isEmpty()) {
            return null;
        } else {
            return reArr;
        }

    }

    public String createNew(Connection c, NewEnroll obj) throws SQLException {
        newEnroll = "INSERT INTO ENROLLED VALUES(?,?)";
        PreparedStatement ps = c.prepareStatement(newEnroll);
        ps.setInt(1, obj.getStud().getNumber());
        ps.setString(2, obj.getCourse().getCode());

        selectStudRecords = "SELECT * FROM ENROLLED WHERE Enroll_StudNum=? AND Enroll_Code =?";

        PreparedStatement ps1 = c.prepareStatement(selectStudRecords);
        ps.setInt(1, obj.getStud().getNumber());
        ps.setString(2, obj.getCourse().getCode());

        ResultSet row = ps.executeQuery();

        if (row.getRow() == 0) {
            return "Unable to add";
        } else {
            int rows = ps1.executeUpdate();
            if (rows == 0) {
                return "Unable to add";
            } else {
                return "New Enrollment successfully";
            }
        }

    }
}
