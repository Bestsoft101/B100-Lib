package b100.lib.config.properties;

import java.io.File;

public class PropertiesFile extends Properties {
	
	private File file;

	public PropertiesFile(File file) {
		this.file = file;
	}
	
	public boolean load() {
		boolean ret = super.load(file);
		
		if(!file.exists()) {
			save();
		}
		
		return ret;
	}
	
	public void save() {
		super.save(file);
	}
	
	@Deprecated
	@Override
	public boolean load(File file) {
		return super.load(file);
	}
	
	@Deprecated
	@Override
	public void save(File file) {
		super.save(file);
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
}
