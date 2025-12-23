package b100.lib.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import b100.lib.client.B100LibClient;
import b100.lib.client.config.Property;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.util.UpdateMode;
import net.minecraft.text.Text;

public class BooleanToggleElement extends AbstractButtonOptionElement implements ConfigElement<Boolean> {
	
	protected boolean initialValue;
	protected boolean value;
	protected boolean defaultValue;
	protected Function<Boolean, Text> toTextFunction;

	protected final List<Consumer<Boolean>> updateConsumers = new ArrayList<>();
	protected final List<Consumer<Boolean>> saveConsumers = new ArrayList<>();
	
	public BooleanToggleElement(GuiScreen screen, String key, boolean value) {
		this(screen, key, value, value);
	}

	public BooleanToggleElement(GuiScreen screen, String key, boolean value, boolean defaultValue) {
		super(screen, key);
		this.value = initialValue = value;
		this.defaultValue = defaultValue;
		
		update();
	}
	
	@Override
	public void actionPerformed(GuiElement source) {
		value = !value;
		
		for(ConfigElementListener configElementListener : configElementListeners) {
			configElementListener.valueChanged(this);
		}
		
		update();
	}
	
	@Override
	public Text getButtonText() {
		if(toTextFunction != null) {
			return toTextFunction.apply(value);	
		}
		return Text.of(value ? "\247a" + B100LibClient.trans.asString("value.yes") : "\247c" + B100LibClient.trans.asString("value.no"));
	}
	
	public boolean getValue() {
		return value;
	}
	
	public BooleanToggleElement setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
	
	public boolean getDefaultValue() {
		return defaultValue;
	}

	@Override
	public boolean isChanged() {
		return value != initialValue;
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
	public boolean isDefaultValue() {
		return value == defaultValue;
	}

	@Override
	public void save() {
		if(initialValue != value) {
			initialValue = value;
		}
		for(Consumer<Boolean> saveConsumer : saveConsumers) {
			saveConsumer.accept(value);
		}
	}
	
	public BooleanToggleElement setToTextFunction(Function<Boolean, Text> toTextFunction) {
		this.toTextFunction = toTextFunction;
		update();
		return this;
	}
	
	public Function<Boolean, Text> getToTextFunction() {
		return toTextFunction;
	}

	public GuiElement addUpdateConsumer(Consumer<Boolean> consumer) {
		updateConsumers.add(consumer);
		return this;
	}

	public boolean removeUpdateConsumer(Consumer<Boolean> consumer) {
		return updateConsumers.remove(consumer);
	}

	public GuiElement addSaveConsumer(Consumer<Boolean> consumer) {
		saveConsumers.add(consumer);
		return this;
	}

	public boolean removeSaveConsumer(Consumer<Boolean> consumer) {
		return saveConsumers.remove(consumer);
	}
	
	////////////////////////////////
	
	public static BooleanToggleElement create(GuiScreen screen, String key, Property<Boolean> property, UpdateMode updateMode) {
		return create(screen, key, property.getValue(), property.getDefaultValue(), property::setValue, updateMode);
	}
	
	public static BooleanToggleElement create(GuiScreen screen, String key, boolean value, boolean defaultValue, Consumer<Boolean> consumer, UpdateMode updateMode) {
		BooleanToggleElement element = new BooleanToggleElement(screen, key, value, defaultValue);
		
		if(updateMode == UpdateMode.ON_SAVE) {
			element.addSaveConsumer(consumer);
		}else if(updateMode == UpdateMode.ON_UPDATE) {
			element.addUpdateConsumer(consumer);
		}
		
		return element;
	}
}
