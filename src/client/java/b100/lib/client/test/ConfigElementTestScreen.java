package b100.lib.client.test;

import b100.lib.client.B100LibClient;
import b100.lib.client.gui.config.BooleanToggleElement;
import b100.lib.client.gui.config.IntegerTextFieldElement;
import b100.lib.client.mixin.IScreen;

class ConfigElementTestScreen extends BasicScrollableScreen {

	public ConfigElementTestScreen(IScreen parentScreen) {
		super(parentScreen);
		
		title = B100LibClient.trans.asText("screen.configTest");
	}

	@Override
	public void initScrollElements() {
		BooleanToggleElement booleanToggleElement = new BooleanToggleElement(this, "b100lib.option.test.boolean", false);
		scrollList.add(booleanToggleElement);
		
		IntegerTextFieldElement integerTextFieldElement = new IntegerTextFieldElement(this, "b100lib.option.test.integer.textField", 1000);
		scrollList.add(integerTextFieldElement);
	}
	
}
