package been;

public class Location {
	private String location_cd;
	private String location_name;
	private String disp_order;
	
	public Location() {
	}
	public Location(String location_cd, String location_name, String disp_order) {
		this.location_cd = location_cd;
		this.location_name = location_name;
		this.disp_order = disp_order;
		
	}
	public String getLocation_cd() {
		return location_cd;
	}
	public void setLocation_cd(String location_cd) {
		this.location_cd = location_cd;
	}
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	public String getDisp_order() {
		return disp_order;
	}
	public void setDisp_order(String disp_order) {
		this.disp_order = disp_order;
	}
	
	
}
