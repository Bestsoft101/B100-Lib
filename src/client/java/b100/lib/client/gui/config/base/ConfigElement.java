package b100.lib.client.gui.config.base;

import b100.lib.client.gui.GuiElement;

public interface ConfigElement<E> {
	
	public boolean isChanged();
	
	public boolean isDefaultValue();
	
	public void resetToInitialValue();
	
	public void resetToDefaultValue();
	
	public void save();
	
	public GuiElement addConfigElementListener(ConfigElementListener listener);
	
	public boolean removeConfigElementListener(ConfigElementListener listener);

}
