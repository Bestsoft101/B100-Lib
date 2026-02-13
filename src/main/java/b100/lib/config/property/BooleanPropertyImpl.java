package b100.lib.config.property;

public class BooleanPropertyImpl implements BooleanProperty {

	protected final boolean defaultValue;
	
	protected boolean value;
	
	public BooleanPropertyImpl(boolean defaultValue) {
		this.defaultValue = this.value = defaultValue;
	}
	
	@Override
	public boolean getBoolean() {
		return value;
	}

	@Override
	public void setBoolean(boolean value) {
		this.value = value;
	}

	@Override
	public Boolean getDefaultValue() {
		return defaultValue;
	}
	
}
