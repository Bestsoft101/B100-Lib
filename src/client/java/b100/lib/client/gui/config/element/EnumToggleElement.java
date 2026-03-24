package b100.lib.client.gui.config.element;

import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import b100.lib.client.gui.config.base.ButtonOptionElement;
import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.util.UpdateMode;
import b100.lib.config.property.EnumProperty;

public class EnumToggleElement<E extends Enum<E>> extends ButtonOptionElement<E> implements ConfigElement<E> {

	protected final Class<E> type;
	protected Function<E, Component> toTextFunction;
	
	public EnumToggleElement(GuiScreen screen, String key, Class<E> type, E value, E defaultValue) {
		super(screen, key, value, defaultValue);
		
		this.type = type;
		
		update();
	}

	@Override
	public void onClick(int mouseButton) {
		value = getNextValue(value, mouseButton == 1 ? -1 : 1);

		configElementListeners.forEach(listener -> listener.valueChanged(this));
		
		for(Consumer<E> consumer : updateConsumers) {
			consumer.accept(value);
		}
		
		update();
	}
	
	protected E getNextValue(E value, int direction) {
		E[] allValues = type.getEnumConstants();
		for(int i=0; i < allValues.length; i++) {
			if(allValues[i] == value) {
				return allValues[(i + direction + allValues.length) % allValues.length];
			}
		}
		return allValues[0];
	}

	@Override
	public Component getButtonText() {
		if(toTextFunction != null) {
			return toTextFunction.apply(value);
		}
		return Component.literal(value.name());
	}
	
	public EnumToggleElement<E> setToTextFunction(Function<E, Component> toTextFunction) {
		this.toTextFunction = toTextFunction;
		return this;
	}
	
	public Function<E, Component> getToTextFunction() {
		return toTextFunction;
	}
	
	////////////////////////////////
	
	public static <E extends Enum<E>> EnumToggleElement<E> create(GuiScreen screen, String key, EnumProperty<E> property, UpdateMode updateMode) {
		return create(screen, key, property.getType(), property.get(), property.getDefaultValue(), property::set, updateMode);
	}
	
	public static <E extends Enum<E>> EnumToggleElement<E> create(GuiScreen screen, String key, Class<E> type, E value, E defaultValue, Consumer<E> consumer, UpdateMode updateMode) {
		EnumToggleElement<E> element = new EnumToggleElement<E>(screen, key, type, value, defaultValue);
		
		if(updateMode == UpdateMode.ON_SAVE) {
			element.addSaveConsumer(consumer);
		}else if(updateMode == UpdateMode.ON_UPDATE) {
			element.addUpdateConsumer(consumer);
		}
		
		return element;
	}
	
}
