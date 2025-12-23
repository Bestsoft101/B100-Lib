package b100.lib.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import b100.lib.client.gui.ActionListener;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.GuiTextField;
import net.minecraft.text.Text;

public class IntegerTextFieldElement extends AbstractOptionElement implements ActionListener, ConfigElement<Integer> {
	
	protected int initialValue;
	protected int value;
	protected int defaultValue;

	private final List<ConfigElementListener> configElementListeners = new ArrayList<>();
	private final List<Consumer<Integer>> saveConsumers = new ArrayList<>();
	
	public IntegerTextFieldElement(GuiScreen screen, String key, int value) {
		super(screen, key);
		
		this.value = initialValue = defaultValue = value;
		
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
				System.out.println(newValue);
				value = newValue;
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
	public GuiElement addSaveConsumer(Consumer<Integer> saveListener) {
		saveConsumers.add(saveListener);
		return this;
	}

	@Override
	public boolean removeSaveConsumer(Consumer<Integer> saveListener) {
		return saveConsumers.remove(saveListener);
	}
	
}
