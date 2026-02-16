package b100.lib.client;

import b100.lib.B100Lib;
import b100.lib.Print;
import b100.lib.translate.Translations;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageManager;

public class B100LibClient implements ClientModInitializer {
	
	static {
		Translations.loadFromNamespace(B100Lib.MODID);
	}

	public static final Translations TRANS = Translations.get(B100Lib.MODID + ".");
	
	@Override
	public void onInitializeClient() {
		
	}
	
	public static String getCurrentLanguage() {
		LanguageManager languageManager = MinecraftClient.getInstance().getLanguageManager();
		if(languageManager == null) {
			Print.print("Language Manager is null!");
			return null;
		}
		return languageManager.getLanguage();
	}
}