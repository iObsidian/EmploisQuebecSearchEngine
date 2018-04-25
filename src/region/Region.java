package region;

import selector.Selectable;

public class Region extends Selectable {

	private String name;
	private String code;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Region [name=" + name + ", code=" + code + "]";
	}

}