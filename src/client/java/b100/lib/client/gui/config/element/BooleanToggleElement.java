package b100.lib.client.gui.config.element;

import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import b100.lib.client.B100LibClient;
import b100.lib.client.gui.config.base.ButtonOptionElement;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.util.UpdateMode;
import b100.lib.config.property.BooleanProperty;

public class BooleanToggleElement extends ButtonOptionElement<Boolean> {

	protected Function<Boolean, Component> toTextFunction;

	public BooleanToggleElement(GuiScreen screen, String key, boolean value, boolean defaultValue) {
		super(screen, key, value, defaultValue);
		
		update();
	}
	
	@Override
	public void onClick(int mouseButton) {
		value = !value;
		
		configElementListeners.forEach(listener -> listener.valueChanged(this));
		
		for(Consumer<Boolean> consumer : updateConsumers) {
			consumer.accept(value);
		}
		
		update();
	}
	
	@Override
	public Component getButtonText() {
		if(toTextFunction != null) {
			return toTextFunction.apply(value);	
		}
		
		String color;
		if(isEnabled()) {
			if(value) {
				color = "\247a";
			}else {
				color = "\247c";
			}
		}else {
			color = "";
		}
		
		return Component.nullToEmpty(value ? color + B100LibClient.TRANS.asString("value.yes") : color + B100LibClient.TRANS.asString("value.no"));
	}
	
	public BooleanToggleElement setToTextFunction(Function<Boolean, Component> toTextFunction) {
		this.toTextFunction = toTextFunction;
		update();
		return this;
	}
	
	public Function<Boolean, Component> getToTextFunction() {
		return toTextFunction;
	}
	
	////////////////////////////////
	
	public static BooleanToggleElement create(GuiScreen screen, String key, BooleanProperty property, UpdateMode updateMode) {
		return create(screen, key, property.getBoolean(), property.getDefaultValue(), property::setBoolean, updateMode);
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
