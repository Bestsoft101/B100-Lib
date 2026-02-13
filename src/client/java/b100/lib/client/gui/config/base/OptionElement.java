package b100.lib.client.gui.config.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import b100.lib.client.gui.element.Focusable;
import b100.lib.client.gui.element.GuiContainer;
import b100.lib.client.gui.element.GuiElement;
import b100.lib.client.gui.screen.GuiScreen;
import b100.lib.client.gui.util.ListenerList;
import b100.lib.client.translate.Translations;
import net.minecraft.text.Text;

public abstract class OptionElement<T extends Comparable<T>, E extends GuiElement> extends GuiContainer implements ConfigElement<T> {

	public final GuiScreen screen;
	
	protected Text name;
	protected Text tooltipText;
	
	protected final T defaultValue;
	protected T initialValue;
	protected T value;
	
	private E optionElement;
	
	protected final List<Consumer<T>> updateConsumers = new ArrayList<>();
	protected final List<Consumer<T>> saveConsumers = new ArrayList<>();
	
	protected final ListenerList<ConfigElementListener> configElementListeners = new ListenerList<>(this);
	
	public OptionElement(GuiScreen screen, String key, T value, T defaultValue) {
		this.screen = screen;
		this.defaultValue = defaultValue;
		this.initialValue = this.value = value;
		
		this.name = Translations.INSTANCE.asText(key);
		
		String tooltipText = Translations.INSTANCE.asStringOrNull(key + ".tooltip");
		if(tooltipText != null) {
			this.tooltipText = Text.of(tooltipText);
		}
		
		setSize(320, 24);
	}
	
	public final E getOptionElement() {
		if(optionElement == null) {
			optionElement = createOptionElement();
			if(optionElement == null) {
				throw new NullPointerException("createOptionElement returned null!");
			}
			initOptionElement(optionElement);
		}
		return optionElement;
	}
	
	protected abstract E createOptionElement();
	
	protected void initOptionElement(E element) {
		optionElement.setSize(112, 20);
		add(optionElement);
	}
	
	@Override
	public void onAddedToContainer(GuiContainer container) {
		// Make sure element is created
		getOptionElement();
		
		super.onAddedToContainer(container);
	}
	
	@Override
	public void draw() {
		GuiElement mouseOver = screen.getMouseOver();
		E element = getOptionElement();
		
		if(mouseOver == this || contains(mouseOver) || Focusable.isFocused(element)) {
			utils.drawRectangle(posX, posY, width, height, 0x20FFFFFF);
			
			if(tooltipText != null) {
				screen.drawWrappedTooltip(tooltipText);
			}
		}
		
		super.draw();
		
		utils.drawString(name, posX + 8, posY + height / 2 - 4, 0xFFFFFF, true);
	}
	
	@Override
	public void onResize() {
		E element = getOptionElement();
		
		element.setPosition(posX + width - element.width - 2, posY + height / 2 - element.height / 2);
		
		super.onResize();
	}
	
	public boolean equal(T value1, T value2) {
		return value1.compareTo(value2) == 0;
	}
	
	@Override
	public boolean isChanged() {
		return !equal(value, initialValue);
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
		return equal(value, defaultValue);
	}
	
	@Override
	public void save() {
		if(initialValue != value) {
			initialValue = value;
		}
		for(Consumer<T> saveConsumer : saveConsumers) {
			saveConsumer.accept(value);
		}
	}

	@Override
	public OptionElement<T, E> addConfigElementListener(ConfigElementListener listener) {
		configElementListeners.add(listener);
		return this;
	}

	@Override
	public boolean removeConfigElementListener(ConfigElementListener listener) {
		return configElementListeners.remove(listener);
	}

	public OptionElement<T, E> addUpdateConsumer(Consumer<T> consumer) {
		updateConsumers.add(consumer);
		return this;
	}

	public boolean removeUpdateConsumer(Consumer<T> consumer) {
		return updateConsumers.remove(consumer);
	}

	public OptionElement<T, E> addSaveConsumer(Consumer<T> consumer) {
		saveConsumers.add(consumer);
		return this;
	}

	public boolean removeSaveConsumer(Consumer<T> consumer) {
		return saveConsumers.remove(consumer);
	}
	
	public OptionElement<T, E> setTooltipText(Text tooltipText) {
		this.tooltipText = tooltipText;
		return this;
	}
	
	public Text getTooltipText() {
		return tooltipText;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
}
