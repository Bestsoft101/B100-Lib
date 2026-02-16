package b100.lib;

import java.io.File;
import java.nio.file.Paths;

import b100.lib.translate.Translate;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class B100Lib implements ModInitializer {
	
	public static final boolean INDEV = FabricLoader.getInstance().isDevelopmentEnvironment();
	public static final String MODID = "b100lib";
	public static final File CONFIG_FOLDER = Paths.get("config").toFile();
	public static final File CONFIG_FILE = new File(CONFIG_FOLDER, MODID + ".properties");
	
	@Override
	public void onInitialize() {
		Print.print("Is Development Environment: " + INDEV);
		
		Translate.loadAllTranslations("en_us");
	}
}