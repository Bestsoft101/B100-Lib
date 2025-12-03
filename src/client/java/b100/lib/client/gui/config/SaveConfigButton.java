package b100.lib.client.gui.config;

import java.util.HashSet;
import java.util.Set;

import b100.lib.client.gui.GuiButton;
import b100.lib.client.gui.GuiScreen;
import b100.lib.client.translate.Translate;

public class SaveConfigButton extends GuiButton implements ConfigElementListener {

	private final Set<ConfigElement<?>> changedConfigElements = new HashSet<>();
	
	public SaveConfigButton(GuiScreen screen) {
		super(screen, Translate.translate("button.saveAndQuit"));
		
		setActive(false);
	}

	@Override
	public void valueChanged(ConfigElement<?> configElement) {
		if(configElement.isChanged()) {
			changedConfigElements.add(configElement);
		}else {
			changedConfigElements.remove(configElement);
		}
		setActive(changedConfigElements.size() > 0);
	}
	
	public void setActive(boolean active) {
		setClickable(active);
	}
	
	@Override
	public void clickButton() {
		for(ConfigElement<?> configElement : changedConfigElements) {
			configElement.save();
		}
		super.clickButton();
	}

}
