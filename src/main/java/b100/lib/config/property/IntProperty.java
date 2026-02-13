package b100.lib.config.property;

import b100.lib.util.consumer.IntConsumer;
import b100.lib.util.supplier.IntSupplier;

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
	
	////////////////////////////////
	
	public static IntProperty create(int defaultValue) {
		return new IntPropertyImpl(defaultValue);
	}
	
	public static IntProperty create(final int defaultValue, IntSupplier get, IntConsumer set) {
		IntProperty prop = new IntProperty() {
			@Override
			public int getInt() {
				return get.getInt();
			}

			@Override
			public void setInt(int value) {
				set.accept(value);
			}

			@Override
			public Integer getDefaultValue() {
				return defaultValue;
			}
		};
		return prop;
	}
	
}
