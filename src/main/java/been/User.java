package been;

public class User {
	
	private String user_id;
	private String user_name;
	private String password;
	private String location_cd;
	private int admin_flg;
	private int delete_flg;
	
	
	
	
	
	public User() {
	}
	public User(String user_id, String user_name, String password) {
		this.user_id = user_id;
		this.user_name = user_name;
		this.password = password;
		
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocation_cd() {
		return location_cd;
	}
	public void setLocation_cd(String location_cd) {
		this.location_cd = location_cd;
	}
	public int getAdmin_flg() {
		return admin_flg;
	}
	public void setAdmin_flg(int admin_flg) {
		this.admin_flg = admin_flg;
	}
	public int getDelete_flg() {
		return delete_flg;
	}
	public void setDelete_flg(int delete_flg) {
		this.delete_flg = delete_flg;
	}

	
	
	
	
}
