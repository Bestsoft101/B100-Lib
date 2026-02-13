package b100.lib.config.property;

import b100.lib.util.consumer.FloatConsumer;
import b100.lib.util.supplier.FloatSupplier;

public interface FloatProperty extends Property<Float> {
	
	public float getFloat();
	
	public void setFloat(float value);
	
	@Override
	@Deprecated
	default Float get() {
		return getFloat();
	}
	
	@Override
	@Deprecated
	default void set(Float value) {
		setFloat(value);
	}
	
	@Override
	default void parse(String value) {
		setFloat(Float.parseFloat(value));
	}
	
	@Override
	default String stringValue() {
		return String.valueOf(getFloat());
	}
	
	////////////////////////////////
	
	public static FloatProperty create(float defaultValue) {
		return new FloatPropertyImpl(defaultValue);
	}
	
	public static FloatProperty create(final float defaultValue, FloatSupplier get, FloatConsumer set) {
		FloatProperty prop = new FloatProperty() {
			@Override
			public float getFloat() {
				return get.getFloat();
			}

			@Override
			public void setFloat(float value) {
				set.accept(value);
			}

			@Override
			public Float getDefaultValue() {
				return defaultValue;
			}
		};
		return prop;
	}
	
}
