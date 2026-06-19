package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBManager;

public class AssetDao {

	public static Integer getAssetNumber(String assetName) {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Integer assetNumber = null;

	    try {
	        conn = DBManager.getConnection();
	        String sql = "SELECT asset_number FROM asset WHERE asset_name = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, assetName);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            assetNumber = rs.getInt("asset_number");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(rs);
	        DBManager.close(ps);
	        DBManager.close(conn);
	    }

	    return assetNumber;
	}
}
