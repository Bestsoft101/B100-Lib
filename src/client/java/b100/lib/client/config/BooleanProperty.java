package b100.lib.client.config;

public interface BooleanProperty extends Property<Boolean> {
	
	public boolean getBoolean();
	
	public void setBoolean(boolean value);
	
	@Override
	@Deprecated
	default Boolean get() {
		return getBoolean();
	}
	
	@Override
	@Deprecated
	default void set(Boolean value) {
		setBoolean(value);
	}
	
	@Override
	default void parse(String value) {
		setBoolean(value.equalsIgnoreCase("true"));
	}
	
	@Override
	default String stringValue() {
		return String.valueOf(getBoolean());
	}
	
	public static BooleanProperty create(boolean defaultValue) {
		return new BooleanPropertyImpl(defaultValue);
	}
		
}
