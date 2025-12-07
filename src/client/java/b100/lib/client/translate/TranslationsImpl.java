package b100.lib.client.translate;

import net.minecraft.text.Text;

class TranslationsImpl implements Translations {
	
	private String prefix;
	
	public TranslationsImpl(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public String asString(String key) {
		String fullKey = addPrefixToKey(key);
		String translation = Translate.get(fullKey);
		return translation != null ? translation : key;
	}

	@Override
	public String asStringOrNull(String key) {
		String fullKey = addPrefixToKey(key);
		return Translate.get(fullKey);
	}

	@Override
	public Text asText(String key) {
		String fullKey = addPrefixToKey(key);
		String translation = Translate.get(fullKey);
		return translation != null ? Text.of(translation) : Text.of(key);
	}

	@Override
	public Text asTextOrNull(String key) {
		String fullKey = addPrefixToKey(key);
		String translation = Translate.get(fullKey);
		return translation != null ? Text.of(translation) : null;
	}

	@Override
	public boolean exists(String key) {
		return Translate.exists(addPrefixToKey(key));
	}
	
	private String addPrefixToKey(String key) {
		return prefix != null ? prefix + key : key;
	}
	
}
