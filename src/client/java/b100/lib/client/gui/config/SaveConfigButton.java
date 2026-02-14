package b100.lib.client.gui.config;

import java.util.HashSet;
import java.util.Set;

import b100.lib.client.B100LibClient;
import b100.lib.client.gui.config.base.ConfigElement;
import b100.lib.client.gui.config.base.ConfigElementListener;
import b100.lib.client.gui.element.GuiButton;
import b100.lib.client.gui.screen.GuiScreen;

public class SaveConfigButton extends GuiButton implements ConfigElementListener {

	private final Set<ConfigElement<?>> changedConfigElements = new HashSet<>();
	
	public SaveConfigButton(GuiScreen screen) {
		super(screen, B100LibClient.trans.asText("button.saveAndQuit"));
		
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
	public void clickButton(int button) {
		for(ConfigElement<?> configElement : changedConfigElements) {
			configElement.save();
		}
		super.clickButton(button);
	}

}
