package b100.lib.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import b100.lib.client.config.IntProperty;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiIntegerSlider;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.GuiSlider;
import b100.lib.client.gui.SliderListener;
import b100.lib.client.gui.config.base.AbstractOptionElement;
import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.util.UpdateMode;

public class IntegerSliderElement extends AbstractOptionElement implements ConfigElement<Integer>, SliderListener<Integer> {

	protected final int defaultValue;
	protected int initialValue;
	protected int value;

	protected final List<Consumer<Integer>> updateConsumers = new ArrayList<>();
	protected final List<Consumer<Integer>> saveConsumers = new ArrayList<>();

	public IntegerSliderElement(GuiScreen screen, String key, int minValue, int maxValue, int value) {
		this(screen, key, minValue, maxValue, value, value);
	}

	public IntegerSliderElement(GuiScreen screen, String key, int minValue, int maxValue, int value, int defaultValue) {
		super(screen, key);
		
		this.value = initialValue = value;
		this.defaultValue = defaultValue;
		
		GuiIntegerSlider slider = new GuiIntegerSlider(screen, minValue, maxValue, value);
		slider.sliderListeners.add(this);
		element = add(slider);
		element.setSize(112, 20);
	}

	@Override
	public void sliderValueChanged(GuiSlider<Integer> slider, Integer value) {
		this.value = value;
		
		configElementListeners.forEach(listener -> listener.valueChanged(this));
		
		for(Consumer<Integer> consumer : updateConsumers) {
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
		for(Consumer<Integer> saveConsumer : saveConsumers) {
			saveConsumer.accept(value);
		}
	}

	public GuiElement addUpdateConsumer(Consumer<Integer> consumer) {
		updateConsumers.add(consumer);
		return this;
	}

	public boolean removeUpdateConsumer(Consumer<Integer> consumer) {
		return updateConsumers.remove(consumer);
	}

	public GuiElement addSaveConsumer(Consumer<Integer> consumer) {
		saveConsumers.add(consumer);
		return this;
	}

	public boolean removeSaveConsumer(Consumer<Integer> consumer) {
		return saveConsumers.remove(consumer);
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
