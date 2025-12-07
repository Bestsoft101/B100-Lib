package b100.lib.client;

import b100.lib.B100Lib;
import b100.lib.client.translate.Translations;
import net.fabricmc.api.ClientModInitializer;

public class B100LibClient implements ClientModInitializer {
	
	static {
		Translations.loadFromNamespace(B100Lib.MODID);
	}

	public static Translations trans = Translations.get("b100lib.");
	
	@Override
	public void onInitializeClient() {
		
	}
}