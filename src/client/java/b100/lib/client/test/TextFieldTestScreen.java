package b100.lib.client.test;

import b100.lib.Print;
import b100.lib.client.gui.GuiButton;
import b100.lib.client.gui.GuiScrollListScreen;
import b100.lib.client.gui.GuiScrollableList.ListLayout;
import b100.lib.client.gui.GuiScrollableList.ListLayout.Align;
import b100.lib.client.gui.GuiTextField;
import b100.lib.client.mixin.IScreen;
import net.minecraft.text.Text;

public class TextFieldTestScreen extends GuiScrollListScreen {

	public GuiButton doneButton;
	
	public TextFieldTestScreen(IScreen parentScreen) {
		super(parentScreen);
	}

	@Override
	protected void onInit() {
		super.onInit();
		
		title = Text.of("Text Fields");
		
		doneButton = add(new GuiButton(this, Text.of("Done")));
		doneButton.addActionListener((e) -> back());
		
		scrollList.layout = new ListLayout().setInnerPadding(4).setOuterPadding(8).setAlign(Align.CENTER);
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
	
	@Override
	public void onResize() {
		super.onResize();
		setFooterButtonPosition(doneButton);
	}
	
}
