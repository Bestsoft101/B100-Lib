package b100.lib.client.test;

import b100.lib.client.gui.GuiScreen;
import b100.lib.client.mixin.IScreen;

public class B100LibTesting {
	
	public static GuiScreen getTestScreen(IScreen parentScreen) {
		return new TestScreen(parentScreen);
	}
	
}
