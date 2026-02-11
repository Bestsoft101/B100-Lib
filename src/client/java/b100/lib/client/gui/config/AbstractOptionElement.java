package b100.lib.client.gui.config;

import b100.lib.client.gui.Focusable;
import b100.lib.client.gui.GuiContainer;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.ListenerList;
import b100.lib.client.translate.Translations;
import net.minecraft.text.Text;

public abstract class AbstractOptionElement extends GuiContainer {

	protected GuiScreen screen;
	protected Text name;
	protected Text tooltipText;
	protected GuiElement element;
	
	protected final ListenerList<ConfigElementListener> configElementListeners = new ListenerList<>(this);
	
	public AbstractOptionElement(GuiScreen screen, String key) {
		this.screen = screen;
		
		this.name = Translations.INSTANCE.asText(key);
		
		String tooltipText = Translations.INSTANCE.asStringOrNull(key + ".tooltip");
		if(tooltipText != null) {
			this.tooltipText = Text.of(tooltipText);
		}
		
		setSize(320, 24);
	}
	
	@Override
	public void onResize() {
		element.setPosition(posX + width - element.width - 2, posY + height / 2 - element.height / 2);
		
		super.onResize();
	}
	
	@Override
	public void draw() {
		GuiElement mouseOver = screen.getMouseOver();
		if(mouseOver == this || contains(mouseOver) || Focusable.isFocused(element)) {
			utils.drawRectangle(posX, posY, width, height, 0x20FFFFFF);
			
			if(tooltipText != null) {
				screen.drawWrappedTooltip(tooltipText);
			}
		}
		
		super.draw();
		
		utils.drawString(name, posX + 8, posY + height / 2 - 4, 0xFFFFFF, true);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

	public GuiElement addConfigElementListener(ConfigElementListener listener) {
		configElementListeners.add(listener);
		return this;
	}

	public boolean removeConfigElementListener(ConfigElementListener listener) {
		return configElementListeners.remove(listener);
	}
}
