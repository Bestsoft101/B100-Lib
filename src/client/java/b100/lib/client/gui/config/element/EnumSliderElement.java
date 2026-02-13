package b100.lib.client.gui.config.element;

import java.util.function.Consumer;

import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.gui.config.base.SliderOptionElement;
import b100.lib.client.gui.element.GuiEnumSlider;
import b100.lib.client.gui.element.GuiSlider;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.util.UpdateMode;
import b100.lib.config.property.EnumProperty;

public class EnumSliderElement<E extends Enum<E>> extends SliderOptionElement<E> implements ConfigElement<E> {
	
	public final Class<E> type;
	
	public EnumSliderElement(GuiScreen screen, String key, Class<E> type, E value, E defaultValue) {
		super(screen, key, value, defaultValue);
		
		this.type = type;
	}

	@Override
	protected GuiSlider<E> createOptionElement() {
		return new GuiEnumSlider<E>(screen, type, defaultValue);
	}

	@Override
	public void sliderValueChanged(GuiSlider<E> slider, E value) {
		this.value = value;
		
		configElementListeners.forEach(listener -> listener.valueChanged(this));
		
		for(Consumer<E> consumer : updateConsumers) {
			consumer.accept(value);
		}
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
