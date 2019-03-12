package member.model.vo;

public class Images implements java.io.Serializable{
	private static final long serialVersionUID = 20190227L;
	
	private int imageId;
	private String imageRoot;
	
	public Images() {}

	public Images(int imageId, String imageRoot) {
		super();
		this.imageId = imageId;
		this.imageRoot = imageRoot;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getImageRoot() {
		return imageRoot;
	}

	public void setImageRoot(String imageRoot) {
		this.imageRoot = imageRoot;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return imageId + ", " + imageRoot;
	}
	
}
