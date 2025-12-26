package b100.lib.client.config;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public interface DoubleProperty extends Property<Double> {
	
	public double getDouble();
	
	public void setDouble(double value);
	
	@Override
	@Deprecated
	default Double get() {
		return getDouble();
	}
	
	@Override
	@Deprecated
	default void set(Double value) {
		setDouble(value);
	}
	
	@Override
	default void parse(String value) {
		setDouble(Double.parseDouble(value));
	}
	
	@Override
	default String stringValue() {
		return String.valueOf(getDouble());
	}
	
	public static DoubleProperty create(double defaultValue) {
		return new DoublePropertyImpl(defaultValue);
	}
	
	public static DoubleProperty create(final double defaultValue, DoubleSupplier get, DoubleConsumer set) {
		DoubleProperty prop = new DoubleProperty() {
			@Override
			public double getDouble() {
				return get.getAsDouble();
			}

			@Override
			public void setDouble(double value) {
				set.accept(value);
			}

			@Override
			public Double getDefaultValue() {
				return defaultValue;
			}
		};
		return prop;
	}
	
}
