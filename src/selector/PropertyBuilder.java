package selector;

import alde.commons.properties.Property;
import alde.commons.properties.PropertyFileManager;

public class PropertyBuilder {

	private PropertyFileManager propertyFileManager;

	public PropertyBuilder(PropertyFileManager p) {
		this.propertyFileManager = p;
	}

	public Property buildProperty(String key, String defaultValue) {
		return buildProperty(key, "Automatically generated property from key '" + key + "'", defaultValue);
	}

	public Property buildProperty(String key, String description, String defaultValue) {
		return new Property(key, description, defaultValue, propertyFileManager);
	}

}
