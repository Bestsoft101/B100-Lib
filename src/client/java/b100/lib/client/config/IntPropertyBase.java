package b100.lib.client.config;

public abstract class IntPropertyBase implements Property<Integer> {
	
	@Override
	public void parseValue(String value) {
		setValue(Integer.parseInt(value));
	}
	
	@Override
	public String getStringValue() {
		return String.valueOf(getValue());
	}
	
}
