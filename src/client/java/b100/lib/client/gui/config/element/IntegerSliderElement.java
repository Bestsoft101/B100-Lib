package b100.lib.client.gui.config.element;

import java.util.function.Consumer;

import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.gui.config.base.SliderOptionElement;
import b100.lib.client.gui.element.GuiIntegerSlider;
import b100.lib.client.gui.element.GuiSlider;
import b100.lib.client.gui.listener.SliderListener;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.util.UpdateMode;
import b100.lib.config.property.IntProperty;

public class IntegerSliderElement extends SliderOptionElement<Integer> implements ConfigElement<Integer>, SliderListener<Integer> {

	public final int minValue;
	public final int maxValue;
	
	public IntegerSliderElement(GuiScreen screen, String key, int minValue, int maxValue, int value, int defaultValue) {
		super(screen, key, value, defaultValue);

		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	protected GuiSlider<Integer> createOptionElement() {
		return new GuiIntegerSlider(screen, minValue, maxValue, value);
	}

	@Override
	public void sliderValueChanged(GuiSlider<Integer> slider, Integer value) {
		this.value = value;
		
		configElementListeners.forEach(listener -> listener.valueChanged(this));
		
		for(Consumer<Integer> consumer : updateConsumers) {
			consumer.accept(value);
		}
	}
	
	////////////////////////////////
	
	public static IntegerSliderElement create(GuiScreen screen, String key, int minValue, int maxValue, IntProperty property, UpdateMode updateMode) {
		return create(screen, key, minValue, maxValue, property.getInt(), property.getDefaultValue(), property::setInt, updateMode);
	}
	
	public static IntegerSliderElement create(GuiScreen screen, String key, int minValue, int maxValue, int value, int defaultValue, Consumer<Integer> consumer, UpdateMode updateMode) {
		IntegerSliderElement element = new IntegerSliderElement(screen, key, minValue, maxValue, value, defaultValue);
		
		if(updateMode == UpdateMode.ON_SAVE) {
			element.addSaveConsumer(consumer);
		}else if(updateMode == UpdateMode.ON_UPDATE) {
			element.addUpdateConsumer(consumer);
		}
		
		return element;
	}
	
}
