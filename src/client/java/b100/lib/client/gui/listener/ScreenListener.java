package b100.lib.client.gui.listener;

import b100.lib.client.gui.screen.GuiScreen;

/**
 * If a GuiElement implementing a ScreenListener is added to a GuiScreen, it will automatically be registered 
 */
public interface ScreenListener {
	
	public void onScreenOpened(GuiScreen screen);

}
