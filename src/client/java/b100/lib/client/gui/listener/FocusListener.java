package b100.lib.client.gui.listener;

import b100.lib.client.gui.element.Focusable;
import b100.lib.client.gui.element.GuiElement;

/**
 * All {@link GuiElement}s implementing {@link FocusListener} will automatically receive focus changes for all elements on the same screen
 */
public interface FocusListener {
	
	public void focusChanged(Focusable focusable);

}
