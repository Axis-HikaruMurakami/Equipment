package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.DBManager;

public class LocationDao {
	public static ArrayList<String> getLocation() {

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    ArrayList<String> locationList = new ArrayList<>();

	    try {
	        conn = DBManager.getConnection();

	        String sql = "SELECT location_name FROM location";

	        ps = conn.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            locationList.add(rs.getString("location_name"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();

	    } finally {
	        DBManager.close(rs);
	        DBManager.close(ps);
	        DBManager.close(conn);
	    }

	    return locationList;
	}
}
