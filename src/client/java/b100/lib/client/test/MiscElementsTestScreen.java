package b100.lib.client.test;

import b100.lib.Print;
import b100.lib.client.B100LibClient;
import b100.lib.client.gui.element.GuiButton;
import b100.lib.client.gui.element.GuiFloatSlider;
import b100.lib.client.gui.element.GuiTextField;
import b100.lib.client.mixin.IScreen;
import net.minecraft.text.Text;

class MiscElementsTestScreen extends BasicScrollableScreen {

	public MiscElementsTestScreen(IScreen parentScreen) {
		super(parentScreen);
	}

	@Override
	protected void onInit() {
		super.onInit();
		
		title = B100LibClient.trans.asText("screen.miscElements");
	}

	@Override
	public void initScrollElements() {
		GuiButton button1 = scrollList.add(new GuiButton(this, Text.of("Clickable button")));
		button1.setClickable(true);
		
		GuiButton button2 = scrollList.add(new GuiButton(this, Text.of("Not clickable button")));
		button2.setClickable(false);
		
		GuiFloatSlider slider1 = scrollList.add(new GuiFloatSlider(this, 0.5f));
		slider1.setSlideable(true);
		
		GuiFloatSlider slider2 = scrollList.add(new GuiFloatSlider(this, 0.33f));
		slider2.setSlideable(false);
		
		GuiTextField textField1 = scrollList.add(new GuiTextField(this, Text.of("Text field 1")));
		textField1.setText("Editable text field");
		textField1.actionListeners.add((src) -> {
			if(src instanceof GuiTextField textField) {
				Print.debugPrint(textField.getText());
			}
		});
		
		GuiTextField textField2 = scrollList.add(new GuiTextField(this, Text.of("Text field 2")));
		textField2.setText("Not editable text field");
		textField2.setEditable(false);
		textField2.setFocusable(true);
		
		GuiTextField textField3 = scrollList.add(new GuiTextField(this, Text.of("Text field 3")));
		textField3.setText("Not focusable text field");
		textField3.setEditable(false);
		textField3.setFocusable(false);
	}
	
}
