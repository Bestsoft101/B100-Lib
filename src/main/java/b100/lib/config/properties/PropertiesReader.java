package b100.lib.config.properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import b100.lib.Print;
import b100.lib.util.FileUtil;

public class PropertiesReader {

	public final Properties properties;
	
	public PropertiesReader(Properties properties) {
		this.properties = properties;
	}
	
	public boolean loadFromFile(File file) {
		if(!file.exists()) {
			return false;
		}

		List<Error> errors = new ArrayList<>();
		
		FileUtil.readFile(file, br -> parse(br, errors::add));

		if(errors.size() > 0) {
			onParseError(file, errors);
		}
		
		return true;
	}
	
	protected void parse(BufferedReader br, Consumer<Error> errorConsumer) throws IOException {
		int lineNumber = 0;
		while(true) {
			lineNumber++;
			String line = br.readLine();
			if(line == null) {
				break;
			}
			
			line = line.trim();
			if(line.length() == 0 || line.startsWith("#")) {
				continue;
			}
			
			int separatorPosition = line.indexOf(Properties.SEPERATOR);
			if(separatorPosition == -1) {
				errorConsumer.accept(new Error(lineNumber, line, "No seperator (" + Properties.SEPERATOR + ") in line!"));
				continue;
			}
			
			String key = line.substring(0, separatorPosition).trim();
			String value = line.substring(separatorPosition + 1);
			
			try {
				value = deescapeString(value);	
			}catch (Exception e) {
				errorConsumer.accept(new Error(lineNumber, line, "Incorrectly escaped string in line!", e));
			}
			
			Entry entry = properties.getEntry(key);
			if(entry == null) {
				continue;
			}
			
			try {
				entry.property.parse(value);	
			}catch (Exception e) {
				errorConsumer.accept(new Error(lineNumber, line, "Invalid value for property \"" + key + "\"!", e));
			}
		}
	}
	
	protected String deescapeString(String string) {
		return PropertiesUtil.deescapeString(string);
	}
	
	////////////////////////////////
	
	protected void onParseError(File file, List<Error> errors) {
		File erroredFile = createErrorFile(file);
		if(!file.renameTo(erroredFile)) {
			throw new RuntimeException("Couldn't rename file " + file.getAbsolutePath() + " to " + erroredFile.getAbsolutePath());
		}
		
		File logFile = FileUtil.changeExtension(erroredFile, "log");
		
		FileUtil.writeToFile(logFile, fw -> {
			for(Error error : errors) {
				fw.write("Error in line " + error.lineNumber + ": " + error.info + "\n");
				fw.write("    Line content: \"" + error.line + "\"\n");
			}
		});
		
		Print.printError("Config file " + file.getAbsolutePath() + " failed to load. The file has been renamed to " + erroredFile.getName()
			+ ". A log file containing more information has been created.");
	}
	
	protected File createErrorFile(File file) {
		File parent = file.getAbsoluteFile().getParentFile();
		if(!parent.exists()) {
			parent.mkdirs();
		}
		
		final LocalDateTime time = LocalDateTime.now();
		final String timeString = time.getYear()
			+ "-" + istr2(time.getMonthValue())
			+ "-" + istr2(time.getDayOfMonth())
			+ "_" + istr2(time.getHour())
			+ "-" + istr2(time.getMinute())
			+ "-" + istr2(time.getSecond());
		
		return FileUtil.changeFileNameOnly(file, FileUtil.getFilenameOnly(file) + "_error_" + timeString);
	}
	
	////////////////////////////////
	
	public static class Error {
		public final int lineNumber;
		public final String line;
		public final String info;
		public final Exception exception;
		
		public Error(int lineNumber, String line, String info) {
			this(lineNumber, line, info, null);
		}
		
		public Error(int lineNumber, String line, String info, Exception exception) {
			this.lineNumber = lineNumber;
			this.line = line;
			this.info = info;
			this.exception = exception;
		}
	}
	
	public static String istr2(int i) {
		return istr(i, 2);
	}
	
	public static String istr(int i, int minLength) {
		StringBuilder str = new StringBuilder();
		str.append(i);
		while(str.length() < minLength) {
			str.insert(0, '0');
		}
		return str.toString();
	}
	
}
