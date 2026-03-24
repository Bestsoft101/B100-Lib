package b100.lib.translate;

import net.minecraft.network.chat.Component;

class TranslationsImpl implements Translations {
	
	private String prefix;
	
	public TranslationsImpl(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public String asString(String key) {
		key = addPrefixToKey(key);
		String translation = Translate.get(key);
		return translation != null ? translation : key;
	}

	@Override
	public String asStringOrNull(String key) {
		key = addPrefixToKey(key);
		return Translate.get(key);
	}

	@Override
	public Component asText(String key) {
		key = addPrefixToKey(key);
		String translation = Translate.get(key);
		return translation != null ? Component.nullToEmpty(translation) : Component.nullToEmpty(key);
	}

	@Override
	public Component asTextOrNull(String key) {
		key = addPrefixToKey(key);
		String translation = Translate.get(key);
		return translation != null ? Component.nullToEmpty(translation) : null;
	}

	@Override
	public boolean exists(String key) {
		return Translate.exists(addPrefixToKey(key));
	}
	
	private String addPrefixToKey(String key) {
		return prefix != null ? prefix + key : key;
	}
	
}
