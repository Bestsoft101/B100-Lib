package b100.lib.config.properties;

public abstract class PropertiesUtil {
	
	public static boolean isValidPropertyKey(String key, char separator) {
		// Can't start or end with whitespace
		if(key.trim().length() != key.length()) {
			return false;
		}
		// Can't contain the separator character
		if(key.indexOf(separator) != -1) {
			return false;
		}
		// Can't contain any line breaks
		if(key.indexOf('\n') != -1) {
			return false;
		}
		// Can't start with a hashtag as it would turn the line into a comment
		if(key.startsWith("#")) {
			return false;
		}
		return true;
	}
	
	public static String escapeString(String string) {
		final boolean quote = string.trim().length() != string.length();
		
		StringBuilder str = new StringBuilder();
		if(quote) {
			str.append('"');
		}
		
		for(int i=0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(c == '"') {
				str.append("\\\"");
				
			}else if(c == '\n') {
				str.append("\\n");
				
			}else if(c == '\t') {
				str.append("\\t");
				
			}else if(c == '\r') {
				str.append("\\r");
				
			}else {
				str.append(c);
			}
		}
		
		if(quote) {
			str.append('"');
		}
		
		return str.toString();
	}
	
	public static String deescapeString(String string) {
		string = string.trim();
		
		if(string.startsWith("\"")) {
			string = string.substring(1, string.length() - 1);
		}
		
		StringBuilder str = new StringBuilder();
		for(int i=0; i < string.length(); i++) {
			char c = string.charAt(i);
			
			if(c == '\\') {
				char next = string.charAt(++i);
				
				if(next == '\\') {
					str.append('\\');
					
				}else if(next == '"') {
					str.append('"');
					
				}else if(next == 'n') {
					str.append('\n');
					
				}else if(next == 't') {
					str.append('\t');
					
				}else if(next == 'r') {
					str.append('\r');
					
				}else {
					throw new RuntimeException("Invalid escaped character " + next + " at index " + i);
				}
			}else {
				str.append(c);
			}
		}
		
		return str.toString();
	}
	
}
