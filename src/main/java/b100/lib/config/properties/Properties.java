package b100.lib.config.properties;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import b100.lib.config.property.Property;

public class Properties implements Iterable<Entry> {
	
	public static final char SEPERATOR = ':';
	
	protected final List<Entry> entries = new ArrayList<>();
	protected final List<Entry> immutableEntries = Collections.unmodifiableList(entries);
	
	protected final Map<String, Entry> keyToEntry = new HashMap<>();
	
	protected PropertiesReader reader = new PropertiesReader(this);
	protected PropertiesWriter writer = new PropertiesWriter(this);
	
	////////////////////////////////
	
	public <E extends Property<?>> E add(String key, E property) {
		if(key == null) throw new NullPointerException("Key is null!");
		if(property == null) throw new NullPointerException("Property is null!");
		
		if(!PropertiesUtil.isValidPropertyKey(key, SEPERATOR)) {
			throw new RuntimeException("Invalid key: \"" + key + "\"");
		}
		
		final Entry prev = getEntry(key);
		if(prev != null) {
			entries.remove(prev);
		}
		
		final Entry entry = new Entry(key, property);
		entries.add(entry);
		keyToEntry.put(key, entry);
		
		return property;
	}
	
	public boolean remove(String key) {
		Entry entry = getEntry(key);
		if(entry != null) {
			removeEntry(entry);
			return true;
		}
		return false;
	}
	
	public boolean remove(Property<?> property) {
		Entry entry = getEntry(property);
		if(entry != null) {
			removeEntry(entry);
			return true;
		}
		return false;
	}
	
	protected void removeEntry(Entry entry) {
		keyToEntry.remove(entry.key);
		entries.remove(entry);
	}
	
	////////////////////////////////
	
	public Property<?> get(String key) {
		return getEntry(key).property;
	}
	
	public String getKey(Property<?> property) {
		Entry entry = getEntry(property);
		if(entry != null) {
			return entry.key;
		}
		return null;
	}
	
	public Entry getEntry(String key) {
		return keyToEntry.get(key);
	}
	
	public Entry getEntry(Property<?> property) {
		for(Entry entry : entries) {
			if(entry.property == property) {
				return entry;
			}
		}
		return null;
	}
	
	public boolean contains(String key) {
		return getEntry(key) != null;
	}
	
	public boolean contains(Property<?> property) {
		return getEntry(property) != null;
	}
	
	////////////////////////////////
	
	@Override
	public Iterator<Entry> iterator() {
		return immutableEntries.iterator();
	}
	
	public void forEachKey(Consumer<String> consumer) {
		Objects.requireNonNull(consumer);
		for(Entry entry : immutableEntries) {
			consumer.accept(entry.key);
		}
	}
	
	public void forEachProperty(Consumer<Property<?>> consumer) {
		Objects.requireNonNull(consumer);
		for(Entry entry : immutableEntries) {
			consumer.accept(entry.property);
		}
	}
	
	public void sortEntries(Comparator<? super Entry> c) {
		entries.sort(c);
	}
	
	public void sortEntriesByKey(Comparator<String> c) {
		entries.sort((o1, o2) -> c.compare(o1.key, o2.key));
	}
	
	public List<Entry> entries() {
		return immutableEntries;
	}
	
	public List<String> keys() {
		List<String> keys = new ArrayList<>();
		for(Entry entry : immutableEntries) {
			keys.add(entry.key);
		}
		return Collections.unmodifiableList(keys);
	}
	
	public List<Property<?>> properties() {
		List<Property<?>> properties = new ArrayList<>();
		for(Entry entry : immutableEntries) {
			properties.add(entry.property);
		}
		return Collections.unmodifiableList(properties);
	}
	
	////////////////////////////////
	
	public boolean load(File file) {
		return reader.loadFromFile(file);
	}
	
	public void save(File file) {
		writer.writeToFile(file);
	}
	
	////////////////////////////////
	
	public void setWriter(PropertiesWriter writer) {
		if(writer.properties != this) {
			throw new RuntimeException();
		}
		this.writer = writer;
	}
	
	public PropertiesWriter getWriter() {
		return writer;
	}
	
	public void setReader(PropertiesReader reader) {
		if(reader.properties != this) {
			throw new RuntimeException();
		}
		this.reader = reader;
	}
	
	public PropertiesReader getReader() {
		return reader;
	}
	
}
