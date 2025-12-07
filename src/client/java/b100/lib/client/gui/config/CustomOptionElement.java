package b100.lib.client.gui.config;

import java.util.function.Function;

import b100.lib.client.gui.ActionListener;
import b100.lib.client.gui.GuiElement;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.gui.ListenerList;
import net.minecraft.text.Text;

public class CustomOptionElement<E> extends AbstractButtonOptionElement {

	protected E value;
	protected Function<E, Text> toTextFunction;
	
	public final ListenerList<ActionListener> actionListeners = new ListenerList<>(this);
	
	public CustomOptionElement(GuiScreen screen, String key, E value) {
		super(screen, key);
		this.value = value;
		
		update();
	}

	@Override
	public void actionPerformed(GuiElement source) {
		actionListeners.forEach((e) -> e.actionPerformed(this));
	}
	
	@Override
	public Text getButtonText() {
		if(toTextFunction != null) {
			return toTextFunction.apply(value);	
		}
		return Text.of(String.valueOf(value));
	}
	
	public CustomOptionElement<E> setValue(E value) {
		this.value = value;
		return this;
	}
	
	public E getValue() {
		return value;
	}
	
	@Deprecated
	public CustomOptionElement<E> addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
		return this;
	}
	
	@Deprecated
	public boolean removeActionListener(ActionListener actionListener) {
		return actionListeners.remove(actionListener);
	}
	
	public CustomOptionElement<E> setToTextFunction(Function<E, Text> toTextFunction) {
		this.toTextFunction = toTextFunction;
		update();
		return this;
	}
	
	public Function<E, Text> getToTextFunction() {
		return toTextFunction;
	}

}
