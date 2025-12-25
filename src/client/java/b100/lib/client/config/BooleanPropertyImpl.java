package b100.lib.client.config;

class BooleanPropertyImpl implements BooleanProperty {

	private final boolean defaultValue;
	
	private boolean value;
	
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
