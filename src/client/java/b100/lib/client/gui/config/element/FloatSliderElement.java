package b100.lib.client.gui.config.element;

import java.util.function.Consumer;

import b100.lib.client.config.FloatProperty;
import b100.lib.client.gui.GuiFloatSlider;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.GuiSlider;
import b100.lib.client.gui.SliderListener;
import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.gui.config.base.SliderOptionElement;
import b100.lib.client.util.UpdateMode;

public class FloatSliderElement extends SliderOptionElement<Float> implements ConfigElement<Float>, SliderListener<Float> {

	public FloatSliderElement(GuiScreen screen, String key, float value, float defaultValue) {
		super(screen, key, value, defaultValue);
	}

	@Override
	protected GuiSlider<Float> createOptionElement() {
		return new GuiFloatSlider(screen, value);
	}
	
	@Override
	public void sliderValueChanged(GuiSlider<Float> slider, Float value) {
		this.value = value;
		
		configElementListeners.forEach(listener -> listener.valueChanged(this));
		
		for(Consumer<Float> consumer : updateConsumers) {
			consumer.accept(value);
		}
	}
	
	////////////////////////////////
	
	public static FloatSliderElement create(GuiScreen screen, String key, FloatProperty property, UpdateMode updateMode) {
		return create(screen, key, property.getFloat(), property.getDefaultValue(), property::setFloat, updateMode);
	}
	
	public static FloatSliderElement create(GuiScreen screen, String key, float value, float defaultValue, Consumer<Float> consumer, UpdateMode updateMode) {
		FloatSliderElement element = new FloatSliderElement(screen, key, value, defaultValue);
		
		if(updateMode == UpdateMode.ON_SAVE) {
			element.addSaveConsumer(consumer);
		}else if(updateMode == UpdateMode.ON_UPDATE) {
			element.addUpdateConsumer(consumer);
		}
		
		return element;
	}
	
}
