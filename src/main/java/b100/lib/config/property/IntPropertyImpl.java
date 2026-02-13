package b100.lib.config.property;

public class IntPropertyImpl implements IntProperty {

	protected final int defaultValue;
	
	protected int value;
	
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
