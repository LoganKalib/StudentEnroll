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
            ps.close();
            return null;
        } else {
            ps.close();
            return reArr;
        }

    }

    public String createNew(Connection c, NewEnroll obj) throws SQLException {

        selectStudRecords = "SELECT * FROM ENROLLED WHERE Enroll_StudNum=? AND Enroll_Code =?";
        PreparedStatement ps1 = c.prepareStatement(selectStudRecords);
        ps1.setInt(1, obj.getStud().getNumber());
        ps1.setString(2, obj.getCourse().getCode());

        ResultSet rs = ps1.executeQuery();

        if (!rs.next()) {
            newEnroll = "INSERT INTO ENROLLED VALUES(?,?)";
            PreparedStatement ps = c.prepareStatement(newEnroll);
            ps.setInt(1, obj.getStud().getNumber());
            ps.setString(2, obj.getCourse().getCode());
            int row = ps.executeUpdate();

            if (row > 0) {
                ps1.close();
                ps.close();
                return "Record added Successfully.";
            } else {
                ps1.close();
                ps.close();
                return "Unable to add record";
            }
        } else {
            ps1.close();
            return "Unable to add record";
        }
    }
}
