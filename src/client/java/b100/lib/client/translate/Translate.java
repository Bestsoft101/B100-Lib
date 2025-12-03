package b100.lib.client.translate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import b100.lib.B100Lib;
import b100.lib.Print;
import b100.lib.client.util.ConfigUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.resource.Resource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Translate {

	private static final Map<String, String> translations = new HashMap<>();
	private static final Set<String> namespaces = new HashSet<>();
	
	static {
		registerNamespace(B100Lib.MODID);
	}
	
	public static void registerNamespace(String namespace) {
		namespaces.add(namespace);
	}
	
	public static void loadTranslations() {
		StringBuilder strb = new StringBuilder();
		List<String> sortedNamespaces = new ArrayList<>(namespaces);
		sortedNamespaces.sort(String.CASE_INSENSITIVE_ORDER);
		for(int i=0; i < sortedNamespaces.size(); i++) {
			String namespace = sortedNamespaces.get(i);
			if(i > 0) {
				strb.append(", ");
			}
			strb.append(namespace);
		}
		Print.print("Loading translations from namespaces: " + strb.toString());
		
		LanguageManager languageManager = MinecraftClient.getInstance().getLanguageManager();
		if(languageManager == null) {
			Print.print("Language Manager is null!");
			return;
		}
		
		translations.clear();
		String language = languageManager.getLanguage();
		
		if(!language.equals("en_us")) {
			loadLanguage("en_us");
		}
		loadLanguage(language);
		
		Print.print(translations.size() + " Translation keys");
	}

	private static void loadLanguage(String languageName) {
		for(String namespace : namespaces) {
			loadLanguage(namespace, languageName);
		}
	}
	
	private static void loadLanguage(String namespace, String languageName) {
		InputStream stream = null;
		
		try {
			if(FabricLoader.getInstance().isModLoaded("fabric-api")) {
				String path = "lang/" + languageName + ".lang";
				Optional<Resource> resource = MinecraftClient.getInstance().getResourceManager().getResource(Identifier.of(B100Lib.MODID, path));
				if(resource.isPresent()) {
					stream = resource.get().getInputStream();
				}
			}
			if(stream == null) {
				stream = Translate.class.getResourceAsStream("/assets/" + namespace + "/lang/" + languageName + ".lang");
				if(stream == null) {
					return;
				}
			}
			ConfigUtil.loadConfig(stream, (key, value) -> translations.put(key, value), '=');	
		}catch (Exception e) {
			throw new RuntimeException("Loading language: " + languageName, e);
		}
	}
	
	////////////////////////////////
	
	public static Text translate(String key) {
		String value = translations.get(key);
		if(value != null) {
			return Text.of(value);
		}
		return Text.of(key);
	}
	
	public static String translateIfExists(String key) {
		String value = translations.get(key);
		if(value != null) {
			return value;
		}
		return null;
	}
	
	public static String translateToString(String key) {
		return translations.get(key);
	}
	
	public static boolean translationExists(String key) {
		return translations.containsKey(key);
	}
	
}
