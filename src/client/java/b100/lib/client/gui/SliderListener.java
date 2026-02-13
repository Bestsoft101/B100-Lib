package b100.lib.client.gui;

public interface SliderListener<T> {
	
	public void sliderValueChanged(GuiSlider<T> slider, T value);
	
}
