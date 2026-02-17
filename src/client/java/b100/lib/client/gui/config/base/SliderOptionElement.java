package b100.lib.client.gui.config.base;

import b100.lib.client.gui.element.GuiSlider;
import b100.lib.client.gui.listener.SliderListener;
import b100.lib.client.gui.screen.GuiScreen;

public abstract class SliderOptionElement<T> extends OptionElement<T, GuiSlider<T>> implements SliderListener<T> {

	public SliderOptionElement(GuiScreen screen, String key, T value, T defaultValue) {
		super(screen, key, value, defaultValue);
	}
	
	@Override
	protected void initOptionElement(GuiSlider<T> element) {
		super.initOptionElement(element);
		
		element.sliderListeners.add(this);
	}
	
	@Override
	public boolean isEnabled() {
		return getOptionElement().isSlideable();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		getOptionElement().setSlideable(enabled);
	}
	
}
