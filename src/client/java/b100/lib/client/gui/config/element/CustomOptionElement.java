package b100.lib.client.gui.config.element;

import java.util.function.Function;
import net.minecraft.network.chat.Component;
import b100.lib.client.gui.config.base.ButtonOptionElement;
import b100.lib.client.gui.listener.ActionListener;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.gui.util.ListenerList;

public class CustomOptionElement<E> extends ButtonOptionElement<E> {

	protected Function<E, Component> toTextFunction;
	
	public final ListenerList<ActionListener> actionListeners = new ListenerList<>(this);
	
	public CustomOptionElement(GuiScreen screen, String key, E value) {
		this(screen, key, value, value);
	}
	
	public CustomOptionElement(GuiScreen screen, String key, E value, E defaultValue) {
		super(screen, key, value, defaultValue);
		
		update();
	}

	@Override
	public void onClick(int mouseButton) {
		actionListeners.forEach((e) -> e.actionPerformed(this));
	}
	
	@Override
	public Component getButtonText() {
		if(toTextFunction != null) {
			return toTextFunction.apply(value);	
		}
		return Component.nullToEmpty(String.valueOf(value));
	}
	
	public CustomOptionElement<E> setValue(E value) {
		this.value = value;
		return this;
	}
	
	public E getValue() {
		return value;
	}
	
	public CustomOptionElement<E> addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
		return this;
	}
	
	public boolean removeActionListener(ActionListener actionListener) {
		return actionListeners.remove(actionListener);
	}
	
	public CustomOptionElement<E> setToTextFunction(Function<E, Component> toTextFunction) {
		this.toTextFunction = toTextFunction;
		update();
		return this;
	}
	
	public Function<E, Component> getToTextFunction() {
		return toTextFunction;
	}

}
