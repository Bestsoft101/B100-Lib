package b100.lib.client.gui.config;

import b100.lib.client.gui.ActionListener;
import b100.lib.client.gui.GuiButton;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.ScreenListener;
import net.minecraft.text.Text;

public abstract class AbstractButtonOptionElement extends AbstractOptionElement implements ActionListener, ScreenListener {
	
	public AbstractButtonOptionElement(GuiScreen screen, String key) {
		super(screen, key);
		
		element = add(new GuiButton(screen, null).addActionListener(this));
		element.setSize(112, 20);
		
	}
	
	public void update() {
		getButton().text = getButtonText();
	}
	
	public abstract Text getButtonText();
	
	protected GuiButton getButton() {
		return (GuiButton) element;
	}

	@Override
	public void onScreenOpened(GuiScreen screen) {
		update();
	}
	
	public AbstractButtonOptionElement setTooltipText(Text tooltipText) {
		this.tooltipText = tooltipText;
		return this;
	}
	
	public Text getTooltipText() {
		return tooltipText;
	}
	
}
