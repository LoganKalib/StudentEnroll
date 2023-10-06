package DbConnection;

import com.bigthree.objects.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    
    private String selectAdmin;
    
    public Admin selectAdmin(Connection c, Admin admin) throws SQLException{
        selectAdmin = "SELECT * FROM ADMIN WHERE Username=? AND Password=?";
        PreparedStatement ps = c.prepareStatement(selectAdmin);
        ps.setString(1, admin.getUsername());
        ps.setString(2, admin.getPassword());
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            Admin adUser = new Admin(rs.getString("Name"), rs.getString("Surname"), rs.getString("Password"), rs.getString("Username"));
            ps.close();
            return adUser;
        }
        ps.close();
        return null;
    }
}
