package b100.lib.config.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class PropertyImpl<E> implements Property<E> {

	protected final E defaultValue;
	
	protected E value;
	
	protected Function<E, String> toString;
	protected Function<String, E> parser;
	
	protected final List<Consumer<E>> valueChangeListeners = new ArrayList<>();
	
	public PropertyImpl(E defaultValue, Function<E, String> toString, Function<String, E> parseFunction) {
		this.defaultValue = this.value = defaultValue;
		
		this.toString = toString;
		this.parser = parseFunction;
	}
	
	protected PropertyImpl(E defaultValue) {
		this.defaultValue = this.value = defaultValue;
	}

	@Override
	public E get() {
		return value;
	}

	@Override
	public void set(E value) {
		if(!Objects.equals(this.value, value)) {
			this.value = value;
			onValueChange(value);
		}
	}
	
	protected void onValueChange(E value) {
		for(Consumer<E> listener : valueChangeListeners) {
			listener.accept(value);
		}
	}

	@Override
	public E getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void parse(String value) {
		set(parser.apply(value));
	}

	@Override
	public String stringValue() {
		return toString.apply(value);
	}
	
	public Function<String, E> getParser() {
		return parser;
	}
	
	public Function<E, String> getToStringFunction() {
		return toString;
	}
	
	public void setParser(Function<String, E> parseFunction) {
		this.parser = parseFunction;
	}
	
	public void setToStringFunction(Function<E, String> toString) {
		this.toString = toString;
	}
	
	public PropertyImpl<E> addValueChangeListener(Consumer<E> listener) {
		valueChangeListeners.add(listener);
		return this;
	}
	
	public boolean removeValueChangeListener(Consumer<E> listener) {
		return valueChangeListeners.remove(listener);
	}
	
}
