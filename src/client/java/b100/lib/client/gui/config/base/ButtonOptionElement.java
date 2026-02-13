package b100.lib.client.gui.config.base;

import b100.lib.client.gui.ActionListener;
import b100.lib.client.gui.GuiButton;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.ScreenListener;
import net.minecraft.text.Text;

public abstract class ButtonOptionElement<T extends Comparable<T>> extends OptionElement<T, GuiButton> implements ActionListener, ScreenListener {
	
	public ButtonOptionElement(GuiScreen screen, String key, T value, T defaultValue) {
		super(screen, key, value, defaultValue);
	}
	
	@Override
	protected void initOptionElement(GuiButton element) {
		super.initOptionElement(element);

		element.addActionListener(this);
	}
	
	@Override
	protected GuiButton createOptionElement() {
		return new GuiButton(screen, null);
	}
	
	public abstract Text getButtonText();
	
	public void update() {
		getOptionElement().text = getButtonText();
	}

	@Override
	public void onScreenOpened(GuiScreen screen) {
		update();
	}
	
}
