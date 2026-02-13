package b100.lib.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import b100.lib.client.config.EnumProperty;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.config.base.AbstractButtonOptionElement;
import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.util.UpdateMode;
import net.minecraft.text.Text;

public class EnumToggleElement<E extends Enum<E>> extends AbstractButtonOptionElement implements ConfigElement<E> {

	protected final Class<E> type;
	protected final E defaultValue;
	protected E initialValue;
	protected E value;
	protected Function<E, Text> toTextFunction;
	
	protected final List<Consumer<E>> updateConsumers = new ArrayList<>();
	protected final List<Consumer<E>> saveConsumers = new ArrayList<>();
	
	public EnumToggleElement(GuiScreen screen, String key, Class<E> type, E value) {
		super(screen, key);
		
		this.type = type;
		this.value = initialValue = value;
		this.defaultValue = value;
		
		update();
	}

	@Override
	public void actionPerformed(GuiElement element) {
		value = getNextValue(value);

		configElementListeners.forEach(listener -> listener.valueChanged(this));
		
		for(Consumer<E> consumer : updateConsumers) {
			consumer.accept(value);
		}
		
		update();
	}
	
	protected E getNextValue(E value) {
		E[] allValues = type.getEnumConstants();
		for(int i=0; i < allValues.length; i++) {
			if(allValues[i] == value) {
				return allValues[(i + 1) % allValues.length];
			}
		}
		return allValues[0];
	}

	@Override
	public Text getButtonText() {
		if(toTextFunction != null) {
			return toTextFunction.apply(value);
		}
		return Text.literal(value.name());
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
		for(Consumer<E> consumer : saveConsumers) {
			consumer.accept(value);
		}
	}
	
	public EnumToggleElement<E> setToTextFunction(Function<E, Text> toTextFunction) {
		this.toTextFunction = toTextFunction;
		return this;
	}
	
	public Function<E, Text> getToTextFunction() {
		return toTextFunction;
	}

	public GuiElement addUpdateConsumer(Consumer<E> consumer) {
		this.updateConsumers.add(consumer);
		return this;
	}

	public boolean removeUpdateConsumer(Consumer<E> consumer) {
		return this.updateConsumers.remove(consumer);
	}

	public GuiElement addSaveConsumer(Consumer<E> consumer) {
		this.saveConsumers.add(consumer);
		return this;
	}

	public boolean removeSaveConsumer(Consumer<E> consumer) {
		return this.saveConsumers.remove(consumer);
	}
	
	////////////////////////////////
	
	public static <E extends Enum<E>> EnumToggleElement<E> create(GuiScreen screen, String key, EnumProperty<E> property, UpdateMode updateMode) {
		return create(screen, key, property.getType(), property.get(), property::set, updateMode);
	}
	
	public static <E extends Enum<E>> EnumToggleElement<E> create(GuiScreen screen, String key, Class<E> type, E value, Consumer<E> consumer, UpdateMode updateMode) {
		EnumToggleElement<E> element = new EnumToggleElement<E>(screen, key, type, value);
		
		if(updateMode == UpdateMode.ON_SAVE) {
			element.addSaveConsumer(consumer);
		}else if(updateMode == UpdateMode.ON_UPDATE) {
			element.addUpdateConsumer(consumer);
		}
		
		return element;
	}
	
}
