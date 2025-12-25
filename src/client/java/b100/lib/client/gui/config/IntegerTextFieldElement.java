package b100.lib.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import b100.lib.client.config.IntProperty;
import b100.lib.client.gui.ActionListener;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.GuiTextField;
import b100.lib.client.util.UpdateMode;
import net.minecraft.text.Text;

public class IntegerTextFieldElement extends AbstractOptionElement implements ActionListener, ConfigElement<Integer> {
	
	protected int initialValue;
	protected int value;
	protected int defaultValue;

	protected final List<Consumer<Integer>> updateConsumers = new ArrayList<>();
	protected final List<Consumer<Integer>> saveConsumers = new ArrayList<>();
	
	public IntegerTextFieldElement(GuiScreen screen, String key, int value) {
		this(screen, key, value, value);
	}
	
	public IntegerTextFieldElement(GuiScreen screen, String key, int value, int defaultValue) {
		super(screen, key);

		this.value = initialValue = value;
		this.defaultValue = defaultValue;
		
		GuiTextField textField = new GuiTextField(screen, Text.of(String.valueOf(value))).addActionListener(this);
		textField.setText(String.valueOf(value));
		element = add(textField);
		element.setSize(112, 20);
	}

	@Override
	public void actionPerformed(GuiElement source) {
		if(source == element) {
			GuiTextField textField = getTextField();
			
			Integer newValue = null;
			try {
				newValue = Integer.parseInt(textField.getText());
			}catch (Exception e) {}
			
			if(newValue != null) {
				value = newValue;
				
				for(ConfigElementListener configElementListener : configElementListeners) {
					configElementListener.valueChanged(this);
				}
				for(Consumer<Integer> consumer : updateConsumers) {
					consumer.accept(value);
				}
			}
		}
	}
	
	protected GuiTextField getTextField() {
		return (GuiTextField) element;
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
	
	public static IntegerTextFieldElement create(GuiScreen screen, String key, IntProperty property, UpdateMode updateMode) {
		return create(screen, key, property.getInt(), property.getDefaultValue(), property::setInt, updateMode);
	}
	
	public static IntegerTextFieldElement create(GuiScreen screen, String key, int value, int defaultValue, Consumer<Integer> consumer, UpdateMode updateMode) {
		IntegerTextFieldElement element = new IntegerTextFieldElement(screen, key, value, defaultValue);
		
		if(updateMode == UpdateMode.ON_SAVE) {
			element.addSaveConsumer(consumer);
		}else if(updateMode == UpdateMode.ON_UPDATE) {
			element.addUpdateConsumer(consumer);
		}
		
		return element;
	}
	
}
