package b100.lib.client.gui;

import b100.lib.client.access.TextFieldWidgetAccess;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class GuiTextField extends GuiElement implements Focusable {

	public GuiScreen screen;
	
	private TextFieldWidget widget;
	
	private boolean focusable = true;
	
	private String previousText;
	
	public final ListenerList<FocusListener> focusListeners = new ListenerList<>(this);
	public final ListenerList<ActionListener> actionListeners = new ListenerList<>(this);
	
	public GuiTextField(GuiScreen screen, Text text) {
		this.screen = screen;
		
		widget = new TextFieldWidget(utils.textRenderer, 200, 20, text);
		widget.setChangedListener(this::textChanged);
		
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
		widget.render(utils.drawContext, 0, 0, 1.0f);
	}
	
	@Override
	public boolean keyEvent(int key, int scancode, int modifiers, boolean pressed) {
		if(pressed) {
			return widget.keyPressed(key, scancode, modifiers);
		}else {
			return widget.keyReleased(key, scancode, modifiers);
		}
	}
	
	@Override
	public boolean mouseEvent(int button, boolean pressed, double mouseX, double mouseY) {
		if(pressed) {
			if(screen.isMouseOver(this)) {
				if(isFocusable()) {
					setFocused(true);	
				}
				widget.mouseClicked(mouseX, mouseY, button);
				return true;
			}else {
				setFocused(false);
				return false;
			}
		}else {
			widget.mouseReleased(mouseX, mouseY, button);
			return false;
		}
	}
	
	@Override
	public void charEvent(char c, int modifiers) {
		widget.charTyped(c, modifiers);
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
		widget.setText(text);
	}
	
	public String getText() {
		return widget.getText();
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
	
	public boolean isEditable() {
		TextFieldWidgetAccess access = (TextFieldWidgetAccess) widget;
		
		return access.isFocusable();
	}
	
	public void setEditable(boolean editable) {
		widget.setEditable(editable);
	}
	
	@Deprecated
	public TextFieldWidget getWidget() {
		return widget;
	}
	
	@Override
	public String toString() {
		String text = "\"" + widget.getText() + "\"";
		
		return getClass().getSimpleName() + "[x=" + posX + ",y=" + posY + ",w=" + width + ",h=" + height + ",text=" + text + "]";
	}
	
}
