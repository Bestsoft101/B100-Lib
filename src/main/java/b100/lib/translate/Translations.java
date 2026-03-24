package b100.lib.translate;

import net.minecraft.network.chat.Component;

public interface Translations {
	
	public static final Translations INSTANCE = get(null);
	
	public String asString(String key);
	
	public String asStringOrNull(String key);
	
	public Component asText(String key);
	
	public Component asTextOrNull(String key);
	
	public boolean exists(String key);
	
	////////////////////////////////
	
	/**
	 * @param namespace The namespace to load the language file from
	 */
	public static void loadFromNamespace(String namespace) {
		loadFrom(new NamespaceSearchTarget(namespace));
	}
	
	public static void loadFrom(SearchTarget searchTarget) {
		Translate.register(searchTarget);
	}
	
	/**
	 * @param queryPrefix The query prefix is added to the translation key whenever a translation is queried. Can be null.
	 */
	public static Translations get(String queryPrefix) {
		return new TranslationsImpl(queryPrefix);
	}
	
}
