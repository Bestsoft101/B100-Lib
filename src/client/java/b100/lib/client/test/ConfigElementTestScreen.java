package b100.lib.client.test;

import java.io.File;

import b100.lib.B100Lib;
import b100.lib.Print;
import b100.lib.client.B100LibClient;
import b100.lib.client.config.BooleanProperty;
import b100.lib.client.config.Config;
import b100.lib.client.config.IntProperty;
import b100.lib.client.gui.GuiContainer;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.config.BooleanToggleElement;
import b100.lib.client.gui.config.ConfigElement;
import b100.lib.client.gui.config.IntegerTextFieldElement;
import b100.lib.client.mixin.IScreen;
import b100.lib.client.util.UpdateMode;

class ConfigElementTestScreen extends BasicScrollableScreen {
	
	public static Config config = new Config(new File(B100Lib.CONFIG_FOLDER, B100Lib.MODID + "_config_demo.properties"));
	
	public static IntProperty intProperty = config.register("intProperty", IntProperty.create(100));
	public static BooleanProperty booleanProperty = config.register("booleanProperty", BooleanProperty.create(false));
	
	static {
		config.load();
	}
	
	////////////////////////////////
	
	private boolean changed = false;

	public ConfigElementTestScreen(IScreen parentScreen) {
		super(parentScreen);
		
		title = B100LibClient.trans.asText("screen.configTest");
	}

	@Override
	public void initScrollElements() {
		scrollList.add(BooleanToggleElement.create(this, "b100lib.option.test.boolean", booleanProperty, UpdateMode.ON_UPDATE));
		scrollList.add(IntegerTextFieldElement.create(this, "b100lib.option.test.integer.textField", intProperty, UpdateMode.ON_UPDATE));
	}
	
	@Override
	public void elementAdded(GuiContainer parent, GuiElement element) {
		super.elementAdded(parent, element);
		if(element instanceof ConfigElement<?> configElement) {
			configElement.addConfigElementListener((e) -> changed = true);
		}
	}
	
	@Override
	public void onClose() {
		if(changed) {
			Print.print("Save: " + config.configFile.getAbsolutePath());
			config.save();	
		}
	}
}
