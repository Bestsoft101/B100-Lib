package b100.lib.client.config;

public class BooleanProperty extends BooleanPropertyBase {

	public final boolean defaultValue;
	
	protected boolean value;
	
	public BooleanProperty(boolean defaultValue) {
		this.defaultValue = defaultValue;
		this.value = defaultValue;
	}
	
	@Override
	public void setValue(Boolean value) {
		this.value = value;
	}
	
	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public Boolean getDefaultValue() {
		return defaultValue;
	}
}
