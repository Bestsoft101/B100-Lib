package b100.lib.client.test;

import b100.lib.client.B100LibClient;
import b100.lib.client.config.BooleanProperty;
import b100.lib.client.config.IntProperty;
import b100.lib.client.gui.config.BooleanToggleElement;
import b100.lib.client.gui.config.IntegerTextFieldElement;
import b100.lib.client.mixin.IScreen;
import b100.lib.client.util.UpdateMode;

class ConfigElementTestScreen extends BasicScrollableScreen {
	
	public static IntProperty intProperty = new IntProperty(100);
	public static BooleanProperty booleanProperty = new BooleanProperty(false);

	public ConfigElementTestScreen(IScreen parentScreen) {
		super(parentScreen);
		
		title = B100LibClient.trans.asText("screen.configTest");
	}

	@Override
	public void initScrollElements() {
		scrollList.add(BooleanToggleElement.create(this, "b100lib.option.test.boolean", booleanProperty, UpdateMode.ON_UPDATE));
		scrollList.add(IntegerTextFieldElement.create(this, "b100lib.option.test.integer.textField", intProperty, UpdateMode.ON_UPDATE));
	}
	
}
