package b100.lib.client.config;

public interface IntProperty extends Property<Integer> {
	
	public int getInt();
	
	public void setInt(int value);
	
	@Override
	@Deprecated
	default Integer get() {
		return getInt();
	}
	
	@Override
	@Deprecated
	default void set(Integer value) {
		setInt(value);
	}
	
	@Override
	default void parse(String value) {
		setInt(Integer.parseInt(value));
	}
	
	@Override
	default String stringValue() {
		return String.valueOf(getInt());
	}
	
	public static IntProperty create(int defaultValue) {
		return new IntPropertyImpl(defaultValue);
	}
	
}
