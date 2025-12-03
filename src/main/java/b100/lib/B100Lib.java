package b100.lib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class B100Lib implements ModInitializer {
	
	public static final boolean INDEV = FabricLoader.getInstance().isDevelopmentEnvironment();
	public static final String MODID = "b100lib";
	
	@Override
	public void onInitialize() {
		Print.print("Is Development Environment: " + INDEV);
	}
}