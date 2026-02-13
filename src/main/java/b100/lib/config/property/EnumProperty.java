package b100.lib.config.property;

public interface EnumProperty<E extends Enum<E>> extends Property<E> {

	public Class<E> getType();
	
	static <E extends Enum<E>> EnumProperty<E> create(Class<E> type) {
		return new EnumPropertyImpl<E>(type, type.getEnumConstants()[0]);
	}

	static <E extends Enum<E>> EnumProperty<E> create(Class<E> type, E defaultValue) {
		return new EnumPropertyImpl<E>(type, defaultValue);
	}
	
}
