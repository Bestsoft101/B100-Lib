package b100.lib.client.config;

public class IntProperty extends IntPropertyBase {
	
	public final int defaultValue;
	
	protected int value;
	
	public IntProperty(int defaultValue) {
		this.defaultValue = defaultValue;
		this.value = defaultValue;
	}
	
	@Override
	public void setValue(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public Integer getDefaultValue() {
		return defaultValue;
	}
}
