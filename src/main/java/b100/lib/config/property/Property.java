package b100.lib.config.property;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import b100.lib.util.ConfigStringifiable;

public interface Property<E> {
	
	public E get();
	
	public void set(E value);
	
	public E getDefaultValue();
	
	public void parse(String value);
	
	public String stringValue();
	
	////////////////////////////////
	
	public static <E> PropertyImpl<E> create(final E defaultValue, Function<String, E> parser, Function<E, String> toString) {
		return new PropertyImpl<E>(defaultValue, toString, parser);
	}
	
	public static <E extends ConfigStringifiable> PropertyImpl<E> create(E defaultValue, Function<String, E> parseFunction) {
		return new PropertyImpl<E>(defaultValue, ConfigStringifiable::toConfigStringOrNull, parseFunction);
	}
	
	public static <E> Property<E> create(final E defaultValue, Supplier<E> get, Consumer<E> set, Function<String, E> parser, Function<E, String> toString) {
		Property<E> prop = new Property<E>() {
			@Override
			public E get() {
				return get.get();
			}

			@Override
			public void set(E value) {
				set.accept(value);
			}

			@Override
			public E getDefaultValue() {
				return defaultValue;
			}

			@Override
			public void parse(String value) {
				parser.apply(value);
			}

			@Override
			public String stringValue() {
				return toString.apply(get());
			}
		};
		return prop;
	}
}
