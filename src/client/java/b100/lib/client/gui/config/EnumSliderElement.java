package b100.lib.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import b100.lib.client.config.EnumProperty;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiEnumSlider;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.GuiSlider;
import b100.lib.client.gui.SliderListener;
import b100.lib.client.gui.config.base.AbstractOptionElement;
import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.util.UpdateMode;

public class EnumSliderElement<E extends Enum<E>> extends AbstractOptionElement implements ConfigElement<E>, SliderListener<E> {
	
	protected final E defaultValue;
	protected E initialValue;
	protected E value;

	protected final List<Consumer<E>> updateConsumers = new ArrayList<>();
	protected final List<Consumer<E>> saveConsumers = new ArrayList<>();
	
	public EnumSliderElement(GuiScreen screen, String key, Class<E> type, E value, E defaultValue) {
		super(screen, key);
		
		this.value = initialValue = value;
		this.defaultValue = defaultValue;
		
		GuiEnumSlider<E> slider = new GuiEnumSlider<E>(screen, type, value);
		slider.sliderListeners.add(this);
		element = add(slider);
		element.setSize(112, 20);
	}

	@Override
	public void sliderValueChanged(GuiSlider<E> slider, E value) {
		this.value = value;
		
		configElementListeners.forEach(listener -> listener.valueChanged(this));
		
		for(Consumer<E> consumer : updateConsumers) {
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
		for(Consumer<E> saveConsumer : saveConsumers) {
			saveConsumer.accept(value);
		}
	}

	public GuiElement addUpdateConsumer(Consumer<E> consumer) {
		updateConsumers.add(consumer);
		return this;
	}

	public boolean removeUpdateConsumer(Consumer<E> consumer) {
		return updateConsumers.remove(consumer);
	}

	public GuiElement addSaveConsumer(Consumer<E> consumer) {
		saveConsumers.add(consumer);
		return this;
	}

	public boolean removeSaveConsumer(Consumer<E> consumer) {
		return saveConsumers.remove(consumer);
	}
	
	////////////////////////////////
	
	public static <E extends Enum<E>> EnumSliderElement<E> create(GuiScreen screen, String key, EnumProperty<E> property, UpdateMode updateMode) {
		return create(screen, key, property.getType(), property.get(), property.getDefaultValue(), property::set, updateMode);
	}
	
	public static <E extends Enum<E>> EnumSliderElement<E> create(GuiScreen screen, String key, Class<E> type, E value, E defaultValue, Consumer<E> consumer, UpdateMode updateMode) {
		EnumSliderElement<E> element = new EnumSliderElement<>(screen, key, type, value, defaultValue);
		
		if(updateMode == UpdateMode.ON_SAVE) {
			element.addSaveConsumer(consumer);
		}else if(updateMode == UpdateMode.ON_UPDATE) {
			element.addUpdateConsumer(consumer);
		}
		
		return element;
	}
	
}
