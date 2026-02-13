package b100.lib.client.gui.element;

import org.lwjgl.glfw.GLFW;

import b100.lib.client.gui.screen.GuiScreen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class GuiIntegerSlider extends GuiSlider<Integer> {

	public final int offset;
	public final int steps;
	
	protected int sliderValue;
	
	public GuiIntegerSlider(GuiScreen screen, int minValue, int maxValue, int value) {
		super(screen);
		
		int steps = (maxValue - minValue) + 1;
		if(steps < 2) {
			throw new RuntimeException("Number of slider steps must be at least 2, got " + steps);
		}
		
		this.offset = minValue;
		this.steps = steps;
		
		setValue(value);
	}

	@Override
	public Integer getValue() {
		return sliderValue;
	}

	@Override
	public void setValue(Integer value) {
		if(sliderValue != value) {
			sliderValue = MathHelper.clamp(value, offset, offset + steps - 1);
			onValueChange(value);
		}
	}
	
	@Override
	protected Text getText(Integer value) {
		return Text.of(String.valueOf(value));
	}
	
	@Override
	protected Integer getNextSliderValue(Integer prev, int direction, int modifiers) {
		final boolean ctrl = (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;
		final boolean shift = (modifiers & GLFW.GLFW_MOD_SHIFT) != 0;
		
		int interval = 1;
		if(ctrl && shift) {
			interval = 100;
		}else if(ctrl) {
			interval = 10;
		}
		
		return prev + direction * interval;
	}
	
	@Override
	protected float getSliderValueAsFloat(Integer value) {
		return MathHelper.clamp((value - offset) / (float) (steps - 1), 0.0f, 1.0f);
	}

	@Override
	protected Integer convertFloatToSliderValue(float value) {
		return MathHelper.clamp(Math.round(value * (steps - 1)), 0, steps - 1) + offset;
	}
	
}
