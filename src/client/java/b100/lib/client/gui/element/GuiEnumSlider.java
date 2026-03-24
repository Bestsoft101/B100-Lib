package b100.lib.client.gui.element;

import org.lwjgl.glfw.GLFW;

import b100.lib.client.gui.screen.GuiScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class GuiEnumSlider<E extends Enum<E>> extends GuiSlider<E> {

	public final Class<E> type;
	
	protected E sliderValue;
	
	public GuiEnumSlider(GuiScreen screen, Class<E> type, E value) {
		super(screen);
		
		this.type = type;
		
		setValue(value);
	}

	@Override
	public E getValue() {
		return sliderValue;
	}

	@Override
	public void setValue(E value) {
		if(sliderValue != value) {
			sliderValue = value;
			onValueChange(value);
		}
	}

	@Override
	protected Component getText(E value) {
		return Component.nullToEmpty(value.name());
	}

	@Override
	protected E getNextSliderValue(E prev, int direction, int modifiers) {
		int index = indexOf(sliderValue);
		
		final boolean ctrl = (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;
		final boolean shift = (modifiers & GLFW.GLFW_MOD_SHIFT) != 0;
		
		int interval = 1;
		if(ctrl && shift) {
			interval = 100;
		}else if(ctrl) {
			interval = 10;
		}
		
		return getValue(index + direction * interval);
	}

	@Override
	protected float getSliderValueAsFloat(E value) {
		final int steps = type.getEnumConstants().length - 1;
		final int index = indexOf(value);
		
		return Mth.clamp(index / (float) steps, 0.0f, 1.0f);
	}

	@Override
	protected E convertFloatToSliderValue(float value) {
		final int steps = type.getEnumConstants().length - 1;
		
		return getValue(Mth.clamp(Math.round(value * steps), 0, steps));
	}
	
	protected int indexOf(E value) {
		E[] values = type.getEnumConstants();
		for(int i=0; i < values.length; i++) {
			if(values[i] == value) {
				return i;
			}
		}
		return 0;
	}
	
	protected E getValue(int index) {
		return type.getEnumConstants()[index];
	}
	
}
