package valuteConverter;

public class ValuteSpinnerModel {

	private String value;
	private String subValue;
	private int image;

	public ValuteSpinnerModel(String value, String subValue, int image) {
		// super();
		this.value = value;
		this.subValue = subValue;
		this.image = image;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSubValue() {
		return subValue;
	}

	public void setSubValue(String subValue) {
		this.subValue = subValue;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return value;

	}

}
