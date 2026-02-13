package b100.lib.client.translate;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import b100.lib.Print;
import b100.lib.util.ConfigUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageManager;

public class Translate {

	private static final Map<String, String> translations = new HashMap<>();
	private static final Set<SearchTarget> searchTargets = new HashSet<>();
	
	////////////////////////////////
	
	public static Set<String> getAllLanguageFilePaths(String language) {
		Set<String> allPaths = new HashSet<>();
		
		for(SearchTarget searchTarget : searchTargets) {
			allPaths.add(searchTarget.getFullPath(language));
		}
		
		return allPaths;
	}
	
	public static void loadTranslations() {
		String language = getCurrentLanguage();
		if(language == null) {
			Print.print("Language is null!");
			return;
		}
		translations.clear();
		if(!language.equals("en_us")) {
			loadLanguage("en_us");
		}
		loadLanguage(language);
		
		Print.print(translations.size() + " Translation keys");
	}

	private static void loadLanguage(String languageName) {
		Set<String> paths = getAllLanguageFilePaths(languageName);
		
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
			
			ConfigUtil.loadConfig(stream, translations::put, '=');	
		}catch (Exception e) {
			throw new RuntimeException("Loading language: " + languageName, e);
		}
	}
	
	public static String getCurrentLanguage() {
		LanguageManager languageManager = MinecraftClient.getInstance().getLanguageManager();
		if(languageManager == null) {
			Print.print("Language Manager is null!");
			return null;
		}
		return languageManager.getLanguage();
	}
	
	public static InputStream getTranslationFileInputStream(String path) {
		return Translate.class.getResourceAsStream(path);
	}

	////////////////////////////////
	
	protected static void register(SearchTarget searchTarget) {
		searchTargets.add(searchTarget);
	}

	protected static boolean exists(String key) {
		return translations.containsKey(key);
	}

	protected static String get(String key) {
		return translations.get(key);
	}
}
