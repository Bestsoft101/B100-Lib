package b100.lib.client.gui.element;

import org.lwjgl.glfw.GLFW;

import b100.lib.client.gui.listener.FocusListener;
import b100.lib.client.gui.listener.SliderListener;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.gui.util.GuiColors;
import b100.lib.client.gui.util.ListenerList;
import b100.lib.client.gui.util.Textures;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;

public abstract class GuiSlider<T> extends GuiElement implements Focusable {
	
	public static final int HANDLE_WIDTH = 8;
	
	/** The screen that this slider is in */
	public final GuiScreen screen;
	
	/** Should the slider be editable or grayed out */
	private boolean slideable = true;
	
	/** When the slider is focused it's value can be changed using the keyboard */
	private boolean focused = false;

	protected boolean dragging;
	protected boolean selected;
	
	public final ListenerList<FocusListener> focusListeners = new ListenerList<>(this);
	public final ListenerList<SliderListener<T>> sliderListeners = new ListenerList<>(this);

	public GuiSlider(GuiScreen screen) {
		this.screen = screen;
		
		this.width = 200;
		this.height = 20;
	}
	
	////////////////////////////////
	
	public abstract T getValue();
	
	public abstract void setValue(T value);
	
	protected abstract Component getText(T value);
	
	protected abstract T getNextSliderValue(T prev, int direction, int modifiers);
	
	protected abstract float getSliderValueAsFloat(T value);
	
	protected abstract T convertFloatToSliderValue(float value);
	
	////////////////////////////////
	
	@Override
	public void draw() {
		final T value = getValue();
		
		if(dragging) {
			float mouseX = (float) screen.mouseX;
			float f = (mouseX - posX - (HANDLE_WIDTH / 2)) / (width - HANDLE_WIDTH);
			f = Mth.clamp(f, 0.0f, 1.0f);
			T newValue = convertFloatToSliderValue(f);
			setValue(newValue);
		}
		
		final boolean mouseOver = screen.isMouseOver(this);
		
		ResourceLocation backgroundTexture, handleTexture;
		int textColor;
		boolean textShadow;
		
		if(slideable) {
			textColor = GuiColors.INSTANCE.defaultText;
			textShadow = true;
			
			if(focused && !selected) {
				backgroundTexture = Textures.INSTANCE.sliderHighlighted;
			}else {
				backgroundTexture = Textures.INSTANCE.sliderNormal;
			}
			
			if(selected || dragging || mouseOver) {
				handleTexture = Textures.INSTANCE.sliderHandleHighlighted;
			}else {
				handleTexture = Textures.INSTANCE.sliderHandle;
			}
		}else {
			textColor = GuiColors.INSTANCE.disabledText;
			textShadow = false;
			
			backgroundTexture = Textures.INSTANCE.sliderNormal;
			handleTexture = Textures.INSTANCE.sliderHandle;
		}
		
		utils.drawGuiTexture(backgroundTexture, posX, posY, width, height);
		
		float floatValue = getSliderValueAsFloat(value);
		
		int handlePos = posX + (int) (floatValue * (width - HANDLE_WIDTH));
		utils.drawGuiTexture(handleTexture, handlePos, posY, HANDLE_WIDTH, height);
		
		utils.drawCenteredString(getText(value), posX + width / 2, posY + height / 2 - 4, textColor, textShadow);
	}
	
	@Override
	public boolean mouseEvent(int button, boolean pressed, double mouseX, double mouseY) {
		if(button == 0) {
			if(pressed && screen.isMouseOver(this) && slideable) {
				dragging = true;
				utils.playSound(SoundEvents.UI_BUTTON_CLICK);
				
				return true;
			}else if(!pressed) {
				dragging = false;
			}
		}
		return false;
	}
	
	@Override
	public boolean keyEvent(int key, int scancode, int modifiers, boolean pressed) {
		if(pressed && key == GLFW.GLFW_KEY_ENTER && focused) {
			selected = !selected;
			return true;
		}
		if(pressed && selected) {
			if(key == GLFW.GLFW_KEY_LEFT) {
				setValue(getNextSliderValue(getValue(), -1, modifiers));
				return true;
			}
			if(key == GLFW.GLFW_KEY_RIGHT) {
				setValue(getNextSliderValue(getValue(), 1, modifiers));
				return true;
			}
		}
		return false;
	}
	
	public void setSlideable(boolean slideable) {
		this.slideable = slideable;
	}
	
	public boolean isSlideable() {
		return slideable;
	}

	@Override
	public void setFocused(boolean focused) {
		if(focused != this.focused) {
			this.focused = focused;
			if(!focused) {
				selected = false;
			}
			
			focusListeners.forEach(listener -> listener.focusChanged(this));
		}
	}
	
	@Override
	public boolean isFocused() {
		return focused;
	}

	@Override
	public boolean isFocusable() {
		return slideable;
	}

	@Override
	public ListenerList<FocusListener> getFocusListeners() {
		return focusListeners;
	}
	
	protected void onValueChange(T value) {
		sliderListeners.forEach(listener -> listener.sliderValueChanged(this, value));
	}
	
}
