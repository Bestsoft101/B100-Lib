package b100.lib.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import b100.lib.client.B100LibClient;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiScreen;
import net.minecraft.text.Text;

public class BooleanToggleElement extends AbstractButtonOptionElement implements ConfigElement<Boolean> {
	
	protected boolean initialValue;
	protected boolean value;
	protected boolean defaultValue;
	protected Function<Boolean, Text> toTextFunction;
	
	private final List<ConfigElementListener> configElementListeners = new ArrayList<>();
	private final List<Consumer<Boolean>> saveConsumers = new ArrayList<>();

	public BooleanToggleElement(GuiScreen screen, String key, boolean value) {
		super(screen, key);
		this.value = initialValue = defaultValue = value;
		
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
	public GuiElement addConfigElementListener(ConfigElementListener listener) {
		configElementListeners.add(listener);
		return this;
	}

	@Override
	public boolean removeConfigElementListener(ConfigElementListener listener) {
		return configElementListeners.remove(listener);
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

	@Override
	public GuiElement addSaveConsumer(Consumer<Boolean> saveListener) {
		saveConsumers.add(saveListener);
		return this;
	}

	@Override
	public boolean removeSaveConsumer(Consumer<Boolean> saveListener) {
		return saveConsumers.remove(saveListener);
	}
	
	public BooleanToggleElement setToTextFunction(Function<Boolean, Text> toTextFunction) {
		this.toTextFunction = toTextFunction;
		update();
		return this;
	}
	
	public Function<Boolean, Text> getToTextFunction() {
		return toTextFunction;
	}
}
