package b100.lib.client.config;

public class DoublePropertyImpl implements DoubleProperty {

	protected final double defaultValue;
	
	protected double value;
	
	public DoublePropertyImpl(double defaultValue) {
		this.defaultValue = this.value = defaultValue;
	}

	@Override
	public double getDouble() {
		return value;
	}

	@Override
	public void setDouble(double value) {
		this.value = value;
	}

	@Override
	public Double getDefaultValue() {
		return defaultValue;
	}
	
}
