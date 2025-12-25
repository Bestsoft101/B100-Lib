package b100.lib.client.config;

public interface Property<E> {
	
	public E get();
	
	public void set(E value);
	
	public E getDefaultValue();
	
	public void parse(String value);
	
	public String stringValue();
	
}
