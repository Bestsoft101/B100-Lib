package b100.lib.client.gui.config.element;

import java.util.function.Consumer;

import b100.lib.client.gui.config.base.OptionElement;
import b100.lib.client.gui.element.GuiElement;
import b100.lib.client.gui.element.GuiTextField;
import b100.lib.client.gui.listener.ActionListener;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.util.UpdateMode;
import b100.lib.config.property.IntProperty;
import net.minecraft.text.Text;

public class IntegerTextFieldElement extends OptionElement<Integer, GuiTextField> implements ActionListener, ConfigElement<Integer> {
	
	public IntegerTextFieldElement(GuiScreen screen, String key, int value, int defaultValue) {
		super(screen, key, value, defaultValue);
	}
	
	@Override
	protected void initOptionElement(GuiTextField element) {
		super.initOptionElement(element);

		element.setText(String.valueOf(value));
		element.addActionListener(this);
	}

	@Override
	protected GuiTextField createOptionElement() {
		return new GuiTextField(screen, Text.of(String.valueOf(value)));
	}

	@Override
	public void actionPerformed(GuiElement source) {
		GuiTextField optionElement = getOptionElement();
		if(source == optionElement) {
			Integer newValue = null;
			try {
				newValue = Integer.parseInt(optionElement.getText());
			}catch (Exception e) {}
			
			if(newValue != null) {
				value = newValue;

				configElementListeners.forEach(listener -> listener.valueChanged(this));
				
				for(Consumer<Integer> consumer : updateConsumers) {
					consumer.accept(value);
				}
			}
		}
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
