package b100.lib.client.config;

public class DoubleProperty extends DoublePropertyBase {
	
	public final double defaultValue;
	
	protected double value;
	
	public DoubleProperty(double defaultValue) {
		this.defaultValue = defaultValue;
		this.value = defaultValue;
	}
	
	@Override
	public void setValue(Double value) {
		this.value = value;
	}
	
	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public Double getDefaultValue() {
		return defaultValue;
	}
	
}
