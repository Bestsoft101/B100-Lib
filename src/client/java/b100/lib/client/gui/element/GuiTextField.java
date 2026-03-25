package b100.lib.client.gui.element;

import b100.lib.client.access.TextFieldWidgetAccess;
import b100.lib.client.gui.listener.ActionListener;
import b100.lib.client.gui.listener.FocusListener;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.gui.util.GuiColors;
import b100.lib.client.gui.util.ListenerList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.network.chat.Component;

public class GuiTextField extends GuiElement implements Focusable {

	public final GuiScreen screen;
	
	private EditBox widget;
	
	private boolean focusable = true;
	
	private String previousText;
	
	public final ListenerList<FocusListener> focusListeners = new ListenerList<>(this);
	public final ListenerList<ActionListener> actionListeners = new ListenerList<>(this);
	
	public GuiTextField(GuiScreen screen, Component text) {
		this.screen = screen;
		
		widget = new EditBox(utils.getFont(), 200, 20, text);
		widget.setResponder(this::textChanged);
		widget.setTextColor(GuiColors.INSTANCE.defaultText);
		widget.setTextColorUneditable(GuiColors.INSTANCE.disabledText);
		
		width = 200;
		height = 20;
	}
	
	@Override
	public void draw() {
		if(widget.getX() != posX || widget.getY() != posY) {
			widget.setPosition(posX, posY);
		}
		if(widget.getWidth() != width) {
			widget.setWidth(width);
		}
		if(widget.getHeight() != height) {
			widget.setHeight(height);
		}
		widget.extractRenderState(utils.getGraphics(), 0, 0, 1.0f);
	}
	
	@Override
	public boolean keyEvent(int key, int scancode, int modifiers, boolean pressed) {
		if(pressed) {
			return widget.keyPressed(new KeyEvent(key, scancode, modifiers));
		}else {
			return widget.keyReleased(new KeyEvent(key, scancode, modifiers));
		}
	}
	
	@Override
	public boolean mouseEvent(int button, boolean pressed, double mouseX, double mouseY) {
		if(pressed) {
			if(screen.isMouseOver(this)) {
				if(isFocusable()) {
					setFocused(true);	
				}
				widget.mouseClicked(new MouseButtonEvent(mouseX, mouseY, new MouseButtonInfo(button, 0)), false);
				return true;
			}else {
				setFocused(false);
				return false;
			}
		}else {
			widget.mouseReleased(new MouseButtonEvent(mouseX, mouseY, new MouseButtonInfo(button, 0)));
			return false;
		}
	}
	
	@Override
	public void charEvent(char c, int modifiers) {
		widget.charTyped(new CharacterEvent(c));
	}
	
	private void textChanged(String text) {
		if(previousText == null || !previousText.equals(text)) {
			previousText = text;
			actionListeners.forEach((listener) -> listener.actionPerformed(this));
		}
	}

	@Override
	public void setFocused(boolean focused) {
		if(focused != widget.isFocused()) {
			widget.setFocused(focused);
			focusListeners.forEach((listener) -> listener.focusChanged(this));
		}
	}
	
	public void setText(String text) {
		widget.setValue(text);
	}
	
	public String getText() {
		return widget.getValue();
	}

	@Override
	public boolean isFocused() {
		return widget.isFocused();
	}
	
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}

	@Override
	public boolean isFocusable() {
		return focusable;
	}

	@Override
	public ListenerList<FocusListener> getFocusListeners() {
		return focusListeners;
	}
	
	public ListenerList<ActionListener> getActionListeners() {
		return actionListeners;
	}
	
	public GuiTextField addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
		return this;
	}
	
	public boolean removeActionListener(ActionListener actionListener) {
		return actionListeners.remove(actionListener);
	}
	
	public boolean isEditable() {
		TextFieldWidgetAccess access = (TextFieldWidgetAccess) widget;
		
		return access.isFocusable();
	}
	
	public void setEditable(boolean editable) {
		widget.setEditable(editable);
	}
	
	@Deprecated
	public EditBox getWidget() {
		return widget;
	}
	
	@Override
	public String toString() {
		String text = "\"" + widget.getValue() + "\"";
		
		return getClass().getSimpleName() + "[x=" + posX + ",y=" + posY + ",w=" + width + ",h=" + height + ",text=" + text + "]";
	}
	
}
