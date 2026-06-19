package been;

import java.sql.Date;

public class Equipment {
	
	private String	equipmentId;
	private String	maker;
	private String	model;
	private String	type;
	private String	serialnumber;
	private String	sp;
	private String	mac_ad;
	private Date	purchaseDate;
	private int		purchasePrice;
	private String	notes;
	private boolean	delete_equipment;
	private String location_cd;
	
	public Equipment() {
	}
	
	//「equipmentId_new」を追加。（やり残し消化_2026対応）
	
	public Equipment(String equipmentId, String maker, String model, String type, String serialnumber, String sp,
			String mac_ad, Date purchaseDate, int purchasePrice, String notes, boolean delete_equipment) {
		this.equipmentId		= equipmentId;
		this.maker				= maker;
		this.model				= model;
		this.type				= type;
		this.serialnumber		= serialnumber;
		this.sp					= sp;
		this.mac_ad				= mac_ad;
		this.purchaseDate		= purchaseDate;
		this.purchasePrice		= purchasePrice;
		this.notes				= notes;
		this.delete_equipment	= delete_equipment;
	}
	
	
	//現行備品ID
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
	public String getMac_ad() {
		return mac_ad;
	}
	public void setMac_ad(String mac_ad) {
		this.mac_ad = mac_ad;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public int getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public boolean isDelete_equipment() {
		return delete_equipment;
	}
	public void setDelete_equipment(boolean delete_equipment) {
		this.delete_equipment = delete_equipment;
	}

	public String getLocation_cd() {
		return location_cd;
	}

	public void setLocation_cd(String location_cd) {
		this.location_cd = location_cd;
	}
	
	
	
	

}
