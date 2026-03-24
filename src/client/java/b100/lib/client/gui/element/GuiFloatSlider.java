package b100.lib.client.gui.element;

import org.lwjgl.glfw.GLFW;

import b100.lib.client.gui.screen.GuiScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class GuiFloatSlider extends GuiSlider<Float> {
	
	protected float sliderValue;
	
	public GuiFloatSlider(GuiScreen screen, float value) {
		super(screen);
		
		setValue(value);
	}

	@Override
	public Float getValue() {
		return sliderValue;
	}

	@Override
	public void setValue(Float value) {
		if(sliderValue != value) {
			sliderValue = Mth.clamp(value, 0.0f, 1.0f);
			onValueChange(value);
		}
	}
	
	@Override
	protected Component getText(Float value) {
		int percent = Math.round(sliderValue * 100.0f);
		
		return Component.nullToEmpty(percent + "%");
	}
	
	@Override
	protected Float getNextSliderValue(Float prev, int direction, int modifiers) {
		final boolean ctrl = (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;
		final boolean shift = (modifiers & GLFW.GLFW_MOD_SHIFT) != 0;
		
		float interval = 0.01f;
		if(ctrl && shift) {
			interval = 1.0f;
			
		}else if(ctrl && !shift) {
			interval = 0.1f;
			
		}else if(shift && !ctrl) {
			interval = 0.001f;
		}
		
		return prev + interval * direction;
	}

	@Override
	protected float getSliderValueAsFloat(Float value) {
		return sliderValue;
	}

	@Override
	protected Float convertFloatToSliderValue(float value) {
		return value;
	}
	
}
