package b100.lib.client.config;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Property<E> {
	
	public E get();
	
	public void set(E value);
	
	public E getDefaultValue();
	
	public void parse(String value);
	
	public String stringValue();
	
	public static <E> Property<E> create(final E defaultValue, Function<String, E> parser, Function<E, String> toString) {
		Property<E> prop = new Property<E>() {
			E value;
			
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
