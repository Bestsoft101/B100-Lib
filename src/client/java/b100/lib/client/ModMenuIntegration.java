package b100.lib.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import b100.lib.client.gui.util.ScreenWrapper;
import b100.lib.client.mixin.IScreen;
import b100.lib.client.test.B100LibTesting;

public class ModMenuIntegration implements ModMenuApi {
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> new ScreenWrapper(B100LibTesting.getTestScreen((IScreen) parent));
	}
	
}

