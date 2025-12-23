package b100.lib.client.config;

import java.util.Objects;

public interface Property<E> {
	
	public void setValue(E value);
	
	public E getValue();
	
	public E getDefaultValue();
	
	public default boolean isDefault() {
		return isDefaultValue(getValue());
	}
	
	public default boolean isDefaultValue(E value) {
		return Objects.equals(getValue(), getDefaultValue());
	}
	
	public default void resetToDefault() {
		setValue(getDefaultValue());
	}
	
	public void parseValue(String value);
	
	public String getStringValue();
	
}
