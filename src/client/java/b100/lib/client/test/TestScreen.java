package b100.lib.client.test;

import b100.lib.client.B100LibClient;
import b100.lib.client.gui.element.GuiButton;
import b100.lib.client.mixin.IScreen;

class TestScreen extends BasicScrollableScreen {
	
	public TestScreen(IScreen parentScreen) {
		super(parentScreen);
	}
	
	@Override
	protected void onInit() {
		super.onInit();
		
		title = B100LibClient.trans.asText("screen.gui_demo");
	}
	
	@Override
	public void initScrollElements() {
		GuiButton button;
		
		button = new GuiButton(this, B100LibClient.trans.asText("screen.buttons"));
		button.addActionListener((e) -> utils.setScreen(new ButtonTestScreen(this)));
		scrollList.add(button);
		
		button = new GuiButton(this, B100LibClient.trans.asText("screen.miscElements"));
		button.addActionListener((e) -> utils.setScreen(new MiscElementsTestScreen(this)));
		scrollList.add(button);
		
		button = new GuiButton(this, B100LibClient.trans.asText("screen.translations"));
		button.addActionListener((e) -> utils.setScreen(new SelectTranslationFileScreen(this)));
		scrollList.add(button);
		
		button = new GuiButton(this, B100LibClient.trans.asText("screen.configTest"));
		button.addActionListener((e) -> utils.setScreen(new ConfigElementTestScreen(this)));
		scrollList.add(button);
	}
	
}
