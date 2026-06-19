package been;

import java.sql.Date;

public class Display {
	private int		assetNumber;
	private String	assetName;
	private String	equipmentId;
	private String	maker;
	private String	model;
	private String	type;
	private String	serialnumber;
	private String 	sp;
	private Date	purchaseDate;
	private int		purchasePrice;
	private int		usegeId;
	private int		old;
	private String	currentuser;
	private String	previousUser;
	private String	location;
	private String	notes;
	private boolean	delete_u_sege;
	private Date	startDate;
	private Date	applicationCompletionDate;
	private String	equipmentNumber;          // 備品番号
	private String	macAddress;               // MACアドレス
	private java.sql.Date useStartDate;       // 現在使用開始日
	private java.sql.Date applyCompleteDate;  // 現在申請完了日
	private String	note;                     // その他
	private String	equipmentStatus;
	private String	equipmentStatusName;
	
	// + 各フィールドのgetter/setterも忘れずに
	
	public Display() {
	}


	public Display(int assetNumber, String assetName, String equipmentId, String maker, String model, String type,
			String serialnumber, String sp, Date purchaseDate, int purchasePrice, int usegeId, int old,
			String currentuser, String previousUser, String location, String notes, boolean delete_u_sege,
			Date startDate, Date applicationCompletionDate, String equipmentNumber, String macAddress,
			Date useStartDate, Date applyCompleteDate, String note) {
		this.assetNumber = assetNumber;
		this.assetName = assetName;
		this.equipmentId = equipmentId;
		this.maker = maker;
		this.model = model;
		this.type = type;
		this.serialnumber = serialnumber;
		this.sp = sp;
		this.purchaseDate = purchaseDate;
		this.purchasePrice = purchasePrice;
		this.usegeId = usegeId;
		this.old = old;
		this.currentuser = currentuser;
		this.previousUser = previousUser;
		this.location = location;
		this.notes = notes;
		this.delete_u_sege = delete_u_sege;
		this.startDate = startDate;
		this.applicationCompletionDate = applicationCompletionDate;
		this.equipmentNumber = equipmentNumber;
		this.macAddress = macAddress;
		this.useStartDate = useStartDate;
		this.applyCompleteDate = applyCompleteDate;
		this.note = note;
	}









	public int getAssetNumber() {
		return assetNumber;
	}
	public void setAssetNumber(int assetNumber) {
		this.assetNumber = assetNumber;
	}

	


	public String getEquipmentNumber() {
		return equipmentNumber;
	}
	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	


	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}


	

	public java.sql.Date getUseStartDate() {
		return useStartDate;
	}
	public void setUseStartDate(java.sql.Date useStartDate) {
		this.useStartDate = useStartDate;
	}


	

	public java.sql.Date getApplyCompleteDate() {
		return applyCompleteDate;
	}
	public void setApplyCompleteDate(java.sql.Date applyCompleteDate) {
		this.applyCompleteDate = applyCompleteDate;
	}

	


	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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


	

	public int getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}



	
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	
	
	public String getSp() {
		return sp;
	}
	public void setSp(String sp) {
		this.sp = sp;
	}
	
	
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	
	public int getUsegeId() {
		return usegeId;
	}
	public void setUsegeId(int usegeId) {
		this.usegeId = usegeId;
	}
	
	
	public int getOld() {
		return old;
	}
	public void setOld(int old) {
		this.old = old;
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
	public void setLocation(String fields) {
		this.location = fields;
	}
	
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	public boolean isDelete_u_sege() {
		return delete_u_sege;
	}
	public void setDelete_u_sege(boolean delete_u_sege) {
		this.delete_u_sege = delete_u_sege;
	}


	public String getEquipmentStatus() {
		return equipmentStatus;
	}
	public void setEquipmentStatus(String equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}


	public String getEquipmentStatusName() {
		return equipmentStatusName;
	}
	public void setEquipmentStatusName(String equipmentStatusName) {
		this.equipmentStatusName = equipmentStatusName;
	}
	


}
