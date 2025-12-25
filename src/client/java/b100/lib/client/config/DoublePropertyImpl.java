package b100.lib.client.config;

class DoublePropertyImpl implements DoubleProperty {

	private final double defaultValue;
	
	private double value;
	
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
