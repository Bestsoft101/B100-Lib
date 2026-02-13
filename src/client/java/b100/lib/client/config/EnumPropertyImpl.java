package b100.lib.client.config;

public class EnumPropertyImpl<E extends Enum<E>> implements EnumProperty<E> {

	protected final Class<E> type;
	protected final E defaultValue;
	
	protected E value;
	
	public EnumPropertyImpl(Class<E> type, E defaultValue) {
		this.type = type;
		this.defaultValue = this.value = defaultValue;
	}

	@Override
	public E get() {
		return value;
	}

	@Override
	public void set(E value) {
		this.value = value;
	}

	@Override
	public E getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void parse(String string) {
		E[] values = type.getEnumConstants();
		for(int i=0; i < values.length; i++) {
			E enumValue = values[i];
			if(enumValue.name().equals(string)) {
				value = enumValue;
			}
		}
	}

	@Override
	public String stringValue() {
		return value.name();
	}

	@Override
	public Class<E> getType() {
		return type;
	}
	
}