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

	        String sql = "SELECT user_id, password, user_name "
	        		+ "FROM user "
	        		+ "WHERE user_id = ? AND password = ? AND delete_flg = 0 ";

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
	public static String getLocation_cd(String userId) {

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = DBManager.getConnection();

	        String sql = "SELECT location_cd FROM user WHERE user_id = ? AND delete_flg = 0";

	        ps = conn.prepareStatement(sql);
	        ps.setString(1, userId);

	        rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getString("location_cd");
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
	
	public static Integer getAdmin_flg(String user_id) {

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = DBManager.getConnection();

	        String sql = "SELECT admin_flg FROM user WHERE user_id = ? ";

	        ps = conn.prepareStatement(sql);
	        ps.setString(1, user_id);

	        rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("admin_flg");
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
	
	public static void updateUser(String user_id, String user_name, String password,
	        String location_cd, Integer admin_flg) {

	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = DBManager.getConnection();

	        String sql = "UPDATE user "
	                + "SET user_name = ?, password = ?, location_cd = ?, admin_flg = ? "
	                + "WHERE user_id = ?";

	        ps = conn.prepareStatement(sql);

	        ps.setString(1, user_name);
	        ps.setString(2, password);
	        ps.setString(3, location_cd);
	        ps.setInt(4, admin_flg);
	        ps.setString(5, user_id);

	        ps.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();

	    } finally {
	        DBManager.close(ps);
	        DBManager.close(conn);
	    }
	}
	
	public static void insertUser(String user_id, String user_name, String password,
	        String location_cd, Integer admin_flg) {

	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = DBManager.getConnection();

	        String sql = "INSERT INTO user(user_id,user_name,password,location_cd,admin_flg) "
	        		+ "VALUES(?,?,?,?,?)";

	        ps = conn.prepareStatement(sql);
	        
	        ps.setString(1, user_id);
	        ps.setString(2, user_name);
	        ps.setString(3, password);
	        ps.setString(4, location_cd);
	        ps.setInt(5, admin_flg);
	        

	        ps.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();

	    } finally {
	        DBManager.close(ps);
	        DBManager.close(conn);
	    }
	}

}
