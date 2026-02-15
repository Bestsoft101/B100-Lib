package b100.lib.config.properties;

import b100.lib.config.property.Property;

public class Entry {
	
	public final String key;
	public final Property<?> property;
	
	public Entry(String key, Property<?> property) {
		this.key = key;
		this.property = property;
	}
}