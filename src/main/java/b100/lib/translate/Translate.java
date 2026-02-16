package b100.lib.translate;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import b100.lib.Print;
import b100.lib.config.properties.PropertiesUtil;
import b100.lib.util.ConfigUtil;

public class Translate {

	private static final Map<String, String> translations = new HashMap<>();
	private static final Set<SearchTarget> searchTargets = new HashSet<>();
	
	private static String previousLanguage = null;;
	
	////////////////////////////////
	
	public static Set<String> getAllLanguageFilePaths(String language) {
		return getLanguageFilePaths(language, searchTargets);
	}
	
	public static Set<String> getLanguageFilePaths(String language, Set<SearchTarget> targets) {
		Set<String> allPaths = new HashSet<>();
		
		for(SearchTarget searchTarget : targets) {
			allPaths.add(searchTarget.getFullPath(language));
		}
		
		return allPaths;
	}
	
	public static void loadAllTranslations(String language) {
		loadTranslations(language, searchTargets);
	}
	
	public static void loadTranslations(String language, Set<SearchTarget> targets) {
		if(language == null) {
			Print.print("Language is null!");
			return;
		}
		
		translations.clear();
		if(!language.equals("en_us")) {
			loadLanguage("en_us", targets);
		}
		loadLanguage(language, targets);
		
		Print.print(translations.size() + " Translation keys");
		
		previousLanguage = language;
	}

	private static void loadLanguage(String languageName, Set<SearchTarget> targets) {
		Set<String> paths = getLanguageFilePaths(languageName, targets);
		
		for(String path : paths) {
			loadLanguage(path, languageName);	
		}
	}
	
	private static void loadLanguage(String path, String languageName) {
		InputStream stream = null;
		
		try {
			stream = getTranslationFileInputStream(path);
			if(stream == null) {
				return;
			}
			
			ConfigUtil.loadConfig(stream, (key, value) -> translations.put(key, PropertiesUtil.deescapeString(value)), '=');	
		}catch (Exception e) {
			throw new RuntimeException("Loading language: " + languageName, e);
		}
	}
	
	public static InputStream getTranslationFileInputStream(String path) {
		return Translate.class.getResourceAsStream(path);
	}
	
	public static String getPreviousLanguage() {
		return previousLanguage;
	}

	////////////////////////////////
	
	protected static void register(SearchTarget searchTarget) {
		searchTargets.add(searchTarget);
		
		if(previousLanguage != null) {
			loadTranslations(previousLanguage, Sets.newHashSet(searchTarget));	
		}
	}

	protected static boolean exists(String key) {
		return translations.containsKey(key);
	}

	protected static String get(String key) {
		return translations.get(key);
	}
}
