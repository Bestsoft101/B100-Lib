package b100.lib.client.config;

class IntPropertyImpl implements IntProperty {

	private final int defaultValue;
	
	private int value;
	
	public IntPropertyImpl(int defaultValue) {
		this.defaultValue = this.value = defaultValue;
	}
	
	@Override
	public int getInt() {
		return value;
	}

	@Override
	public void setInt(int value) {
		this.value = value;
	}

	@Override
	public Integer getDefaultValue() {
		return defaultValue;
	}
	
}
