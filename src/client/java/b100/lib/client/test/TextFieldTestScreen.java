package b100.lib.client.test;

import b100.lib.Print;
import b100.lib.client.B100LibClient;
import b100.lib.client.gui.GuiTextField;
import b100.lib.client.mixin.IScreen;
import net.minecraft.text.Text;

class TextFieldTestScreen extends BasicScrollableScreen {

	public TextFieldTestScreen(IScreen parentScreen) {
		super(parentScreen);
	}

	@Override
	protected void onInit() {
		super.onInit();
		
		title = B100LibClient.trans.asText("screen.textfields");
	}

	@Override
	public void initScrollElements() {
		GuiTextField tf1 = scrollList.add(new GuiTextField(this, Text.of("Text field 1")));
		tf1.setText("Editable text field");
		tf1.actionListeners.add((src) -> {
			if(src instanceof GuiTextField textField) {
				Print.debugPrint(textField.getText());
			}
		});
		
		GuiTextField tf2 = scrollList.add(new GuiTextField(this, Text.of("Text field 2")));
		tf2.setText("Not editable text field");
		tf2.setEditable(false);
		tf2.setFocusable(true);
		
		GuiTextField tf3 = scrollList.add(new GuiTextField(this, Text.of("Text field 3")));
		tf3.setText("Not focusable text field");
		tf3.setEditable(false);
		tf3.setFocusable(false);
	}
	
}
