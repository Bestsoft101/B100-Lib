package b100.lib.config.property;

public class FloatPropertyImpl implements FloatProperty {

	protected final float defaultValue;
	
	protected float value;
	
	public FloatPropertyImpl(float defaultValue) {
		this.defaultValue = this.value = defaultValue;
	}

	@Override
	public float getFloat() {
		return value;
	}

	@Override
	public void setFloat(float value) {
		this.value = value;
	}

	@Override
	public Float getDefaultValue() {
		return defaultValue;
	}
	
}
