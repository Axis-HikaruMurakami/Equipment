package been;

public class Asset {
	
	private int assetNumber;
	private String assetName;
	
	
	
	
	public Asset() {
	}
	public Asset(int assetNumber, String assetName) {
		this.assetNumber = assetNumber;
		this.assetName = assetName;
	}
	public int getAssetNumber() {
		return assetNumber;
	}
	public void setAssetNumber(int assetNumber) {
		this.assetNumber = assetNumber;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	
	

}
