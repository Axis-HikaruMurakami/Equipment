package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import been.User;
import db.DBManager;

public class Userdao {

	public static User login(String id, String password) {

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {

	        conn = DBManager.getConnection();

	        String sql = "SELECT user_id, password, user_name FROM user WHERE user_id = ? AND password = ? AND delete_flg = 0";

	        ps = conn.prepareStatement(sql);

	        ps.setString(1, id);
	        ps.setString(2, password);

	        rs = ps.executeQuery();
	        if (rs.next()) {
	            User user = new User();
	            user.setUser_id(rs.getString("user_id"));
	            user.setPassword(rs.getString("password"));
	            user.setUser_name(rs.getString("user_name"));
	            return user;

	        } else {
	            return null;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;

	    } finally {
	        DBManager.close(rs);
	        DBManager.close(ps);
	        DBManager.close(conn);
	    }
	}


}
