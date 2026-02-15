package b100.lib.config.properties;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

public class DetailedPropertiesWriter extends PropertiesWriter {

	private Function<String, String> commentProvider;
	
	public DetailedPropertiesWriter(Properties properties) {
		super(properties);
	}
	
	@Override
	protected void writeEntry(Entry entry, FileWriter fileWriter) throws IOException {
		String comment = commentProvider.apply(entry.key);
		if(comment != null) {
			String[] lines = comment.split("\n");
			
			for(int i=0; i < lines.length; i++) {
				String line = lines[i].trim();
				if(line.length() == 0) {
					continue;
				}
				
				fileWriter.write("# ");
				fileWriter.write(line);
				fileWriter.write('\n');
			}
		}
		
		super.writeEntry(entry, fileWriter);
		
		fileWriter.write('\n');
	}
	
	public DetailedPropertiesWriter setCommentProvider(Function<String, String> commentProvider) {
		this.commentProvider = commentProvider;
		return this;
	}
	
	public DetailedPropertiesWriter setComments(Map<String, String> map) {
		setCommentProvider(map::get);
		return this;
	}
	
}
