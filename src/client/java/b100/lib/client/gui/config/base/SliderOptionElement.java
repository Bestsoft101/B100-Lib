package b100.lib.client.gui.config.base;

import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.GuiSlider;
import b100.lib.client.gui.SliderListener;

public abstract class SliderOptionElement<T extends Comparable<T>> extends OptionElement<T, GuiSlider<T>> implements SliderListener<T> {

	public SliderOptionElement(GuiScreen screen, String key, T value, T defaultValue) {
		super(screen, key, value, defaultValue);
	}
	
	@Override
	protected void initOptionElement(GuiSlider<T> element) {
		super.initOptionElement(element);
		
		element.sliderListeners.add(this);
	}
	
}
