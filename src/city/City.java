package city;

import selector.Selectable;

public class City extends Selectable {

	private String numberOfOffers;
	private String name;
	private String url;

	public String getNumberOfOffers() {
		return numberOfOffers;
	}

	public void setNumberOfOffers(String numberOfOffers) {
		this.numberOfOffers = numberOfOffers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "City [numberOfOffers=" + numberOfOffers + ", name=" + name + ", url=" + url + "]";
	}

}