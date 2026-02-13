package b100.lib.client.test;

import b100.lib.client.B100LibClient;
import b100.lib.client.gui.element.GuiButton;
import b100.lib.client.mixin.IScreen;
import net.minecraft.text.Text;

class ButtonTestScreen extends BasicScrollableScreen {

	public ButtonTestScreen(IScreen parentScreen) {
		super(parentScreen);
	}
	
	@Override
	protected void onInit() {
		super.onInit();

		title = B100LibClient.trans.asText("screen.buttons");
	}

	@Override
	public void initScrollElements() {
		for(int i=0; i < 16; i++) {
			scrollList.add(new GuiButton(this, Text.of("Button " + (i + 1))));
		}
	}
	
}
