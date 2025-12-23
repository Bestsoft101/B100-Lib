package b100.lib.client.config;

public abstract class BooleanPropertyBase implements Property<Boolean> {

	@Override
	public void parseValue(String str) {
		setValue(str.equalsIgnoreCase("true"));
	}

	@Override
	public String getStringValue() {
		return String.valueOf(getValue());
	}
	
}
