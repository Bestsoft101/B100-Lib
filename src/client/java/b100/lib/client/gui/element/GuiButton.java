package b100.lib.client.gui.element;

import org.lwjgl.glfw.GLFW;

import b100.lib.client.gui.listener.ActionListener;
import b100.lib.client.gui.listener.FocusListener;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.gui.util.GuiColors;
import b100.lib.client.gui.util.ListenerList;
import b100.lib.client.gui.util.Textures;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GuiButton extends GuiElement implements Focusable {
	
	/** The screen that this button is in */
	public final GuiScreen screen;
	
	/** The text of this button. Can be null. */
	public Text text;
	
	/** Should the button be clickable or grayed out */
	private boolean clickable = true;
	
	/** When the button is focused it can be clicked with Space and Enter */
	private boolean focused = false;
	
	public final ListenerList<ActionListener> actionListeners = new ListenerList<>(this);
	public final ListenerList<FocusListener> focusListeners = new ListenerList<>(this);
	
	public GuiButton(GuiScreen screen, Text text) {
		this.screen = screen;
		this.text = text;
		
		this.width = 200;
		this.height = 20;
	}
	
	@Override
	public void draw() {
		Identifier texture;
		int textColor;
		boolean textShadow;
		
		if(clickable) {
			textColor = GuiColors.INSTANCE.defaultText;
			textShadow = true;
			
			if(focused || screen.isMouseOver(this)) {
				textColor = GuiColors.INSTANCE.hoveredText;
				texture = Textures.INSTANCE.buttonHover;
			}else {
				texture = Textures.INSTANCE.buttonNormal;
			}
		}else {
			textColor = GuiColors.INSTANCE.disabledText;
			textShadow = false;
			
			texture = Textures.INSTANCE.buttonDisabled;
		}
		
		utils.drawGuiTexture(texture, posX, posY, width, height);
		
		if(text != null) {
			int textWidth = utils.textRenderer.getWidth(text);
			int textX = posX + (width - textWidth) / 2;
			int textY = posY + height / 2 - 4;
			utils.drawString(text, textX, textY, textColor, textShadow);
		}
	}
	
	@Override
	public boolean keyEvent(int key, int scancode, int modifiers, boolean pressed) {
		if(pressed && focused && (key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_SPACE)) {
			final boolean shift = (modifiers & GLFW.GLFW_MOD_SHIFT) != 0;
			
			clickButton(shift ? 1 : 0);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean mouseEvent(int button, boolean pressed, double mouseX, double mouseY) {
		if(clickable && pressed && screen.isMouseOver(this)) {
			clickButton(button);
			return true;
		}
		
		return super.mouseEvent(button, pressed, mouseX, mouseY);
	}
	
	public void clickButton(int mouseButton) {
		utils.playSound(SoundEvents.UI_BUTTON_CLICK);
		actionListeners.forEach((listener) -> listener.actionPerformed(this));
	}
	
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
	
	public boolean isClickable() {
		return clickable;
	}
	
	@Override
	public void setFocused(boolean focused) {
		if(focused != this.focused) {
			this.focused = focused;
			focusListeners.forEach((listener) -> listener.focusChanged(this));
		}
	}

	@Override
	public boolean isFocused() {
		return focused;
	}

	@Override
	public boolean isFocusable() {
		return clickable;
	}

	@Override
	public ListenerList<FocusListener> getFocusListeners() {
		return focusListeners;
	}
	
	public ListenerList<ActionListener> getActionListeners() {
		return actionListeners;
	}
	
	public GuiButton addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
		return this;
	}
	
	public boolean removeActionListener(ActionListener actionListener) {
		return actionListeners.remove(actionListener);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[x=" + posX + ",y=" + posY + ",w=" + width + ",h=" + height + ",text=" + (text != null ? text.getString() : null) + "]";
	}

}
