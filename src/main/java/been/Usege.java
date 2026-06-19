package been;

import java.sql.Date;

public class Usege {
	
	private int usegeId;
	private String currentuser;
	private String previousUser;
	private String location;
	private Date startDate;
	private Date applicationCompletionDate;
	private boolean delete_u_sege;
	
	
	
	public Usege() {
	}
	public Usege(int usegeId, String currentuser, String previousUser, String location, Date startDate,
			Date applicationCompletionDate, boolean delete_u_sege) {
		this.usegeId = usegeId;
		this.currentuser = currentuser;
		this.previousUser = previousUser;
		this.location = location;
		this.startDate = startDate;
		this.applicationCompletionDate = applicationCompletionDate;
		this.delete_u_sege = delete_u_sege;
	}
	public int getUsegeId() {
		return usegeId;
	}
	public void setUsegeId(int usegeId) {
		this.usegeId = usegeId;
	}
	public String getCurrentuser() {
		return currentuser;
	}
	public void setCurrentuser(String currentuser) {
		this.currentuser = currentuser;
	}
	public String getPreviousUser() {
		return previousUser;
	}
	public void setPreviousUser(String previousUser) {
		this.previousUser = previousUser;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getApplicationCompletionDate() {
		return applicationCompletionDate;
	}
	public void setApplicationCompletionDate(Date applicationCompletionDate) {
		this.applicationCompletionDate = applicationCompletionDate;
	}
	public boolean isDelete_u_sege() {
		return delete_u_sege;
	}
	public void setDelete_u_sege(boolean delete_u_sege) {
		this.delete_u_sege = delete_u_sege;
	}
	
	
	

}
