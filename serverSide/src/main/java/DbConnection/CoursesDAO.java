package DbConnection;

import com.bigthree.objects.Courses;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursesDAO {

    private String selectall, newCourse;

    public ArrayList<Courses> getCourses(Connection c) throws SQLException {
        selectall = "SELECT * FROM COURSES";
        PreparedStatement ps = c.prepareStatement(selectall);
        ResultSet rs = ps.executeQuery();
        ArrayList<Courses> reArr = new ArrayList();

        while (rs.next()) {
            Courses elmt = new Courses(rs.getString("CourseCode"), rs.getString("CourseName"), rs.getInt("CoursePrice"));
            reArr.add(elmt);
        }

        if (reArr.isEmpty()) {
            ps.close();
            return null;
        } else {

            ps.close();
            return reArr;
        }

    }

    public String newCourse(Connection c, Courses obj) throws SQLException {
        selectall = "SELECT * FROM COURSES WHERE CourseCode=?";
        PreparedStatement ps1 = c.prepareStatement(selectall);
        ps1.setString(1, obj.getCode());

        ResultSet rs = ps1.executeQuery();
        if (rs.next()) {
            ps1.close();
            return "Course Code already Exists";
        } else {
            newCourse = "INSERT INTO COURSES VALUES(?,?,?)";
            PreparedStatement ps = c.prepareStatement(newCourse);
            ps.setString(1, obj.getCode());
            ps.setString(2, obj.getName());
            ps.setInt(3, obj.getPrice());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ps1.close();
                ps.close();
                return "Record added successfully.";
            } else {
                ps1.close();
                ps.close();
                return "Unable to add record.";
            }
        }

    }
}
