package b100.lib.client.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import b100.lib.Print;
import b100.lib.client.util.ConfigUtil;
import b100.lib.client.util.ConfigUtil.ConfigParser;

public class Config implements ConfigParser {
	
	public File configFile;
	
	private final List<Property<?>> allProperties = new ArrayList<>();
	private final Map<String, Property<?>> propertyMap = new HashMap<>();
	
	private final List<Property<?>> allPropertiesImmutable = Collections.unmodifiableList(allProperties);
	
	public Config(File configFile) {
		this.configFile = configFile;
	}
	
	public <E extends Property<?>> E register(String name, E property) {
		allProperties.add(property);
		propertyMap.put(name, property);
		return property;
	}
	
	@Override
	public void parse(String key, String value) {
		Property<?> property = propertyMap.get(key);
		if(property == null) {
			Print.print("Unknown Config Property: " + key);
			return;
		}
		property.parseValue(value);
	}
	
	public void load() {
		if(configFile.exists()) {
			ConfigUtil.loadConfig(configFile, this, ':');	
		}
	}
	
	public void save() {
		StringBuilder str = new StringBuilder();
		Map<Property<?>, String> propertyKeys = getPropertyKeyMap();
		
		for(int i=0; i < allProperties.size(); i++) {
			if(i > 0) {
				str.append('\n');
			}
			
			Property<?> property = allProperties.get(i);
			str.append(propertyKeys.get(property));
			str.append(':');
			str.append(property.getStringValue());
		}
		
		ConfigUtil.saveStringToFile(str.toString(), configFile);
	}
	
	public List<Property<?>> getAllProperties() {
		return allPropertiesImmutable;
	}
	
	public Property<?> getProperty(String name) {
		return propertyMap.get(name);
	}
	
	public List<String> getAllPropertyKeys() {
		Map<Property<?>, String> propertyKeys = getPropertyKeyMap();
		List<String> allKeys = new ArrayList<>();
		for(int i=0; i < allProperties.size(); i++) {
			allKeys.add(propertyKeys.get(allProperties.get(i)));
		}
		return allKeys;
	}
	
	public Map<Property<?>, String> getPropertyKeyMap() {
		Map<Property<?>, String> propertyKeys = new HashMap<>();
		for(String key : propertyMap.keySet()) {
			Property<?> property = propertyMap.get(key);
			propertyKeys.put(property, key);
		}
		return propertyKeys;
	}
}
