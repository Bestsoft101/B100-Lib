package b100.lib.client.gui.config;

import b100.lib.client.gui.ActionListener;
import b100.lib.client.gui.GuiButton;
import b100.lib.client.gui.GuiContainer;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.ScreenListener;
import b100.lib.client.translate.Translations;
import net.minecraft.text.Text;

public abstract class AbstractButtonOptionElement extends GuiContainer implements ActionListener, ScreenListener {

	protected GuiScreen screen;
	protected Text name;
	protected Text tooltipText;
	protected GuiButton button;
	
	public AbstractButtonOptionElement(GuiScreen screen, String key) {
		this.screen = screen;
		
		this.name = Translations.INSTANCE.asText(key);
		
		String tooltipText = Translations.INSTANCE.asStringOrNull(key + ".tooltip");
		if(tooltipText != null) {
			this.tooltipText = Text.of(tooltipText);
		}
		
		button = add(new GuiButton(screen, null).addActionListener(this));
		button.setSize(112, 20);
		
		setSize(320, 24);
	}
	
	@Override
	public void draw() {
		GuiElement mouseOver = button.screen.getMouseOver();
		if(mouseOver == this || contains(mouseOver) || button.isFocused()) {
			utils.drawRectangle(posX, posY, width, height, 0x20FFFFFF);
			
			if(tooltipText != null) {
				screen.drawWrappedTooltip(tooltipText);
			}
		}
		
		super.draw();
		
		utils.drawString(name, posX + 8, posY + height / 2 - 4, 0xFFFFFF, true);
	}
	
	@Override
	public void onResize() {
		button.setPosition(posX + width - button.width - 2, posY + height / 2 - button.height / 2);
		
		super.onResize();
	}
	
	public void update() {
		button.text = getButtonText();
	}
	
	public abstract Text getButtonText();
	
	@Override
	public boolean isSolid() {
		return true;
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
