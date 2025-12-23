package b100.lib.client.config;

public abstract class DoublePropertyBase implements Property<Double> {

	@Override
	public void parseValue(String str) {
		setValue(Double.parseDouble(str));
	}

	@Override
	public String getStringValue() {
		return String.valueOf(getValue());
	}
	
}
