package b100.lib.client.gui.element;

import net.minecraft.network.chat.Component;

public class GuiTextElement extends GuiElement {
	
	public Component text;
	public boolean shadow;
	public int color;
	
	public GuiTextElement(Component text, boolean shadow, int color) {
		this.text = text;
		this.shadow = shadow;
		this.color = color;
	}
	
	@Override
	public void draw() {
		utils.drawCenteredText(text, posX + width / 2, posY + height / 2 - 4, color, shadow);
	}
	
}