package b100.lib.util;

public interface ConfigStringifiable {
	
	public String toConfigString();
	
	////////////////////////////////
	
	public static String toConfigStringOrNull(ConfigStringifiable value) {
		return value != null ? value.toConfigString() : null;
	}
	
}