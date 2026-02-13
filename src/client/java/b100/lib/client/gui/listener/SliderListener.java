package b100.lib.client.gui.listener;

import b100.lib.client.gui.element.GuiSlider;

public interface SliderListener<T> {
	
	public void sliderValueChanged(GuiSlider<T> slider, T value);
	
}
