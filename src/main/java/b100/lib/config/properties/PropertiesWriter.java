package b100.lib.config.properties;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import b100.lib.util.FileUtil;

public class PropertiesWriter {
	
	public final Properties properties;

	public PropertiesWriter(Properties properties) {
		this.properties = properties;
	}
	
	public void writeToFile(File file) {
		FileUtil.createNewFile(file);
		FileUtil.writeToFile(file, this::write);
	}
	
	protected void write(FileWriter fileWriter) throws IOException {
		for(Entry entry : properties.entries()) {
			writeEntry(entry, fileWriter);
		}
	}
	
	protected void writeEntry(Entry entry, FileWriter fileWriter) throws IOException {
		String value = entry.property.stringValue();
		if(value == null) {
			return;
		}
		
		fileWriter.write(entry.key);
		fileWriter.write(':');
		
		value = escapeString(value);
		
		fileWriter.write(value);
		fileWriter.write('\n');
	}
	
	protected String escapeString(String string) {
		return PropertiesUtil.escapeString(string);
	}
	
}
