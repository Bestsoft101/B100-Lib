package b100.lib.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import b100.lib.client.config.FloatProperty;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.GuiFloatSlider;
import b100.lib.client.gui.GuiSlider;
import b100.lib.client.gui.SliderListener;
import b100.lib.client.gui.config.base.AbstractOptionElement;
import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.util.UpdateMode;

public class FloatSliderElement extends AbstractOptionElement implements ConfigElement<Float>, SliderListener<Float> {

	protected final float defaultValue;
	protected float initialValue;
	protected float value;

	protected final List<Consumer<Float>> updateConsumers = new ArrayList<>();
	protected final List<Consumer<Float>> saveConsumers = new ArrayList<>();
	
	public FloatSliderElement(GuiScreen screen, String key, float value) {
		this(screen, key, value, value);
	}
	
	public FloatSliderElement(GuiScreen screen, String key, float value, float defaultValue) {
		super(screen, key);
		
		this.value = initialValue = value;
		this.defaultValue = defaultValue;
		
		GuiFloatSlider slider = new GuiFloatSlider(screen, value);
		slider.sliderListeners.add(this);
		element = add(slider);
		element.setSize(112, 20);
	}
	
	@Override
	public void sliderValueChanged(GuiSlider<Float> slider, Float value) {
		this.value = value;
		
		configElementListeners.forEach(listener -> listener.valueChanged(this));
		
		for(Consumer<Float> consumer : updateConsumers) {
			consumer.accept(value);
		}
	}

	@Override
	public boolean isChanged() {
		return value != initialValue;
	}

	@Override
	public boolean isDefaultValue() {
		return value == defaultValue;
	}

	@Override
	public void resetToInitialValue() {
		value = initialValue;
	}

	@Override
	public void resetToDefaultValue() {
		value = defaultValue;
	}

	@Override
	public void save() {
		if(initialValue != value) {
			initialValue = value;
		}
		for(Consumer<Float> saveConsumer : saveConsumers) {
			saveConsumer.accept(value);
		}
	}

	public GuiElement addUpdateConsumer(Consumer<Float> consumer) {
		updateConsumers.add(consumer);
		return this;
	}

	public boolean removeUpdateConsumer(Consumer<Float> consumer) {
		return updateConsumers.remove(consumer);
	}

	public GuiElement addSaveConsumer(Consumer<Float> consumer) {
		saveConsumers.add(consumer);
		return this;
	}

	public boolean removeSaveConsumer(Consumer<Float> consumer) {
		return saveConsumers.remove(consumer);
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
