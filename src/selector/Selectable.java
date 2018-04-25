package selector;

import alde.commons.properties.Property;
import alde.commons.properties.PropertyFileManager;

public abstract class Selectable {

	private static final PropertyBuilder propertyBuilder = new PropertyBuilder(new PropertyFileManager("selector.properties"));

	private Property isSelected;

	public abstract String getName();

	public boolean isSelected() {

		if (isSelected == null) {
			isSelected = propertyBuilder.buildProperty(getName(), Property.FALSE);
		}

		return isSelected.getValueAsBoolean();
	}

	public void setSelected(boolean selected) {
		if (isSelected == null) {
			isSelected = propertyBuilder.buildProperty(getName(), Property.FALSE);
		}

		isSelected.setNewValue(selected);
	}

}
