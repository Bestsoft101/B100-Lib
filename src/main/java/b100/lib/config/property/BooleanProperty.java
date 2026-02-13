package b100.lib.config.property;

import b100.lib.util.consumer.BooleanConsumer;
import b100.lib.util.supplier.BooleanSupplier;

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
	
	////////////////////////////////
	
	public static BooleanProperty create(boolean defaultValue) {
		return new BooleanPropertyImpl(defaultValue);
	}
	
	public static BooleanProperty create(final boolean defaultValue, BooleanSupplier get, BooleanConsumer set) {
		BooleanProperty prop = new BooleanProperty() {
			@Override
			public boolean getBoolean() {
				return get.getBoolean();
			}

			@Override
			public void setBoolean(boolean value) {
				set.accept(value);
			}

			@Override
			public Boolean getDefaultValue() {
				return defaultValue;
			}
		};
		return prop;
	}
		
}
